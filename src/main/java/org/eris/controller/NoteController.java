package org.eris.controller;

import lombok.extern.slf4j.Slf4j;
import org.eris.entity.Note;
import org.eris.entity.User;
import org.eris.service.NoteService;
import org.eris.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> getNotesByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String userName = authentication.getName();
        User userNotes = userService.findByUserName(userName);
        List<Note> noteData = userNotes.getNotes();
        if(noteData != null || noteData.isEmpty()){
            return new ResponseEntity<>(noteData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody Note myNote){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || !authentication.isAuthenticated()){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String userName = authentication.getName();
            noteService.saveEntry(myNote, userName);
            log.info("Controller hit");
            log.info("User {}", userName);
            return new ResponseEntity<>(myNote, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byUserName = userService.findByUserName(userName);
        List<Note> collect = byUserName.getNotes().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Note> noteData = noteService.findById(id);
            if(noteData.isPresent()){
                return new ResponseEntity<>(noteData.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        noteService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateNoteById(@PathVariable Long myId, @RequestBody Note note){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Note> collect = user.getNotes().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Note> noteEntry = noteService.findById(myId);
            if(noteEntry.isPresent()){
                Note old = noteEntry.get();
                old.setTitle(note.getTitle() != null && !note.getTitle().equals("")? note.getTitle(): old.getTitle());
                old.setContent(note.getContent() != null && !note.getContent().equals("")? note.getContent(): old.getContent());

            }
        }
    }
}
