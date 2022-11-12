package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private HashService hashService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public int addCredential(CredentialForm credentialForm){
        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());
        credential.setUserId(credentialForm.getUserId());
        return this.credentialMapper.insertCredential(setCredentialKey(credential));
    }

    public int updateCredential(CredentialForm credentialForm){
        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());
        credential.setUserId(credentialForm.getUserId());
        credential.setCredentialId(credentialForm.getCredentialId());
        return this.credentialMapper.updateCredential(setCredentialKey(credential));
    }

    public Credential setCredentialKey(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String generateEncodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), generateEncodedKey);
        credential.setKey(generateEncodedKey);
        credential.setPassword(encryptedPassword);
        return credential;
    }

    public List<CredentialForm> getCredentialsByUserId(int userId){
        List<Credential> listOfCredentials = credentialMapper.getCredentialByUserId(userId);
        List<CredentialForm> listCredentialForm = new ArrayList<>();
        for (Credential credential : listOfCredentials){
            CredentialForm credentialForm = new CredentialForm();
            credentialForm.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
            credentialForm.setCredentialId(credential.getCredentialId());
            credentialForm.setUrl(credential.getUrl());
            credentialForm.setUsername(credential.getUsername());
            credentialForm.setKey(credential.getKey());
            credentialForm.setUserId(credential.getUserId());
            credentialForm.setPassword(credential.getPassword());
            listCredentialForm.add(credentialForm);
        }
        return listCredentialForm;
    }



    @PostConstruct
    public void postConstruct(){
        System.out.println("Creating CredentialService bean");
    }
}
