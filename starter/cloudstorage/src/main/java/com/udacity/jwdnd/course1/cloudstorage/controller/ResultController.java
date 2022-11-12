package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    private AuthenticationService authenticationService;

    public ResultController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/result")
    public String View(Authentication authentication){
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "login";
        }
        return "result";
    }
}
