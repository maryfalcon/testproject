package com.example.testproject.service.impl;

import com.example.testproject.exceptions.NoteIdMismatchException;
import com.example.testproject.exceptions.NoAccessToNoteException;
import com.example.testproject.exceptions.NoteNotFoundException;
import com.example.testproject.model.Note;
import com.example.testproject.model.User;
import com.example.testproject.repository.NoteRepository;
import com.example.testproject.repository.UserRepository;
import com.example.testproject.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotesServiceImpl implements NotesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getUserNotes() {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getNotes();
    }

    @Override
    public Note getNote(Long id) throws NoAccessToNoteException, NoteNotFoundException {
        Optional<Note> foundNote = noteRepository.findById(id);
        if (foundNote.isEmpty())
            throw new NoteNotFoundException();
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!foundNote.get().getOwnerId().getId().equals(user.getId()))
            throw new NoAccessToNoteException();
        return foundNote.get();
    }

    @Override
    public void createNote(Note note) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        note.setOwnerId(user);
        noteRepository.save(note);
    }

    @Override
    public void updateNote(Long id, Note note) throws NoAccessToNoteException, NoteNotFoundException, NoteIdMismatchException {
        if (!id.equals(note.getId()))
            throw new NoteIdMismatchException();
        Note noteFromDb = getNote(id);
        noteFromDb.setContent(note.getContent());
        noteFromDb.setTitle(note.getTitle());
        noteFromDb.setFields(note.getFields());
        noteRepository.save(noteFromDb);
    }

    @Override
    public void deleteNote(Long id) throws NoAccessToNoteException, NoteNotFoundException {
        Note note = getNote(id);
        noteRepository.deleteById(id);
    }
}
