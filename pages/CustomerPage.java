package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


  public class CustomerPage {
		WebDriver driver;

		//Constructor
 public CustomerPage(WebDriver driver){
		this.driver = driver;
		}
 
		By customerLoginBtn1 = By.xpath("//button[text()='Customer Login']");
		By userDropdown1 = By.id("userSelect");
		By loginBtn1 = By.xpath("//button[text()='Login']");
		By logoutBtn = By.xpath("//button[text()='Logout']");
		By accountDropdown = By.id("accountSelect");
		By welcomeText = By.xpath("//span[contains(@class,'fontBig')]");
		
		// Deposit
	    By depositBtn = By.xpath("//button[contains(text(),'Deposit')]");
	    By amountField = By.xpath("//input[@placeholder='amount' and not(@ng-hide='true')]");
	    By depositSubmit = By.xpath("//button[@type='submit']");
	    By depositMsg = By.xpath("//span[contains(text(),'Deposit Successful')]");
	    By errorMsg = By.xpath("//span[contains(text(),'Please fill in this feild')]");


	    // Withdraw
	    By withdrawBtn =By.xpath("//button[contains(text(),'Withdrawl')]");
	    By withdrawSubmitBtn = By.xpath("//button[contains(text(),'Withdraw') and @type='submit']");
	    By withdrawMsg = By.xpath("//span[@ng-show='message']");
	   
	    
	 // Transactions Tab
	    By transactionsTab = By.xpath("//button[contains(text(),'Transactions')]");

	    // Table Rows
	    By transactionRows = By.xpath("//table/tbody/tr");

	    // Reset Button
	    By resetBtn = By.xpath("//button[text()='Reset']");

	    // Back Btton
	    By backBtn = By.xpath("//button[text()='Back']");

	    // Transaction Type Column (Credit/Debit)
	    By transactionType = By.xpath("//table/tbody/tr/td[2]");

		public void clickCustomerLogin(){
		    driver.findElement(customerLoginBtn1).click();
		}

		public void selectCustomer(){
		    driver.findElement(userDropdown1).sendKeys("Harry Potter");
		}

		public void clickLogin(){
		    driver.findElement(loginBtn1).click();
		}
		public void clickLogout() {

		    driver.findElement(By.xpath("//button[text()='Logout']")).click();

		    // ✅ Wait for dropdown instead of Customer Login button
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userSelect")));
		}
		
//		
		public String getWelcomeText(){
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			    return wait.until(
			        ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText();
		}
		
		public boolean isAccountDropdownDisplayed(){
		    return driver.findElement(accountDropdown).isDisplayed();
		}
		
		public void clickDeposit() {
	        driver.findElement(depositBtn).click();
	    }

	    public void enterAmount(String amount) {
	    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	    WebElement field = wait.until(
	    	        ExpectedConditions.visibilityOfElementLocated(
	    	            By.xpath("//input[@placeholder='amount' and not(contains(@class,'ng-hide'))]")
	    	        )
	    	    );

	    	    field.clear();
	    	    field.sendKeys(amount);

	    	    // ✅ Verify correct field is used
	    	    String entered = field.getAttribute("value");
	    	    System.out.println("Entered value: " + entered);

	    	    if (!entered.equals(amount)) {
	    	        throw new RuntimeException("Value not entered correctly!");
	    	    }
	    	    
	    }

	    public void submitDeposit() {
	        driver.findElement(depositSubmit).click();
	    }

	    public boolean isLogoutDisplayed() {
	    	
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	    return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutBtn)).isDisplayed();
	    }

	    public void selectAccount(String accountNumber) {

	        Select acc = new Select(driver.findElement(accountDropdown));
	        acc.selectByVisibleText(accountNumber);
	        System.out.println("Selected Account: " + accountNumber);

	    }
	    
	    public void selectAllAccounts() throws Exception {

	        Select acc = new Select(driver.findElement(accountDropdown));

	        List<WebElement> accounts = acc.getOptions();

	        for(int i=0; i<accounts.size(); i++) {

	            acc.selectByIndex(i);
	            Thread.sleep(2000);       
	            System.out.println("Selected Account: " + accounts.get(i).getText());
	        }
	    }
	    
	    public String getDepositMessage(){

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        return wait.until( ExpectedConditions.visibilityOfElementLocated(depositMsg)).getText();
	    }
	    
	    public String getErrorMessage(){

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        return wait.until( ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText();
	    }
	    
	    public void clickWithdraw(){
	    	 WebElement btn = driver.findElement(By.xpath("//button[contains(text(),'Withdrawl')]"));
	    	    btn.click();

	    	    System.out.println("Clicked Withdraw tab");

	    	    // ✅ VERIFY tab switched
	    	    WebElement label = driver.findElement(By.xpath("//label[contains(text(),'Withdrawn')]"));
	    	    if (!label.isDisplayed()) {
	    	        throw new RuntimeException("Withdraw tab not activated!");
	    	    }
	    }

	  

	    // Submit withdraw
	    public void submitWithdraw(){
	        driver.findElement(withdrawSubmitBtn).click();   // ✅ FIXED
	        System.out.println("Button clicked");
	    }

	    // Get message
	    public String getWithdrawMessage(){

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        return wait.until(
	            ExpectedConditions.visibilityOfElementLocated(withdrawMsg)).getText();
	    }
	    
	    public String getTransactionMessage() {
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        WebElement msg = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[contains(@class,'error')]")));

	        return msg.getText();
	    }
	    
	    
	    public void clickTransactions() {
	        driver.findElement(transactionsTab).click();
	    }

	    public int getTransactionCount() {
	        return driver.findElements(transactionRows).size();
	    }

	    public String getTransactionType(int index) {
	        return driver.findElements(transactionType).get(index).getText();
	    }

	    public void clickReset() {
	        driver.findElement(resetBtn).click();
	    }

	    public void clickBack() {
	        driver.findElement(backBtn).click();
	    }
	    
	    public void clickHome() {
	        driver.findElement(By.xpath("//button[contains(text(),'Home')]")).click();
	    }
	   }







