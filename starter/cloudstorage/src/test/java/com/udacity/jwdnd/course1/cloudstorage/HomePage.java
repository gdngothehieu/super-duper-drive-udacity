package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class HomePage {

    private final WebDriverWait wait;

    @FindBy(id = "logoutBtn")
    private WebElement logoutBtn;

    @FindBy(id = "nav-notes")
    private WebElement navNotes;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmitBtn")
    private WebElement noteSubmit;

    @FindBy(id = "userTable")
    private WebElement userTable;

    @FindBy(id = "editNoteBtn")
    private WebElement editNoteBtn;

    @FindBy(id = "deleteBtn")
    private WebElement deleteBtn;

    @FindBy(xpath = "//*[@id=\"nav-credentials-tab\"]")
    private WebElement navCredentialsTab;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]/button")
    private WebElement addCredentialBtn;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement createCredentialsBtn;

    @FindBy(xpath = "//*[@id=\"credential-url\"]")
    private WebElement urlField;

    @FindBy(xpath = "//*[@id=\"credential-username\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement credentialEditBtn;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a/button")
    private WebElement credentialDeleteBtn;

    @FindBy(id = "successMsg")
    private WebElement success;

    @FindBy(id = "failureMsg")
    private WebElement failure;

    @FindBy(xpath = "//*[@id=\"fileUpload\"]")
    private WebElement fileUploadBtn;

    @FindBy(xpath = "//*[@id=\"nav-files-tab\"]")
    private WebElement NavFileTab;

    @FindBy(xpath = "//*[@id=\"nav-files\"]/form/div/div/div[3]/button")
    private WebElement uploadBtn;

    public boolean isSuccess(){
        return success != null;
    }

    public boolean isFailure(){
        return failure != null;
    }

    public String getNoteTitle(){
        return this.noteTitle.getText();
    }

    public String getNoteDescription(){
        return this.noteDescription.getText();
    }

    public String getUrlField(){
        return this.urlField.getText();
    }

    public String getUsernameField(){
        return  this.usernameField.getText();
    }

    public String getPasswordField(){
        return this.passwordField.getText();
    }

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    public void elementClick(WebElement element){
        element = wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void logout(){
        elementClick(this.logoutBtn);
    }

    public void displayNotesTab(){
        elementClick(this.navNotesTab);
    }

    public void openModalForNote(){
        elementClick(this.addNoteBtn);
    }

    public void openModalForCredential(){
        elementClick(this.addCredentialBtn);
    }

    public void displayCredentialTab() {
        elementClick(this.navCredentialsTab);
    }

    public void displayFileTab() {
        elementClick(this.NavFileTab);
    }

    public void uploadFile(){
        elementClick(this.uploadBtn);
    }

    public void addNotes(String noteTitle, String noteDescription){
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        elementClick(this.noteSubmit);
    }

    public void createNote(String noteTitle, String noteDescription) throws InterruptedException {
        displayNotesTab();
        openModalForNote();
        Thread.sleep(2000);
        addNotes(noteTitle, noteDescription);
    }

    public void editNote(String notetitle, String noteDescription) throws InterruptedException {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(notetitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmit.click();
        Thread.sleep(2000);
    }

    public void clickEditNoteBtn() throws InterruptedException {
        elementClick(this.editNoteBtn);
        Thread.sleep(2000);
    }

    public void deleteNote(){
        elementClick(this.deleteBtn);
    }

    public boolean isDeletedRow(WebDriver driver, String tableId){
        List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"" + tableId + "\"]/tbody/tr"));
        int rowCount = webElementList.size();

        return rowCount == 0;
    }

    public boolean isNoteRowDeleted(WebDriver driver){
        return isDeletedRow(driver, "userTable");
    }

    public  boolean isCredentialRowDeleted(WebDriver driver){
        return  isDeletedRow(driver, "credentialTable");
    }

    public void createCredentials(String url, String username, String password) throws InterruptedException {
        Thread.sleep(2000);
        this.urlField.sendKeys(url);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        elementClick(this.createCredentialsBtn);
        Thread.sleep(2000);
    }

    public boolean isNoteDisplayed(WebDriver driver, String noteTitle, String noteDescription){
        return isRecordDisplayed(driver, noteTitle, noteDescription, "userTable");
    }

    public boolean isCredentialDisplayed(WebDriver driver, String urlText, String usernameText){
        return isRecordDisplayed(driver, urlText, usernameText, "credentialTable");
    }


    public boolean isRecordDisplayed(WebDriver driver, String fieldOne, String fieldTwo, String tableId){
        boolean isDisplayed = false;
        List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"" + tableId + "\"]/tbody/tr"));
        int rowCount = webElementList.size();

        for(int i = 1; i <= rowCount; i++){
            WebElement rowDataElementOne = driver.findElement(By.xpath("//*[@id=\"" + tableId + "\"]/tbody[" + i + "]/tr/th"));
            WebElement rowDataElementTwo = driver.findElement(By.xpath("//*[@id=\"" + tableId + "\"]/tbody[" + i + "]/tr/td[2]"));

            String rowDataOne = rowDataElementOne.getAttribute("innerHTML");
            String rowDataTwo = rowDataElementTwo.getAttribute("innerHTML");
            if (fieldOne.equalsIgnoreCase(rowDataOne) && fieldTwo.equalsIgnoreCase(rowDataTwo)){
                isDisplayed = true;
                break;
            }
        }
        return isDisplayed;
    }

    public boolean isPasswordEncrypted(WebDriver driver, String passwordText){
        String encryptedPassword = passwordText;
        List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr"));
        int rowCount = webElementList.size();

        for(int i = 1; i <= rowCount; i++){
            WebElement encryptedElement = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody[" + i + "]/tr/td[3]"));
            encryptedPassword = encryptedElement.getAttribute("innerHTML");
            break;
        }
        return !encryptedPassword.equalsIgnoreCase(passwordText);
    }

    public void clickCredentialEditBtn(){
        elementClick(this.credentialEditBtn);
    }

    public void editCredential(String urlTxt, String usernameTxt, String passwordTxt) throws InterruptedException {
        this.urlField.clear();
        this.urlField.sendKeys(urlTxt);
        this.usernameField.clear();
        this.usernameField.sendKeys(usernameTxt);
        this.passwordField.clear();
        this.passwordField.sendKeys(passwordTxt);
        this.createCredentialsBtn.click();
        Thread.sleep(2000);
    }

    public void deleteCredential(){
        elementClick(this.credentialDeleteBtn);
    }

    public void fileSelectionBtn(){
        this.fileUploadBtn.sendKeys("/home/hieunt/Desktop/web.txt");

    }

    @FindBy(xpath = "//*[@id=\"fileTable\"]/tbody/tr/td/a[1]/button")
    private WebElement downloadBtn;

    @FindBy(xpath = "//*[@id=\"fileTable\"]/tbody/tr/td/a[2]/button")
    private WebElement deleteFileBtn;


    public boolean isFileDownloaded(WebDriver driver) {
        String downloadPath = "/home/hieunt/Downloads";
        String fileName = driver.findElement(By.xpath("//*[@id=\"fileTable\"]/tbody/tr/th")).getAttribute("innerHTML");
        File directory = new File(downloadPath);
        File[] dirContents = directory.listFiles();

        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName))
                dirContents[i].delete();
                return true;
        }
        return false;
    }

    public void downloadBtnClick(){
        elementClick(downloadBtn);
    }

    public void deleteUploadedFileBtn(){
        elementClick(this.deleteFileBtn);
    }

    public  boolean isFileRowDeleted(WebDriver driver){
        return  isDeletedRow(driver, "fileTable");
    }
}
