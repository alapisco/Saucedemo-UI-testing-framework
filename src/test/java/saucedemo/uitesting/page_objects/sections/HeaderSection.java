package saucedemo.uitesting.page_objects.sections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import saucedemo.uitesting.page_objects.pages.BasePage;
import saucedemo.uitesting.page_objects.pages.CartPage;

public class HeaderSection extends BasePage {

    // Locators
    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    /**
     * Constructor to set the WebDriver instance and initialize the PageFactory elements.
     *
     * @param driver the WebDriver instance
     */
    public HeaderSection(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    /**
     * Clicks the Cart icon and navigates to the Cart page.
     *
     * @return an instance of CartPage
     */
    public CartPage clickCartIcon() {
        clickWhenClickable(cartIcon);
        return new CartPage(driver);
    }

    /**
     * Gets the number of items added to the cart
     */
    public String getNumberOfItemsInCart() {
        return cartIcon.getText();
    }

}
