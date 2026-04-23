package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public static WebDriver driver;

    @BeforeClass
    public void setup() {

        System.out.println("===== BROWSER SETUP START =====");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/");

        // ✅ VALIDATIONS
        Assert.assertNotNull(driver, "Driver is not initialized!");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("BankingProject"),
                "URL is incorrect!");

        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        Assert.assertEquals(title, "XYZ Bank", "Title mismatch!");

        System.out.println("===== BROWSER SETUP COMPLETED =====");
    }

    @AfterMethod
    public void tearDown() {

        System.out.println("===== TEST COMPLETED - CLOSING BROWSER =====");

        if (driver != null) {
            driver.quit();
        }
    }
}