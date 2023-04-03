package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import saucedemo.uitesting.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * CheckoutStepTwoPage class represents the second step of the checkout process in the application.
 * It provides methods to interact with the Checkout Step Two page using the Page Object and Page Factory design patterns.
 */
public class CheckoutStepTwoPage extends BasePage {

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "cart_item")
    private List<WebElement> checkoutOverViewItemsWebElements;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    private List<Item> checkoutOverviewItems;

    /**
     * Constructor to set the WebDriver instance and initialize the page elements.
     *
     * @param driver the WebDriver instance
     */
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the Finish button on the Checkout Step Two page and navigates to the Checkout Complete page.
     *
     * @return A new instance of the CheckoutCompletePage.
     */
    public CheckoutCompletePage  clickFinishButton() {
        clickWhenClickable(finishButton);
        return new CheckoutCompletePage(driver);
    }

    /**
     * Retrieves a list of Item objects representing the items on the Checkout Step Two page.
     *
     * @return a list of Item objects
     */
    public List<Item> getCheckoutOverviewItems() {
        List<Item> items = new ArrayList<>();

        for (WebElement item : checkoutOverViewItemsWebElements) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            String description = item.findElement(By.className("inventory_item_desc")).getText();
            String price = item.findElement(By.className("inventory_item_price")).getText();
            String quantity = item.findElement(By.className("cart_quantity")).getText();

            items.add(new Item.Builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .build()
            );
        }

        checkoutOverviewItems = items;
        return checkoutOverviewItems;
    }

    /**
     * Retrieves the item total from the summary label.
     *
     * @return the item total as a double
     */
    public double getItemTotal() {
        String itemTotalText = itemTotalLabel.getText().replace("Item total: $", "");
        return Double.parseDouble(itemTotalText);
    }

    /**
     * Retrieves the tax from the summary label.
     *
     * @return the tax as a double
     */
    public double getTax() {
        String taxText = taxLabel.getText().replace("Tax: $", "");
        return Double.parseDouble(taxText);
    }

    /**
     * Retrieves the total from the summary label.
     *
     * @return the total as a double
     */
    public double getTotal() {
        String totalText = totalLabel.getText().replace("Total: $", "");
        return Double.parseDouble(totalText);
    }
}