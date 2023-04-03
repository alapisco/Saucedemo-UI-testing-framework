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
 * CartPage class represents the cart page of the application.
 * It provides methods to interact with the cart page using the Page Object and Page Factory design patterns.
 */
public class CartPage extends BasePage {


    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_list .cart_item")
    private List<WebElement> cartItemsWebElements;

    private List<Item> cartItems;

    /**
     * Constructor to set the WebDriver instance and initialize the page elements.
     *
     * @param driver the WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Retrieves a list of Item objects for all the items in the cart.
     *
     * @return a list of Item objects
     */
    public List<Item> getCartItems() {
        List<Item> items = new ArrayList<>();

        for (WebElement item : cartItemsWebElements) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            String description = item.findElement(By.className("inventory_item_desc")).getText();
            String price = item.findElement(By.className("inventory_item_price")).getText();
            String quantity = item.findElement(By.className("cart_quantity")).getText();
            String buttonText = item.findElement(By.tagName("button")).getText();

            items.add(new Item.Builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .buttonText(buttonText)
                    .quantity(quantity)
                    .build()
            );
        }

        cartItems = items;
        return cartItems;
    }



    /**
     * Get the index of the inventory item by its name.
     *
     * @param itemName The name of the inventory item to find.
     * @return The index of the item in the inventory list.
     * @throws Exception If the item name is not found in the list.
     */
    private int getItemIndexByName(String itemName) throws Exception {
        for (int i = 0; i < cartItemsWebElements.size(); i++) {
            WebElement item = cartItemsWebElements.get(i);
            WebElement itemNameElement = item.findElement(By.cssSelector(".inventory_item_name"));
            String currentItemName = itemNameElement.getText();
            if (currentItemName.equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        throw new Exception("Item not found: " + itemName);
    }

    /**
     * Clicks on the remove button of a cart item by index.
     * Waits for the button to be clickable with a timeout of 20 seconds.
     *
     * @param itemName the name if the item to click its button
     *
     * @throws Exception If the item name is not found in the list.
     */
    public void clickItemRemoveButton(String itemName) throws Exception {
        int itemIndex = getItemIndexByName(itemName);
        WebElement item = cartItemsWebElements.get(itemIndex);
        WebElement itemRemoveButton = item.findElement(By.tagName("button"));
        clickWhenClickable(itemRemoveButton);
        getCartItems();
    }

}
