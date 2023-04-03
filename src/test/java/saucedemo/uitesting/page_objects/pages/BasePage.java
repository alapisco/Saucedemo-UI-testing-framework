package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage class represents a base class for all page classes.
 * It encapsulates common logic and provides methods for waiting for elements.
 */
public abstract class BasePage {

    protected WebDriver driver;

    /**
     * Constructor to set the WebDriver instance.
     *
     * @param driver the WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to a given url
     *
     * @param url the url to visit
     */
    public void visit(String url) {
        driver.get(url);
    }

    /**
     * Waits for an element to be visible using the specified locator and timeout.
     *
     * @param locator the locator for the element
     * @param timeout the timeout in seconds
     * @return the WebElement instance
     */
    protected WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be visible using the specified locator and a default timeout of 20 seconds.
     *
     * @param locator the locator for the element
     * @return the WebElement instance
     */
    protected WebElement waitForElement(By locator) {
        return waitForElement(locator, 20);
    }

    /**
     * Clicks on a WebElement after waiting for it to be clickable with a timeout of 20 seconds.
     *
     * @param element the WebElement to be clicked
     */
    protected void clickWhenClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
