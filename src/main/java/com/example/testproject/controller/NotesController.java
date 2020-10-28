package com.example.testproject.controller;

import com.example.testproject.exceptions.NoAccessToNoteException;
import com.example.testproject.exceptions.NoteIdMismatchException;
import com.example.testproject.exceptions.NoteNotFoundException;
import com.example.testproject.model.Note;
import com.example.testproject.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NotesController {

    @Autowired
    private NotesService notesService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Note> getUserNotes() {
        return notesService.getUserNotes();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Note getNote(@PathVariable String id) throws NoAccessToNoteException, NoteNotFoundException {
        return notesService.getNote(Long.parseLong(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable String id) throws NoAccessToNoteException, NoteNotFoundException {
        notesService.deleteNote(Long.parseLong(id));
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void createNote(@RequestBody Note note) {
        notesService.createNote(note);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void createNote(@PathVariable String id, @RequestBody Note note) throws NoAccessToNoteException, NoteNotFoundException, NoteIdMismatchException {
        notesService.updateNote(Long.parseLong(id), note);
    }
}
