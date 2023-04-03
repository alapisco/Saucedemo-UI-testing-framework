package saucedemo.uitesting.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saucedemo.uitesting.models.Item;
import saucedemo.uitesting.page_objects.pages.CartPage;
import saucedemo.uitesting.page_objects.pages.InventoryPage;
import saucedemo.uitesting.page_objects.sections.HeaderSection;
import saucedemo.uitesting.utils.ScreenshotUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains test cases related to the cart page functionality.
 */
public class CartTests extends BaseTest{

    private InventoryPage inventoryPage;
    private HeaderSection headerSection;
    private CartPage cartPage;
    private List<Item> selectedInventoryItems;

    /**
     * Set up method for CartTests. Inherits from BaseTest and performs additional setup.
     * @throws Exception if there's any issue during the setup.
     */
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        loadTestData("src/test/resources/purchase_flow_data.json");

        // Go to  login page
        loginPage.visit();

        // Get login credentials from test data file
        String username = testData.getUsername();
        String password = testData.getPassword();

        // Login and get to the Inventory page
        inventoryPage = loginPage.login(username, password);

        inventoryPage.selectItemToPurchase("Sauce Labs Bike Light");
        inventoryPage.selectItemToPurchase("Sauce Labs Bolt T-Shirt");
        inventoryPage.selectItemToPurchase("Sauce Labs Onesie");
        inventoryPage.selectItemToPurchase("Test.allTheThings() T-Shirt (Red)");

        ScreenshotUtils.takeScreenshot(driver, "Selected Items");

        selectedInventoryItems = inventoryPage.getSelectedInventoryItems();

        headerSection = new HeaderSection(driver);
        cartPage = headerSection.clickCartIcon();
    }

    /**
     * Test that items in the cart preserve their selection order.
     */
    @Test
    public void itemsPreserveSelectionOrder(){

        List<Item> cartItems = cartPage.getCartItems();

        // Check that the items selected in the inventory page, also appear in the
        // cart page with the selection order

        for (int i = 0; i < selectedInventoryItems.size(); i++) {
            Item inventoryItem = selectedInventoryItems.get(i);
            Item cartItem = cartItems.get(i);
            assertAll("Verify items in the cart page",
                    () -> assertEquals(inventoryItem.getName(), cartItem.getName(), "Names should match"),
                    () -> assertEquals(inventoryItem.getDescription(), cartItem.getDescription(), "Descriptions should match"),
                    () -> assertEquals(inventoryItem.getPrice(), cartItem.getPrice(), "Prices should match")
            );
        }

        ScreenshotUtils.takeScreenshot(driver, "Cart Items");

    }

    /**
     * Test removing items from the cart.
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void removeItemsFromCart() throws Exception {
        List<Item> cartItems = cartPage.getCartItems();

        // Remove the first 2 items in the cart
        String itemName1 = cartItems.get(0).getName();
        String itemName2 = cartItems.get(1).getName();
        cartPage.clickItemRemoveButton(itemName1);
        cartPage.clickItemRemoveButton(itemName2);

        // Check that 2 items are displayed in cart icon
        assertEquals("2", headerSection.getNumberOfItemsInCart());

        ScreenshotUtils.takeScreenshot(driver, "Removed Items");


        // Check that the removed items are not displayed in the cart page
        cartItems = cartPage.getCartItems();
        assertEquals(2, cartItems.size());
        for (Item cartItem:cartItems) {
            String cartItemName = cartItem.getName();
            // cart items remaining should not be the removed items name
            assertFalse(cartItemName.equals(itemName1));
            assertFalse(cartItemName.equals(itemName2));
        }

    }

}
