package com.digilink.application;

import com.digilink.utilities.ActionHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home extends ActionHelper {

    private WebDriver driver;
    private By loginPageLink =  By.linkText("Sign In");

    public Home(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


}
