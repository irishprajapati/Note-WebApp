package org.eris.repository;

import org.eris.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository  extends JpaRepository<Note, Long> {
    //takes the two parameters -> note -> entity type and then Long means id type of that selected entity
}
