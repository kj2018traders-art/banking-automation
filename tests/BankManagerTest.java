package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.BankMnagerPage;

public class BankManagerTest extends BaseTest {

    @Test
    public void bankManagerFlowTest() {

        System.out.println("===== TEST STARTED =====");

        BankMnagerPage manager = new BankMnagerPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Login
        System.out.println("Logging in as Bank Manager...");
        manager.clickBankManagerLogin();

        // ========== MULTIPLE CUSTOMERS ==========
        String[][] customers = {
                {"John", "Doe", "12345"},
                {"Jane", "Smith", "54321"},
                {"Mike", "Brown", "67890"},
                {"Sara", "Wilson", "98765"},
                {"Sara", "Wilson", "98765"} // duplicate
        };

        manager.clickAddCustomer();

        for (String[] cust : customers) {

            String firstName = cust[0];
            String lastName = cust[1];

            System.out.println("Adding Customer: " + firstName + " " + lastName);

            manager.addCustomer(firstName, lastName, cust[2]);

            String alertText = driver.switchTo().alert().getText();
            System.out.println("Alert: " + alertText);

            if (alertText.contains("Customer added successfully")) {
                System.out.println("Customer added successfully ✅");
                Assert.assertTrue(true);
            } 
            else if (alertText.toLowerCase().contains("duplicate")) {
                System.out.println("Duplicate customer detected ⚠️");
                Assert.assertTrue(true);
            } 
            else {
                Assert.fail("Unexpected alert message: " + alertText);
            }

            driver.switchTo().alert().accept();
        }

        // ========== MULTIPLE ACCOUNTS ==========
        String[][] accounts = {
                {"John Doe", "Dollar"},
                {"John Doe", "Rupee"},
                {"Jane Smith", "Pound"},
                {"Mike Brown", "Dollar"}
        };

        manager.clickOpenAccount();

        for (String[] acc : accounts) {

            System.out.println("Opening account for: " + acc[0] + " | " + acc[1]);

            manager.selectCustomer(acc[0]);
            manager.selectCurrency(acc[1]);
            manager.clickProcess();

            String alertText = driver.switchTo().alert().getText();
            System.out.println("Alert: " + alertText);

            Assert.assertTrue(alertText.contains("Account created successfully"),
                    "Account creation failed!");

            driver.switchTo().alert().accept();
        }

        // ========== CUSTOMER LIST ==========
        manager.clickCustomers();
        System.out.println("Opened Customer List");

        manager.viewAllCustomersUsingPagination();

        Assert.assertTrue(manager.isCustomerPresent("John", "Doe"));
        Assert.assertTrue(manager.isCustomerPresent("Jane", "Smith"));

        // ========== SEARCH ==========
        System.out.println("Searching for 'John'");
        manager.searchCustomer("John");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr")));

        boolean searchValid = manager.isSearchResultValid("John");
        System.out.println("Search result valid: " + searchValid);

        Assert.assertTrue(searchValid, "Search results incorrect!");

        // Clear search
        manager.searchCustomer("");

        // ========== DELETE ==========
        System.out.println("Deleting customer: Jane");
        manager.deleteCustomer("Jane");

        boolean isJanePresent = manager.isCustomerPresent("Jane", "Smith");
        System.out.println("Is Jane present after delete? " + isJanePresent);

        Assert.assertFalse(isJanePresent, "Customer not deleted!");

        // ========== COUNT ==========
        int total = manager.getCustomerCount();
        System.out.println("Total customers: " + total);

        Assert.assertTrue(total > 0, "Customer list is empty!");

        // ========== HOME VALIDATION ==========
        manager.clickHomeButton();

        Assert.assertTrue(manager.isOnHomePage(), "Home navigation failed!");
        Assert.assertTrue(manager.isHomePageTitleDisplayed(), "Incorrect page title!");

        System.out.println("Home button validation successful");
        System.out.println("===== TEST COMPLETED SUCCESSFULLY =====");
    }
}
