package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.NoteDTO;
import org.example.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notes")
public class NoteController {
private final NoteService noteService;


    @GetMapping("/readAll")
    @ResponseBody
    public List<NoteDTO> read() {
        final List<NoteDTO> notes = noteService.findAllNotes();
        return notes;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id)  {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public NoteDTO create(@RequestBody NoteDTO note) {
        return noteService.create(note);
    }

    @PutMapping(value = "/edit/")
    @ResponseBody
    public NoteDTO update( @RequestBody NoteDTO note) {
        return noteService.update(note);
    }


    @GetMapping(value = "/read/{id}")
    public ResponseEntity<NoteDTO> read(@PathVariable(name = "id") long id) {
        final NoteDTO note = noteService.getNoteById(id);

        return note != null
               ? new ResponseEntity<>(note, HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
