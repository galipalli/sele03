package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            
            // Add headless options if running in CI environment
            String chromeOptions = System.getenv("CHROME_OPTIONS");
            if (chromeOptions != null && !chromeOptions.isEmpty()) {
                for (String option : chromeOptions.split("\\s+")) {
                    options.addArguments(option);
                }
            }
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
} 