package org.eris.service;

import lombok.extern.slf4j.Slf4j;
import org.eris.entity.Note;
import org.eris.entity.User;
import org.eris.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }
    @Transactional // can be roll back if fails
    public Note saveEntry(Note note, String userName){
        if(note == null){
            throw new IllegalArgumentException("Note cannot be null");
        }
        if (userName == null && userName.isBlank()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        User user = userService.findByUserName(userName);
        if(user == null){
            log.error("user not found with id {}", userName);
            throw new RuntimeException("User not found " + userName);
        }
        if(user.getNotes() == null){
            user.setNotes(new ArrayList<>());
        }
        note.setCreatedAt(LocalDateTime.now());
        Note savedNote = noteRepository.save(note);
        userService.saveNewUser(user);
        return savedNote;
    }
    public void saveEntry(Note note){
        noteRepository.save(note);
    }
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }
    public Optional<Note> findById(Long id){
        return noteRepository.findById(id);
    }
    public void deleteById(Long id, String userName){
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        if(userName == null || userName.isBlank()){
            throw new IllegalArgumentException("Username cannot be empty or null");
        }
        User user = userService.findByUserName(userName);
        if(user == null){
            log.error("User cannot be null");
            throw new RuntimeException("User cannot be null");
        }
        boolean removed = user.getNotes().removeIf(x->x.getId().equals(id));
        if(!removed){
            throw new RuntimeException("Entry not found in user's list");
        }
        userService.saveUser(user);
        noteRepository.deleteById(id);
    }
}
