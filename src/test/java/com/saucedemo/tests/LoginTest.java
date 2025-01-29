package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ExtentReports extent;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        ExtentReportManager.setTest(test);
        
        driver = WebDriverManager.getDriver();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        ExtentTest test = ExtentReportManager.getTest();
        
        try {
            test.info("Navigating to Sauce Demo website");
            loginPage.navigateTo();

            test.info("Attempting to login with standard user");
            loginPage.login("standard_user", "secret_sauce");

            test.info("Verifying successful login");
            Assert.assertTrue(productsPage.isDisplayed(), "Products page should be displayed after login");
            Assert.assertEquals(productsPage.getPageTitle(), "Products", "Page title should be 'Products'");
            
            test.pass("Login test completed successfully");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed: " + result.getName());
            test.log(Status.FAIL, "Test Case Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed: " + result.getName());
        }
        
        WebDriverManager.quitDriver();
    }

    @AfterSuite
    public void teardownReport() {
        extent.flush();
    }
} 