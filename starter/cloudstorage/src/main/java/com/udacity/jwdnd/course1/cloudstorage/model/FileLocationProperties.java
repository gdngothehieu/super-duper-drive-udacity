package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("model")
public class FileLocationProperties {

    private  String location = "Downloads";


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
