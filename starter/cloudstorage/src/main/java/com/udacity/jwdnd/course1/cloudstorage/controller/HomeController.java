package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    private NoteService noteService;
    private AuthenticationService authenticationService;
    private CredentialService credentialService;
    private FileMapper fileMapper;



    public HomeController(NoteService noteService, AuthenticationService authenticationService, CredentialService credentialService, FileMapper fileMapper) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.credentialService = credentialService;
        this.fileMapper = fileMapper;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication, NoteForm noteForm, Model model){
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "login";
        }

        model.addAttribute("noteRecords", this.noteService.getNotesByUserId(user.getUserid()));
        model.addAttribute("credentialRecords", this.credentialService.getCredentialsByUserId(user.getUserid()));
        model.addAttribute("credentials", new CredentialForm());
        model.addAttribute("files", this.fileMapper.getFilesByUserId(user.getUserid()));
        return "home";
    }
}
