package com.digilink.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActionHelper {

    WebDriver driver;

    public ActionHelper(WebDriver driver){
        this.driver = driver;
    }

    public void clickElement(By element)
    {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void scroll(String y){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+y+")");
    }

    public WebDriver setupBrowser(String browser){

        switch (browser.toUpperCase()){
            case "CHROME", "GOOGLE CHROME" ->{
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }
            case "EDGE", "MICROSOFT EDGE" ->{
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static String takeScreenshot(WebDriver driver){
        try{

            String fileSeparator = System.getProperty("file.separator");
            String extentReportsPath = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "java" + fileSeparator + "reporting";
            String screenshotPath = extentReportsPath + fileSeparator + "screenshots";

            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotName = "screenshot_" + Math.random() + ".png";
            String screenshot = screenshotPath + fileSeparator + screenshotName;

            FileUtils.copyFile(file, new File(screenshot));
            return "." + fileSeparator + "screenshots" + fileSeparator + screenshotName;

        } catch (Exception e) {
            Assert.fail("test failed to take a screenshot" + e);
            throw new RuntimeException(e);
        }
    }
}
