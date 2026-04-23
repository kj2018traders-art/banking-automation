package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CustomerPage;

public class LoginAllTest  extends BaseTest {

	  @Test
	    public void loginAllCustomersTest() throws Exception {


		    CustomerPage customer = new CustomerPage(driver);
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Step 1: Click Customer Login
		    customer.clickCustomerLogin();

		    Select select = new Select(driver.findElement(By.id("userSelect")));
		    List<WebElement> allCustomers = select.getOptions();

		    // Loop through each customer (skip "Your Name")
		    for (int i = 1; i < allCustomers.size(); i++) {

		        // Re-fetch dropdown each iteration (IMPORTANT)
		        select = new Select(driver.findElement(By.id("userSelect")));
		        allCustomers = select.getOptions();

		        String customerName = allCustomers.get(i).getText();

		        System.out.println("\n===== Testing Login for: " + customerName + " =====");

		        // Select customer
		        select.selectByVisibleText(customerName);
		        customer.clickLogin();

		        // Wait for login success
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Logout']")));

		        // ✅ Validation 1: Welcome text (use contains)
		        String actualName = customer.getWelcomeText();
		        System.out.println("Welcome text: " + actualName);

		        Assert.assertTrue(actualName.contains(customerName),
		                "❌ Incorrect user logged in");

		        // ✅ Validation 2: Logout visible
		        Assert.assertTrue(customer.isLogoutDisplayed(),
		                "❌ Logout button not visible");

		        // ✅ Validation 3: URL check
		        Assert.assertTrue(driver.getCurrentUrl().contains("account"),
		                "❌ Not navigated to account page");

		        // ✅ Validation 4: Account dropdown visible
		        Assert.assertTrue(customer.isAccountDropdownDisplayed(),
		                "❌ Account dropdown not visible");

		        // ✅ Optional: Validate accounts
		        customer.selectAllAccounts();

		        System.out.println("✅ Login successful for: " + customerName);

		        // Logout
		        customer.clickLogout();

		        // Wait until back to login page
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userSelect")));
		    }
		}
}