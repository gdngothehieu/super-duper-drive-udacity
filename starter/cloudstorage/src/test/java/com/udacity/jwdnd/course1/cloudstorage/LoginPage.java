package com.udacity.jwdnd.course1.cloudstorage;

import org.apache.juli.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "loginBtn")
    private WebElement loginBtn;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public String getUsername(){
        return this.username.getText();
    }

    public String getPassword(){
        return this.password.getText();
    }

    public void loginSubmission(String username, String password){
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.loginBtn.click();
    }

    public boolean isLogout(){
        return this.logoutMsg != null;
    }

}
