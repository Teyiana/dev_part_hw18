package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.dto.NoteDTO;
import org.example.dto.UserDTO;
import org.example.model.Note;
import org.example.model.User;
import org.example.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    public List<NoteDTO> findAllNotes() {
        List<Note> noteList = noteRepository.findAll();
        return noteList.stream().map(NoteService::toDto).toList();

    }

    public NoteDTO create(NoteDTO noteDto) {

        if(noteDto.getId() > 0){
            throw new IllegalArgumentException("Id must be not set");
        }

        if(!StringUtils.hasText(noteDto.getTitle())){
            throw new IllegalArgumentException("Title must be set");
        }

        if(!StringUtils.hasText(noteDto.getContent())){
            throw new IllegalArgumentException("Content must be set");
        }

        Note note = new Note();
        User  user = new User();
        user.setUserId(noteDto.getUserId());
        note.setUser(user);
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note = noteRepository.save(note);
        noteDto.setId(note.getId());
        return noteDto;
    }

    public void deleteNoteById(long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            noteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Note not found, id: " + id);
        }
    }

    public NoteDTO update(NoteDTO note) {
       Note noteNew = noteRepository.findById(note.getId()).orElseThrow();
       if(note.getContent() != null){
           noteNew.setContent(note.getContent());
       }
       if (note.getTitle() != null){
           noteNew.setTitle(note.getTitle());
       }
       noteRepository.save(noteNew);
       return toDto(noteNew);
    }


    public NoteDTO getNoteById(long id) {
        if(id <= 0){
            throw new IllegalArgumentException("Id is not valid: id=" + id);
        }

        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()){
            return toDto(note.get());
        }
        throw new IllegalArgumentException("Note not found, id: " + id);
    }


    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Bean created: {}", this.getClass().getName());

    }

    public static NoteDTO toDto(Note entity){
        if (entity == null) {
            return null;
        }
        NoteDTO dto = new NoteDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        return dto;
    }

}
