package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CloudStorageTest3 {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach(){
        if(this.driver != null){
            driver.quit();
        }
    }

    private void pageLoad(String page){
        driver.get("http://localhost:" + this.port + "/" + page);
    }

    private void signupUser(){
        pageLoad("signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signupSubmission("nanu", "nedu", "nanunedu", "password");
    }

    private void getAuthenticated(WebDriver driver){
        pageLoad("login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSubmission("nanunedu", "password");
    }

    @Test
    @Order(1)
    @DisplayName("Test that shows a set of credentials can be created and displayed on the table with their password is encrypted ")
    public void testCredentialCreation() throws InterruptedException {
        signupUser();
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayCredentialTab();
        homePage.openModalForCredential();
        homePage.createCredentials(credentialDetails()[0],credentialDetails()[1], credentialDetails()[2]);
        Assertions.assertTrue(homePage.isSuccess());
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isCredentialDisplayed(driver, credentialDetails()[0], credentialDetails()[1]));
        Assertions.assertTrue(homePage.isPasswordEncrypted(driver, credentialDetails()[2]));
    }

    @Test
    @Order(2)
    @DisplayName("Editing existing credentials test")
    public void testCredentialEdit() throws InterruptedException {
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayCredentialTab();
        homePage.clickCredentialEditBtn();
        Thread.sleep(2000);
        Assertions.assertNotNull(homePage.getUrlField());
        Assertions.assertNotNull(homePage.getUsernameField());
        Assertions.assertNotNull(homePage.getPasswordField());
        homePage.editCredential(credentialDetails()[3], credentialDetails()[4], credentialDetails()[5]);
        Assertions.assertTrue(homePage.isCredentialDisplayed(driver, credentialDetails()[3], credentialDetails()[4]));
        Assertions.assertTrue(homePage.isPasswordEncrypted(driver, credentialDetails()[5]));
    }

    @Test
    @Order(3)
    @DisplayName("Deleting existing credentials test")
    public void testCredentialDelete() throws InterruptedException {
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayCredentialTab();
        Thread.sleep(2000);
        homePage.deleteCredential();
        Assertions.assertTrue(homePage.isSuccess());
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isCredentialRowDeleted(driver));
    }

    @Test
    @Order(4)
    @DisplayName("File upload test")
    public void testFileUpload() throws InterruptedException {
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayFileTab();
        homePage.fileSelectionBtn();
        Thread.sleep(2000);
        homePage.uploadFile();
        Assertions.assertTrue(homePage.isSuccess());
        homePage.downloadBtnClick();
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isFileDownloaded(driver));
        homePage.deleteUploadedFileBtn();
        Assertions.assertTrue(homePage.isFileRowDeleted(driver));
    }

    private String[] credentialDetails(){
        String[] credential = new String[6];
        credential[0] = "https://www.yahoomail.com";
        credential[1] = "greatuser";
        credential[2] = "d@t@w@r3h0uz3";
        credential[3] = "https://www.ymail.com";
        credential[4] = "blessedman";
        credential[5] = "gr@t3fu77";
        return credential;
    }



}
