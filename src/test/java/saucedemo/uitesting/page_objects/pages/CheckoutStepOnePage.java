package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * CheckoutStepOnePage class represents the first step of the checkout process in the application.
 * It provides methods to interact with the Checkout Step One page using the Page Object and Page Factory design patterns.
 */
public class CheckoutStepOnePage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    /**
     * Constructor to set the WebDriver instance and initialize the page elements.
     *
     * @param driver the WebDriver instance
     */
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Enters the given first name, last name, and postal code into their respective input fields.
     *
     * @param firstName  the first name to enter
     * @param lastName   the last name to enter
     * @param postalCode the postal code to enter
     */
    public void enterInformation(String firstName, String lastName, String postalCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postalCodeInput.sendKeys(postalCode);
    }

    /**
     * Clicks the Continue button and navigates to the Checkout Step Two page.
     *
     * @return an instance of CheckoutStepTwoPage
     */
    public CheckoutStepTwoPage clickContinueButton() {
        clickWhenClickable(continueButton);
        return new CheckoutStepTwoPage(driver);
    }
}
