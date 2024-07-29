package com.digilink.application;

import com.aventstack.extentreports.Status;
import com.digilink.utilities.ActionHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class Login {

    private WebDriver driver = Tests.driver;

    @FindBy(id = "email")
    WebElement email;

    @FindBy(id = "pass")
    WebElement password;

    @FindBy(linkText = "Sign In")
    WebElement signIn;

    @FindBy(id = "send2")
    WebElement loginBtn;

    @FindBy(xpath = "//img[contains(@class, \"captcha-img\")]")
    WebElement captcha;

    @FindBy(id = "captcha_user_login")
    WebElement captchaField;

    public void navigateToLoginPage(String url){
        Tests.test.log(Status.INFO, "Navigate to login page");
        driver.get(url);

        signIn.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/");
    }

    public void captureDetailsAndSubmit(){
        Tests.test.log(Status.INFO, "Capturing login credentials and submit");
        String email = "tester@automation.com";
        String password = "Tester123";

        this.email.sendKeys(email);
        this.password.sendKeys(password);
        Tests.test.addScreenCaptureFromPath(ActionHelper.takeScreenshot(Tests.driver));
        this.loginBtn.click();

    }

}
