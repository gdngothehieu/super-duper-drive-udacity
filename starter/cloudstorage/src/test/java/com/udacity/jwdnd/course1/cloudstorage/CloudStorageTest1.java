package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageTest1 {

	@LocalServerPort
	private int port;

	private String baseUrl;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void pageLoad(String page){
		this.baseUrl = "http://localhost:" + this.port;
		driver.get(this.baseUrl + "/" + page);
	}

	@Test
	@Order(1)
	@DisplayName("An Unauthorized user can only access the login and signup pages")
	public void testAuthenticationPages() {
		pageLoad("login");
		Assertions.assertEquals("Login", driver.getTitle());

		pageLoad("signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		pageLoad("home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		pageLoad("result");
		Assertions.assertNotEquals("Result", driver.getTitle());
	}


	@Test
	@Order(2)
	@DisplayName("New user signs in, logs in, accesses home page, logs out and unable to access home page")
	public void testAuthorization() throws InterruptedException {
		pageLoad("signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signupSubmission("nanu", "nedu", "nanunedu", "password");
		Assertions.assertTrue(signupPage.isSuccessful());

		pageLoad("login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginSubmission("nanunedu", "password");
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(2000);

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		Assertions.assertTrue(loginPage.isLogout());
		Thread.sleep(2000);
		driver.get(baseUrl + "/home");
		Assertions.assertNotEquals(baseUrl + "/home", driver.getCurrentUrl());
	}


}
