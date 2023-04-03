package saucedemo.uitesting.page_objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import saucedemo.uitesting.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * InventoryPage class represents the inventory page of the application.
 * It provides methods to interact with the inventory page using the Page Object and Page Factory design patterns.
 */
public class InventoryPage extends BasePage {

    // Locators
    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItemsWebElements;

    private List<Item> inventoryItems;
    private List<Item> selectedItems = new ArrayList<>();

    /**
     * Constructor to set the WebDriver instance and initialize the PageFactory elements.
     *
     * @param driver the WebDriver instance
     */
    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
        getInventoryItems();
    }

    /**
     * Retrieves all inventory items displayed on the inventory page.
     *
     * @return a list of InventoryItem objects
     */
    public List<Item> getInventoryItems() {
        List<Item> items = new ArrayList<>();

        for (WebElement item : inventoryItemsWebElements) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            String description = item.findElement(By.cssSelector(".inventory_item_desc")).getText();
            String price = item.findElement(By.cssSelector(".inventory_item_price")).getText();
            String buttonText = item.findElement(By.tagName("button")).getText();

            items.add(new Item.Builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .buttonText(buttonText)
                    .build());
        }

        inventoryItems = items;
        return inventoryItems;
    }

    /**
     * Get the list of selected inventory items.
     *
     * @return a List of selected Items
     */
    public List<Item> getSelectedInventoryItems() {
        return selectedItems;
    }

    /**
     * Clicks on the button of an inventory item by index.
     * Waits for the button to be clickable with a timeout of 20 seconds.
     *
     * @param index the index of the item in the inventory list (0-based)
     */
    public void clickItemButtonByIndex(int index) {
        // clicking inventory item
        WebElement itemButton = inventoryItemsWebElements.get(index).findElement(By.tagName("button"));
        clickWhenClickable(itemButton);
        getInventoryItems();
    }

    /**
     * Clicks on the Add To Cart button of an inventory item
     * Waits for the button to be clickable with a timeout of 20 seconds.
     *
     * @param itemName the name of the item to click its button
     *
     */
    public void selectItemToPurchase(String itemName) throws Exception {
        int itemIndex = getItemIndexByName(itemName);
        WebElement item = inventoryItemsWebElements.get(itemIndex);
        WebElement itemNameElement = item.findElement(By.tagName("button"));
        String buttonText = itemNameElement.getText();
        if (buttonText.equals("Add to cart")){
            clickItemButtonByIndex(itemIndex);
            selectedItems.add(inventoryItems.get(itemIndex));
        }
    }

    /**
     * Clicks on the Remove button of an inventory item
     * Waits for the button to be clickable with a timeout of 20 seconds.
     *
     * @param itemName the name of the item to click its button
     *
     */
    public void deselectItemToPurchase(String itemName) throws Exception {
        int itemIndex = getItemIndexByName(itemName);
        WebElement item = inventoryItemsWebElements.get(itemIndex);
        WebElement itemNameElement = item.findElement(By.tagName("button"));
        String buttonText = itemNameElement.getText();
        if (buttonText.equals("Remove")){
            clickItemButtonByIndex(itemIndex);
            // remove from selected items
            int index = -1;
            for (int i = 0; i < selectedItems.size(); i++) {
                if (selectedItems.get(i).getName().equals(itemName)){
                    index = i;
                    break;
                }
            }
            selectedItems.remove(index);
        }
    }

    /**
     * Get the index of the inventory item by its name.
     *
     * @param itemName The name of the inventory item to find.
     * @return The index of the item in the inventory list.
     * @throws Exception If the item name is not found in the list.
     */
    private int getItemIndexByName(String itemName) throws Exception {
        for (int i = 0; i < inventoryItemsWebElements.size(); i++) {
            WebElement item = inventoryItemsWebElements.get(i);
            WebElement itemNameElement = item.findElement(By.cssSelector(".inventory_item_name"));
            String currentItemName = itemNameElement.getText();
            if (currentItemName.equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        throw new Exception("Item not found: " + itemName);
    }
}
