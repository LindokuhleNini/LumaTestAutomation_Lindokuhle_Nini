package com.digilink.application;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for simple App.
 */
public class Test {

    public static WebDriver driver;
    public static ExtentTest test;
    ExtentSparkReporter htmlReporter;
    ExtentReports extent;
    Login login;

    @Parameters("browser")
    @BeforeTest
    public void setUp(String browser) {

        try {
            if(browser.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if(browser.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else{
                throw new RuntimeException("Browser name '" + browser + "' could not be found.Check spelling");
            }
            driver.manage().window().maximize();
            login = PageFactory.initElements(driver, Login.class);

            String fileSeparator = System.getProperty("file.separator");
            String file = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "java" + fileSeparator + "reporting" + fileSeparator + "LumaShopTestReport"
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".html";
            htmlReporter = new ExtentSparkReporter(file);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            htmlReporter.config().setDocumentTitle("LUMA Shop Automation Report");
            htmlReporter.config().setReportName("Shop QA Test Report");
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setTimeStampFormat("EEEE, MMMMM dd, yyyy, hh:mm a '(zzz)'");

        } catch (Exception e){
            Assert.fail("Something went wrong during setup" + e);
            throw new RuntimeException(e);
        }

    }

    @AfterMethod
    public void testResults(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS){
            test.log(Status.PASS, result.getTestName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
        } else{
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @Parameters("url")
    @org.testng.annotations.Test(priority = 1, testName = "TC-001: invalid Login test")
    public void invalidLoginTest(String url){
        test = extent.createTest("TC-001: invalid login test", "");

    }

}
