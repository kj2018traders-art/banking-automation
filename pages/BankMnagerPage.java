package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BankMnagerPage {

	 WebDriver driver;

	    public BankMnagerPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    // Locators
	    By bankManagerLoginBtn = By.xpath("//button[contains(text(),'Bank Manager Login')]");
	    By addCustomerBtn = By.xpath("//button[contains(text(),'Add Customer')]");
	    By openAccountBtn = By.xpath("//button[contains(text(),'Open Account')]");
	    By customersBtn = By.xpath("//button[contains(text(),'Customers')]");

	    // Add Customer fields
	    By firstName = By.xpath("//input[@placeholder='First Name']");
	    By lastName = By.xpath("//input[@placeholder='Last Name']");
	    By postCode = By.xpath("//input[@placeholder='Post Code']");
	    By addCustomerSubmit = By.xpath("//button[@type='submit']");

	    // Open Account fields
	    By customerDropdown = By.id("userSelect");
	    By currencyDropdown = By.id("currency");
	    By processBtn = By.xpath("//button[contains(text(),'Process')]");
	    
	    By homeBtn = By.xpath("//button[contains(text(),'Home')]");

	    // Actions
	    public void clickBankManagerLogin() {
	        driver.findElement(bankManagerLoginBtn).click();
	    }

	    public void clickAddCustomer() {
	        driver.findElement(addCustomerBtn).click();
	    }

	    public void addCustomer(String fName, String lName, String pCode) {
	        driver.findElement(firstName).sendKeys(fName);
	        driver.findElement(lastName).sendKeys(lName);
	        driver.findElement(postCode).sendKeys(pCode);
	        driver.findElement(addCustomerSubmit).click();
	    }

	    public void clickOpenAccount() {
	        driver.findElement(openAccountBtn).click();
	    }

	    public void selectCustomer(String name) {
	        driver.findElement(customerDropdown).sendKeys(name);
	    }

	    public void selectCurrency(String currency) {
	        driver.findElement(currencyDropdown).sendKeys(currency);
	    }

	    public void clickProcess() {
	        driver.findElement(processBtn).click();
	    }

	    public void clickCustomers() {
	        driver.findElement(customersBtn).click();
	    }
	    
	    public boolean isCustomerPresent(String firstName, String lastName) {
	        String xpath = "//table//tr[td[text()='" + firstName + "'] and td[text()='" + lastName + "']]";
	        return driver.findElements(By.xpath(xpath)).size() > 0;
	    }
	    //print all customers
	    public void viewAllCustomersUsingPagination() {

	        System.out.println("===== VIEWING ALL CUSTOMERS =====");

	        int page = 1;

	        while (true) {

	            System.out.println("Page: " + page);

	            List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

	            for (WebElement row : rows) {
	                System.out.println(row.getText());
	            }

	            // Next button
	            List<WebElement> nextBtn = driver.findElements(By.xpath("//button[contains(text(),'Next')]"));

	            if (nextBtn.size() > 0 && nextBtn.get(0).isEnabled()) {
	                nextBtn.get(0).click();
	                page++;
	            } else {
	                break;
	            }
	        }
	    }
	    // delete customer
	    public void deleteCustomer(String firstName) {
	        String xpath = "//table/tbody/tr[td[text()='" + firstName + "']]//button[text()='Delete']";
	        driver.findElement(By.xpath(xpath)).click();
	        System.out.println("Deleted customer: " + firstName);
	    }
	    // search customer
	    public void searchCustomer(String name) {
	        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search Customer']"));
	        searchBox.clear();
	        searchBox.sendKeys(name);

	        System.out.println("Searching for: " + name);
	    }
	    
	    public boolean isSearchResultValid(String expectedName) {
	        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

	        for (WebElement row : rows) {
	            String name = row.getText().toLowerCase();
	            if (!name.contains(expectedName.toLowerCase())) {
	                return false;
	            }
	        }
	        return true;
	    }
	    public int getCustomerCount() {
	        return driver.findElements(By.xpath("//table/tbody/tr")).size();
	    }
	    // ================= HOME BUTTON =================

	    public void clickHomeButton() {
	        driver.findElement(homeBtn).click();
	        System.out.println("Clicked Home button");
	    }

	    // ================= VALIDATIONS =================

	    public boolean isOnHomePage() {
	        return driver.getCurrentUrl().contains("login");
	    }

	    public boolean isHomePageTitleDisplayed() {
	        return driver.getTitle().equals("XYZ Bank");
	    }

}
