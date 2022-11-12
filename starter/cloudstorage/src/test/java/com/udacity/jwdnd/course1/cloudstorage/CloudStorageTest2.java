package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CloudStorageTest2 {

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
    @DisplayName("Create a note and verify it is displayed.")
    public void testNotes() throws InterruptedException {
        signupUser();
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.createNote(noteDetails()[0], noteDetails()[1]);
        Assertions.assertTrue(homePage.isSuccess());
        homePage.displayNotesTab();
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isNoteDisplayed(driver, noteDetails()[0], noteDetails()[1]));
    }

    @Test
    @Order(2)
    @DisplayName("Verify edited Note.")
    public void testNoteEdit() throws InterruptedException {
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayNotesTab();
        Thread.sleep(2000);
        homePage.clickEditNoteBtn();
        Assertions.assertNotNull(homePage.getNoteTitle());
        Assertions.assertNotNull(homePage.getNoteDescription());
        homePage.editNote(noteDetails()[2],noteDetails()[3]);
        Assertions.assertTrue(homePage.isSuccess());
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isNoteDisplayed(driver, noteDetails()[2], noteDetails()[3]));
    }

    @Test
    @Order(3)
    @DisplayName("Verify deleted Note.")
    public void testNoteDelete() throws InterruptedException {
        getAuthenticated(driver);
        HomePage homePage = new HomePage(driver);
        homePage.displayNotesTab();
        Thread.sleep(2000);
        homePage.deleteNote();
        Assertions.assertTrue(homePage.isSuccess());
        Thread.sleep(2000);
        Assertions.assertTrue(homePage.isNoteRowDeleted(driver));

    }


    private String[] noteDetails(){
        String[] notes = new String[4];
        notes[0] = "Data warehouses";
        notes[1] = "They are used for storing historic " +
                "data so has to help businesses make informed decisions.";
        notes[2] = "Love";
        notes[3] = "God's demonstrated His love through Jesus Christ";
        return notes;
    }


}
