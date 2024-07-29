package com.digilink.application;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
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
public class Tests {

    public static WebDriver driver;
    public static ExtentTest test;
    ExtentSparkReporter htmlReporter;
    ExtentReports extent;
    Login login;
    Home home;

    @Parameters("browser")
    @BeforeTest
    public void setUp(String browser) {
        //browser = "chrome";

        try {
            if(browser.equalsIgnoreCase("chrome")){
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-popup-blocking");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            } else if(browser.equalsIgnoreCase("edge")){
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else{
                throw new RuntimeException("Browser name '" + browser + "' could not be found.Check spelling");
            }
            driver.manage().window().maximize();
            login = PageFactory.initElements(driver, Login.class);
            home = PageFactory.initElements(driver, Home.class);

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
    public void validLoginTest(String url){
        test = extent.createTest("TC-001: invalid login test", "");
        login.navigateToLoginPage(url);
        login.captureDetailsAndSubmit();
    }

    @Parameters("url")
    @org.testng.annotations.Test(priority = 2, testName = "TC-002: invalid Login test")
    public void addTeesToCart(String url){
        test = extent.createTest("TC-001: invalid login test", "");
        home.navigateToTees();
    }

}
