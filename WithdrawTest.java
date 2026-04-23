package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CustomerPage;

public class WithdrawTest extends BaseTest {

    @Test
    public void withdrawValidAndInvalidTest() {

        CustomerPage customer = new CustomerPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Login
        customer.clickCustomerLogin();
        customer.selectCustomer();
        customer.clickLogin();

        // ------------------ STEP 1: DEPOSIT ------------------
        int depositAmount = 1000;

        customer.clickDeposit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='amount']")));

        int balanceBeforeDeposit = getBalance();

        customer.enterAmount(String.valueOf(depositAmount));
        customer.submitDeposit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[2]")));

        int balanceAfterDeposit = getBalance();

        Assert.assertEquals(balanceAfterDeposit,
                balanceBeforeDeposit + depositAmount,
                "Deposit failed!");

        System.out.println("Initial Balance after deposit: " + balanceAfterDeposit);

        // ------------------ STEP 2: WITHDRAW ------------------
        String[] withdrawAmounts = {"100", "200", "abc", "1500", "-50"};

        for (String amount : withdrawAmounts) {

            System.out.println("\n--- Withdraw Test: " + amount + " ---");

            customer.clickWithdraw();

            // WAIT for withdraw input
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='amount']")));

            int balanceBeforeWithdraw = getBalance();
            System.out.println("Balance before: " + balanceBeforeWithdraw);

            try {
                customer.enterAmount(amount);
                customer.submitWithdraw();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[2]")));

                int balanceAfterWithdraw = getBalance();
                System.out.println("Balance after: " + balanceAfterWithdraw);

                // ✅ VALID CASE
                if (isValidAmount(amount) && Integer.parseInt(amount) <= balanceBeforeWithdraw) {

                    Assert.assertEquals(balanceAfterWithdraw,
                            balanceBeforeWithdraw - Integer.parseInt(amount),
                            "Valid withdraw failed for: " + amount);

                    System.out.println("PASS ✅ Valid withdraw");
                }
                // ❌ INVALID CASE
                else {

                    Assert.assertEquals(balanceAfterWithdraw,
                            balanceBeforeWithdraw,
                            "Invalid withdraw affected balance: " + amount);

                    System.out.println("PASS ✅ Invalid withdraw handled");
                }

            } catch (Exception e) {

                if (isValidAmount(amount)) {
                    Assert.fail("Valid withdraw failed due to exception: " + amount);
                } else {
                    System.out.println("Handled invalid input: " + amount);
                }
            }
        }

        System.out.println("===== WITHDRAW TEST COMPLETED =====");
    }

    // ✅ Validate amount
    public boolean isValidAmount(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Get balance
    public int getBalance() {
        return Integer.parseInt(driver.findElement(By.xpath("//strong[2]")).getText());
    }
}