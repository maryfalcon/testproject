package com.example.testproject.service;

import com.example.testproject.exceptions.NoteIdMismatchException;
import com.example.testproject.exceptions.NoAccessToNoteException;
import com.example.testproject.exceptions.NoteNotFoundException;
import com.example.testproject.model.Note;

import java.util.List;

public interface NotesService {

    List<Note> getUserNotes();

    Note getNote(Long id) throws NoAccessToNoteException, NoteNotFoundException;

    void createNote(Note note);

    void updateNote(Long id, Note note) throws NoAccessToNoteException, NoteNotFoundException, NoteIdMismatchException;

    void deleteNote(Long id) throws NoAccessToNoteException, NoteNotFoundException;

}
