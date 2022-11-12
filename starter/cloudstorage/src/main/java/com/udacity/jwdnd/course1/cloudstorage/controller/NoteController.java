package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private NoteService noteService;
    private AuthenticationService authenticationService;
    private NoteMapper noteMapper;

    public NoteController(NoteService noteService, AuthenticationService authenticationService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.noteMapper = noteMapper;
    }

    @PostMapping("/note")
    public String postNote(Authentication authentication, @ModelAttribute("noteForm") NoteForm noteForm, RedirectAttributes redirectAttributes){
       int rowAddedOrUpdated = 0;
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "login";
        }
        noteForm.setUserId(user.getUserid());

        String status = "";
        if (noteForm.getNoteId() == null){
            rowAddedOrUpdated = this.noteService.addNote(noteForm);
            status = "saved";
        } else {
            rowAddedOrUpdated = this.noteService.updateNote(noteForm);
            status = "updated";
        }

        if (rowAddedOrUpdated > 0) {
            redirectAttributes.addFlashAttribute("successMsg", "Your note " + status + " successfully. ");
        } else if (rowAddedOrUpdated == 0){
            redirectAttributes.addFlashAttribute("failureMsg", "Your note changes were not " + status + ". ");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "An error occurred, kindly contact administrator. ");
        }
        return "redirect:/home";
    }


    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable long id, Authentication authentication, RedirectAttributes redirectAttributes){
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "login";
        }
        int deletedNoteId = noteMapper.deleteNote((int)id);

        if (deletedNoteId > 0){
            redirectAttributes.addFlashAttribute("successMsg", "Note deleted successfully. ");
        } else{
            redirectAttributes.addFlashAttribute("errorMsg", "Unable to delete. ");
        }
        return "redirect:/home";

    }
}
