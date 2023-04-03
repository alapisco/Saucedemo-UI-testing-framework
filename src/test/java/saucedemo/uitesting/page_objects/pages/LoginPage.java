package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage class represents the login page of the application.
 * It provides methods to interact with the login page using the Page Object and Page Factory design patterns.
 */
public class LoginPage extends BasePage {

    // Locators
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = ".error-message-container.error")
    private WebElement errorMessageContainer;

    /**
     * Constructor to set the WebDriver instance and initialize the PageFactory elements.
     *
     * @param driver the WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates to the login page: https://www.saucedemo.com/
     *
     */
    public void visit(){
        visit("https://www.saucedemo.com/");
    }

    /**
     * Enters the username into the username field.
     *
     * @param username the username to enter
     */
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    /**
     * Enters the password into the password field.
     *
     * @param password the password to enter
     */
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    /**
     * Clicks the Login button and navigates to the Inventory page.
     *
     * @return an instance of InventoryPage
     */
    public InventoryPage clickLoginButton() {
        clickWhenClickable(loginButton);
        return new InventoryPage(driver);
    }

    /**
     * Performs the login action using the specified username and password.
     *
     * @param username the username to enter
     * @param password the password to enter
     *
     * @return an instance of InventoryPage
     */
    public InventoryPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    /**
     * Gets the error message text displayed on the login page.
     *
     * @return the error message text, or an empty string if the error message container is not visible
     */
    public String getErrorMessage() {
        if (errorMessageContainer.isDisplayed()) {
            return errorMessageContainer.getText();
        } else {
            return "";
        }
    }
}
