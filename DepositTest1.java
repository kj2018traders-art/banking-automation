package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CustomerPage;

public class DepositTest1 extends BaseTest {

    @Test
    public void depositMoneyTest() {

        System.out.println("===== DEPOSIT TEST STARTED =====");

        CustomerPage customer = new CustomerPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Login
        customer.clickCustomerLogin();
        customer.selectCustomer();
        customer.clickLogin();

        System.out.println("Logged in as customer");

        // Test data (valid + invalid)
        String[] amounts = {"100", "0200", "300", "abc", "100 200", "100,200", "-100", "100.5","666666666688888"};

        for (String amount : amounts) {

            System.out.println("\n--- Testing amount: " + amount + " ---");

            customer.clickDeposit();

            int balanceBefore = getBalance();
            System.out.println("Balance before: " + balanceBefore);

            try {

                // Enter value
                customer.enterAmount(amount);

                // Submit deposit
                customer.submitDeposit();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[2]")));

                int balanceAfter = getBalance();
                System.out.println("Balance after: " + balanceAfter);

                // ✅ VALID CASE
                if (isValidAmount(amount)) {

                    int expected = balanceBefore + Integer.parseInt(amount);

                    Assert.assertEquals(balanceAfter, expected,
                            "Balance not updated correctly for valid amount: " + amount);

                    System.out.println("PASS ✅ Valid deposit");

                } 
                // ❌ INVALID CASE
                else {

                    Assert.assertEquals(balanceAfter, balanceBefore,
                            "Invalid input changed balance: " + amount);

                    System.out.println("PASS ✅ Invalid input handled correctly");
                }

            } catch (Exception e) {

                // ✅ ONLY FAIL if valid input throws error
                if (isValidAmount(amount)) {
                    Assert.fail("Valid input failed: " + amount);
                } else {
                    System.out.println("Handled invalid input with exception: " + amount);
                }
            }
        }
    }

    // ✅ Get balance from UI
    public int getBalance() {
        String balanceText = driver.findElement(By.xpath("//strong[2]")).getText();
        return Integer.parseInt(balanceText);
    }
    public boolean isValidAmount(String amount) {
        try {
            int value = Integer.parseInt(amount.trim());
            return value > 0;
        } catch (Exception e) {
            return false;
        }
    }
}