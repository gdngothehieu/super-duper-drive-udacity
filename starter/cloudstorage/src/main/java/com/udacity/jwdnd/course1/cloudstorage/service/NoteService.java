package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private List<Note> notes;

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotesByUserId(int userId) {
        return this.noteMapper.getNotesByUserId(userId);
    }

    public List<Note> getNotesByUserId(Integer userId){
        return this.noteMapper.getNotesByUserId(userId);
    }

    public Note getNoteByNoteId(Integer noteId){
        return this.noteMapper.getNoteByNoteId(noteId);
    }

    public int addNote(NoteForm noteForm){
        int rowAdded = 0;
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitleTxt());
        note.setUserId(noteForm.getUserId());
        note.setNoteDescription(noteForm.getNoteDescriptionTxt());
        return this.noteMapper.insertNote(note);
    }

    public int updateNote(NoteForm noteForm){
        int rowAddedOrUpdated = 0;
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitleTxt());
        note.setUserId(noteForm.getUserId());
        note.setNoteDescription(noteForm.getNoteDescriptionTxt());
        note.setNoteId(noteForm.getNoteId());
        return this.noteMapper.updateNote(note);
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Creating NoteService bean");
    }
}
