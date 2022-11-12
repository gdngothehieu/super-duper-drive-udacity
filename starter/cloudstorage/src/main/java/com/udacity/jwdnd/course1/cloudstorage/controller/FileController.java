package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@ControllerAdvice
public class FileController {

    private AuthenticationService authenticationService;
    private FileService fileService;

    public FileController(AuthenticationService authenticationService, FileService fileService) {
        this.authenticationService = authenticationService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null){
            return "redirect:/login";
        }
        String errorMessage = this.fileService.getErrorMessage(file);

        if (errorMessage == null){
            int rowAddedOrUpdated = this.fileService.addFile(file, user.getUserid());
            if (rowAddedOrUpdated > 0) {
                redirectAttributes.addFlashAttribute("successMsg", "You successfully uploaded " + file.getOriginalFilename() + "!");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "An error occurred, kindly contact administrator. ");
            }
        } else{
            redirectAttributes.addFlashAttribute("errorMsg", errorMessage);
        }
        return "redirect:/home";
    }

    @GetMapping("/file/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable long id) throws FileNotFoundException {
        File file = fileService.getFileData((int) id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/file/delete/{id}")
    public String deleteCredential(@PathVariable long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = this.authenticationService.getUserDetails(authentication);
        if (user == null) {
            return "login";
        }

        String fileName = fileService.getFileNameByFileId((int)id);
        int fileId = this.fileService.deleteFile((int)id);

        if (fileId > 0) {
            redirectAttributes.addFlashAttribute("successMsg", "You successfully deleted " + fileName + "! file.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "You were unable to delete " + fileName + "! file.");
        }
        return "redirect:/home";
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleUploadError(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMsg", "Unable to upload file because it exceeds 2MB");
        return "redirect:/home";
    }
}
