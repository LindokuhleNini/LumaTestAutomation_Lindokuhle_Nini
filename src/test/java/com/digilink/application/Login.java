package com.digilink.application;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.digilink.utilities.ActionHelper;
import lombok.experimental.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login extends ActionHelper {

    private WebDriver driver;
    ExtentTest test;
    private WebElement email =  driver.findElement(By.id("email"));
    private WebElement password =  driver.findElement(By.id("pass"));
    private WebElement loginBtn =  driver.findElement(By.id("send2"));

    public Login(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void captureDetailsAndSubmit(){
        Test.test.log(Status.INFO, "Capturing login credentials and submit");
        String email = "";
        String password = "";

        this.email.sendKeys(email);
        this.password.sendKeys(password);
        //Test.test.addScreenCaptureFromPath(Helper.takeScreenshot(Test.test));
    }

}
