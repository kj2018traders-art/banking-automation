package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.CustomerPage;

public class TransactionTest extends BaseTest {

    @Test
    public void depositAndWithdrawTest() {

        CustomerPage customer = new CustomerPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        SoftAssert soft = new SoftAssert();

        // ======================
        // 🔐 LOGIN
        // ======================
        customer.clickCustomerLogin();
        customer.selectCustomer();
        customer.clickLogin();

        // ======================
        // 💰 DEPOSIT
        // ======================
        int depositAmount = 1000;

        customer.clickDeposit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='amount']")));

        int balanceBeforeDeposit = getBalance();

        customer.enterAmount(String.valueOf(depositAmount));
        customer.submitDeposit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[2]")));

        int balanceAfterDeposit = getBalance();

        soft.assertEquals(balanceAfterDeposit,
                balanceBeforeDeposit + depositAmount,
                "Deposit failed!");

        // ======================
        // 💸 WITHDRAW
        // ======================
        String[] withdrawAmounts = {"100", "200", "-50"};

        for (String amount : withdrawAmounts) {

            customer.clickWithdraw();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='amount']")));

            int balanceBeforeWithdraw = getBalance();

            try {
                customer.enterAmount(amount);
                customer.submitWithdraw();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[2]")));

                int balanceAfterWithdraw = getBalance();

                if (isValidAmount(amount) && Integer.parseInt(amount) <= balanceBeforeWithdraw) {

                    soft.assertEquals(balanceAfterWithdraw,
                            balanceBeforeWithdraw - Integer.parseInt(amount),
                            "Valid withdraw failed");

                } else {

                    soft.assertEquals(balanceAfterWithdraw,
                            balanceBeforeWithdraw,
                            "Invalid withdraw affected balance");
                }

            } catch (Exception e) {
                if (isValidAmount(amount)) {
                    soft.fail("Valid withdraw failed: " + amount);
                }
            }
        }

        // ======================
        // 📊 TRANSACTIONS + PRINT
        // ======================
        customer.clickTransactions();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//table/tbody/tr"), 0));

        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

        System.out.println("\n===== TRANSACTION HISTORY =====");

        int creditCount = 0;
        int debitCount = 0;

        for (WebElement row : rows) {

            String date = row.findElement(By.xpath("td[1]")).getText();
            String amount = row.findElement(By.xpath("td[2]")).getText();
            String type = row.findElement(By.xpath("td[3]")).getText();

            // 🔥 PRINT CLEAN FORMAT
            System.out.println("Date: " + date +
                    " | Amount: " + amount +
                    " | Type: " + type);

            if (type.equalsIgnoreCase("Credit")) creditCount++;
            if (type.equalsIgnoreCase("Debit")) debitCount++;
        }

        soft.assertTrue(rows.size() >= 3, "Transactions not recorded properly");
        soft.assertEquals(creditCount, 1, "Credit count mismatch");
        soft.assertEquals(debitCount, 2, "Debit count mismatch");

        // ======================
        // 🔙 BACK BUTTON
        // ======================
        customer.clickBack();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='center']//strong[2]")));

        soft.assertFalse(driver.getCurrentUrl().contains("transactions"),
                "Back button failed");

        // ======================
        // 🔄 RESET + BUG CHECK
        // ======================
        int balanceBeforeReset = getBalance();
        System.out.println("\nBalance Before Reset: " + balanceBeforeReset);

        customer.clickTransactions();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//table/tbody/tr"), 0));

        customer.clickReset();

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//table/tbody/tr"), 0));

        // 🔙 Back to Account page
        customer.clickBack();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='center']//strong[2]")));

        int balanceAfterReset = getBalance();
        System.out.println("Balance After Reset: " + balanceAfterReset);

        // 🚨 BUG DETECTION
        if (balanceAfterReset == 0) {
            soft.fail("🚨 BUG: Reset button incorrectly sets balance to 0!");
        }

        soft.assertEquals(balanceAfterReset, balanceBeforeReset,
                "Reset should NOT change balance!");

        // ======================
        // 🏠 HOME BUTTON TEST
        // ======================
        customer.clickHome();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Customer Login')]")
        ));

        soft.assertTrue(
                driver.findElement(By.xpath("//button[contains(text(),'Customer Login')]")).isDisplayed(),
                "Home button failed"
        );

        System.out.println("PASS ✅ Home button working");

        // ======================
        // ✅ FINAL ASSERT
        // ======================
        soft.assertAll();
    }

    // ======================
    // 🔧 UTIL METHODS
    // ======================

    public int getBalance() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String text = wait.until(driver ->
                driver.findElement(By.xpath("//div[@class='center']//strong[2]")).getText());

        return Integer.parseInt(text.trim());
    }

    public boolean isValidAmount(String str) {
        try {
            return Integer.parseInt(str) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}