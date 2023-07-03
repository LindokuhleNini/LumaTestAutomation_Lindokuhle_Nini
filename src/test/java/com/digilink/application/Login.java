package com.digilink.application;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.digilink.utilities.ActionHelper;
import lombok.experimental.Helper;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login {

    private WebDriver driver = Test.driver;

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
        Test.test.log(Status.INFO, "Navigate to login page");
        driver.get(url);

        signIn.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/");
    }

    public void readCaptcha() {
        //String captchaText = "";
        //File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try{
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Step 4: Save and preprocess the CAPTCHA image
            BufferedImage captchaImage = ImageIO.read(screenshot);
            File captchaFile = new File("captcha.png");
            ImageIO.write(captchaImage, "png", captchaFile);
            // Step 5: Read the CAPTCHA using an OCR library (e.g., Tesseract)
            String captchaText = extractCaptchaText(captchaFile);

            // Get the location and size of the element
            int elementX = captcha.getLocation().getX();
            int elementY = captcha.getLocation().getY();
            int elementWidth = captcha.getSize().getWidth();
            int elementHeight = captcha.getSize().getHeight();

            // Crop the full image to capture only the element
            ///BufferedImage captchaImage = fullImage.getSubimage(elementX, elementY, elementWidth, elementHeight);

            // Save the cropped image as an image file
            //File captchaFile = new File("captcha.png");
            ImageIO.write(captchaImage, "png", captchaFile);

            this.captchaField.sendKeys(captchaText);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the extracted text

    }

    private String extractCaptchaText(File captchaFile) {
        // Create an instance of the Tesseract OCR engine
        ITesseract tesseract = new Tesseract();

        try {
            // Set the path to the tessdata directory containing language data files
            tesseract.setDatapath("src/test/java/com/digilink/data");

            // Set the language for OCR (if different from English, specify the appropriate language code)
            tesseract.setLanguage("eng");

            // Perform OCR on the captcha image file
            String captchaText = tesseract.doOCR(captchaFile);

            // Return the extracted captcha text
            return captchaText.trim();

        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return null; // Return null in case of an error
    }

    public void captureDetailsAndSubmit(){
        Test.test.log(Status.INFO, "Capturing login credentials and submit");
        String email = "roni_cost@example.com";
        String password = "roni_cost3@example.com";

        this.email.sendKeys(email);
        this.password.sendKeys(password);
        Test.test.addScreenCaptureFromPath(ActionHelper.takeScreenshot(Test.driver));
        this.loginBtn.click();

        if (captcha.isDisplayed()){
            this.email.sendKeys(email);
            this.password.sendKeys(password);
            this.readCaptcha();
            this.loginBtn.click();
        }

    }

}
