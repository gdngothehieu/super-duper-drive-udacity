package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private FileMapper fileMapper;
    private final Path rootLocation;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.rootLocation = Paths.get("Downloads");
    }

    public int addFile(MultipartFile uploadedFile, Integer userId) throws IOException {
        File file = new File();
        file.setFileData(uploadedFile.getBytes());
        file.setFileName(uploadedFile.getOriginalFilename());
        file.setContentType(uploadedFile.getContentType());
        file.setFileSize(uploadedFile.getSize());
        file.setUserId(userId);
        return this.fileMapper.insertFile(file);
    }

    public Boolean exists(MultipartFile uploadedFile){
        if(fileMapper.getFileByFileName(uploadedFile.getOriginalFilename()) != null){
            return true;
        }
        return false;
    }

    public String getErrorMessage(MultipartFile file){
        String errorMessage = null;

        if(file.isEmpty()) {
            errorMessage = "No file was selected. Kindly choose a file.";
        } else if (file.getSize() > 2048 * 1000) {
            errorMessage = "Unable to upload " + file.getOriginalFilename() + " because it exceeds 2MB. ";
        } else if (exists(file)) {
            errorMessage = "Unable to upload " + file.getOriginalFilename() + ", because it exists. ";
        }
        return errorMessage;
    }

    public int deleteFile(int fileId){
        return fileMapper.deleteFile(fileId);
    }

    public String getFileNameByFileId(int fileId){
        return fileMapper.getFileNameByFileId(fileId);
    }

    public Path load(String fileName){
        return rootLocation.resolve(fileName);
    }

    public File getFileData(int fileId){
        try{
            File file = fileMapper.getFileDataByFileId(fileId);

            if(file == null){
                throw new FileNotFoundException("Could not Find file.");
            }
            return file;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file.");
        }
    }

}
