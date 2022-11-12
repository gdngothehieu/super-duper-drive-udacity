package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private  WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private  WebElement inputPassword;

    @FindBy(id = "signupSubmitBtn")
    private WebElement signupSubmitBtn;

    @FindBy(id = "signupSuccess")
    private  WebElement success;

    @FindBy(id = "signupError")
    private WebElement failure;

    @FindBy(id = "login-link")
    private WebElement login;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public String getFirstName(){
        return inputFirstName.getText();
    }

    public String getLastName(){
        return inputLastName.getText();
    }

    public String getUsername(){
        return inputUsername.getText();
    }

    public String getPassword(){
        return inputPassword.getText();
    }

    public boolean isSuccessful(){
        return success != null;
    }

    public boolean isFailure(){
        return failure != null;
    }

    public void signupSubmission(String inputFirstName, String inputLastName, String inputUsername, String inputPassword){
        this.inputFirstName.sendKeys(inputFirstName);
        this.inputLastName.sendKeys(inputLastName);
        this.inputUsername.sendKeys(inputUsername);
        this.inputPassword.sendKeys(inputPassword);
        this.signupSubmitBtn.click();
    }

    public void getLoginPage(){
        this.login.click();
    }

}
