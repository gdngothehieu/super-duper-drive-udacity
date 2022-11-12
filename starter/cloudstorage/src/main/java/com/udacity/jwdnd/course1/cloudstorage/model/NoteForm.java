package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private Integer noteId;
    private String noteTitleTxt;
    private String noteDescriptionTxt;
    private Integer userId;

    public Integer getNoteId() {
        return this.noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitleTxt() {
        return this.noteTitleTxt;
    }

    public void setNoteTitleTxt(String noteTitleTxt) {
        this.noteTitleTxt = noteTitleTxt;
    }

    public String getNoteDescriptionTxt() {
        return this.noteDescriptionTxt;
    }

    public void setNoteDescriptionTxt(String noteDescriptionTxt) {
        this.noteDescriptionTxt = noteDescriptionTxt;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
