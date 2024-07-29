package com.digilink.application;

import com.digilink.utilities.ActionHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class Home {

    private WebDriver driver = Tests.driver;

    @FindBy(xpath = "//*[@id=\"ui-id-5\"]/span[2]")
    WebElement menCategory;

    @FindBy(xpath = "//span[contains(text(), \"Tops\")]")
    WebElement topsCategory;

    @FindBy(xpath = "//span[contains(text(), \"Tees\")]")
    WebElement teesCategory;

    public void navigateToTees(){
        /*Actions actions = new Actions(driver);
        actions.moveToElement(menCategory).perform();
        actions.moveToElement(topsCategory).perform();
        this.teesCategory.click();*/
        this.menCategory.click();
    }

}
