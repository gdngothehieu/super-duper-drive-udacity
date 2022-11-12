package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private AuthenticationService authenticationService;
    private CredentialMapper credentialMapper;
    private CredentialService credentialService;

    public CredentialController(AuthenticationService authenticationService, CredentialMapper credentialMapper, CredentialService credentialService) {
        this.authenticationService = authenticationService;
        this.credentialMapper = credentialMapper;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String postCredential(Authentication authentication, @ModelAttribute("credentialForm")CredentialForm credentialForm, RedirectAttributes redirectAttributes) {
        int rowAddedOrUpdated = 0;
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "login";
        }

        String status = "";

        credentialForm.setUserId(user.getUserid());
        if (credentialForm.getCredentialId() == null){
            rowAddedOrUpdated = this.credentialService.addCredential(credentialForm);
            status = "saved";
        } else {
            rowAddedOrUpdated = this.credentialService.updateCredential(credentialForm);
            status = "updated";
        }

        if (rowAddedOrUpdated > 0) {
            redirectAttributes.addFlashAttribute("successMsg", "Your credentials " + status + " successfully. ");
        } else if (rowAddedOrUpdated == 0){
            redirectAttributes.addFlashAttribute("failureMsg", "Your credentials were not " + status +" .");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "An error occurred, kindly contact administrator. ");
        }
        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{id}")
    public String deleteCredential(@PathVariable long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null) {
            return "login";
        }
        int deletedCredentialId = credentialMapper.deleteCredential((int) id);

        if (deletedCredentialId > 0) {
            redirectAttributes.addFlashAttribute("successMsg", "Selected credentials deleted successfully. ");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Unable to delete. ");
        }
        return "redirect:/home";
    }
}
