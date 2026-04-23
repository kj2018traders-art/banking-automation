# 🏦 Banking Automation Project (Selenium + TestNG)

## 📌 Project Overview

This project automates a banking web application using **Selenium WebDriver** and **TestNG**.
It validates core banking functionalities such as **customer login, deposit, withdrawal, balance validation, and transaction history**.

The automation framework follows the **Page Object Model (POM)** design pattern for better maintainability and scalability.

---

## 🌐 Application Under Test

GlobalSQA Banking Project
URL: https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login

---

## 🛠️ Tech Stack

* **Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Framework:** TestNG
* **Build Tool:** Maven
* **Design Pattern:** Page Object Model (POM)

---

## 📂 Project Structure

banking-automation/
│
├── tests/
│   ├── DepositTest.java
│   ├── WithdrawTest.java
│   ├── TransactionTest.java
│
├── pages/
│   ├── CustomerPage.java
│   ├── BankManagerPage.java
│
├── base/
│   ├── BaseTest.java
│└── testng.xml

---

## ✅ Test Scenarios Covered

### 🔐 Login

* Customer login with valid user
* Validation of successful login

### 💰 Deposit

* Deposit valid amount
* Verify success message: *"Deposit Successful"*
* Balance updated correctly

### 💸 Withdraw

* Withdraw valid amount
* Withdraw invalid amount (more than balance)
* Validate error message:
  *"Transaction Failed. You can not withdraw amount more than the balance."*

### 🔄 Transactions

* Perform deposit + withdraw flow
* Validate updated balance
* Verify transaction messages

---

## 🧪 Sample Test Flow

1. Login as Customer
2. Deposit amount (e.g., 1000)
3. Withdraw amount (valid & invalid)
4. Capture and validate messages
5. Verify final balance

---

## ▶️ How to Run the Project

### 1️⃣ Clone Repository

git clone https://github.com/kj2018traders-art/banking-automation.git
### 2️⃣ Open Project

Import into **Eclipse** 

### 3️⃣ Install Dependencies

mvn clean install

### 4️⃣ Run Tests

* Run `testng.xml`
  OR
* Right-click → Run as **TestNG Test**

---

## 📊 Assertions Used

* Verify success messages
* Validate error messages
* Check balance updates after transactions

---

## ⚠️ Important Notes

* Ensure **ChromeDriver** is installed and configured
* Use proper waits (`WebDriverWait`) to avoid timing issues
* Handle dynamic elements carefully

---

## 🚀 Future Enhancements

* Add **DataProvider** for multiple test data
* Integrate **Extent Reports** for reporting
* Add **CI/CD (Jenkins/GitHub Actions)**
* Cross-browser testing

---

## 👩‍💻 Author

Jeshma Veera Monteiro

---

