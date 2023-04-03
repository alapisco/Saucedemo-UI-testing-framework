package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CheckoutCompletePage class represents the Checkout Complete page of the SauceDemo application.
 * It provides methods to interact with the elements on the page and retrieve their properties.
 */
public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the title text from the Checkout Complete page.
     *
     * @return A String containing the title text.
     */
    public String getTitleText() {
        return driver.findElement(By.cssSelector("span.title")).getText();
    }

    /**
     * Gets the header text from the Checkout Complete page.
     *
     * @return A String containing the header text.
     */
    public String getHeaderText() {
        return driver.findElement(By.cssSelector("h2.complete-header")).getText();
    }

    /**
     * Gets the order complete text from the Checkout Complete page.
     *
     * @return A String containing the order complete text.
     */
    public String getOrderCompleteText() {
        return driver.findElement(By.cssSelector("div.complete-text")).getText();
    }
}