package saucedemo.uitesting.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saucedemo.uitesting.models.Item;
import saucedemo.uitesting.page_objects.pages.*;
import saucedemo.uitesting.page_objects.sections.HeaderSection;
import saucedemo.uitesting.utils.ScreenshotUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseFlowTests extends BaseTest {

    /**
     * Set up method for PurchaseFlowTests.
     * @throws Exception if there's any issue during the test execution.
     */
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp(); // Call the parent setUp method
        loadTestData("src/test/resources/purchase_flow_data.json");
    }

    /**
     * Test the purchase flow from selecting items to order confirmation.
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void purchaseItems() throws Exception {

        // Go to  login page
        loginPage.visit();

        ScreenshotUtils.takeScreenshot(driver, "Login Page");

        // Get login credentials from test data file
        String username = testData.getUsername();
        String password = testData.getPassword();

        // Login and get to the Inventory page
        InventoryPage inventoryPage = loginPage.login(username, password);

        // Get items to purchase from test data file
        String[] itemsToPurchase = testData.getItemsToPurchase();

        // Select Inventory Items
        for (String itemToPurchase: itemsToPurchase) {
            inventoryPage.selectItemToPurchase(itemToPurchase);
        }

        ScreenshotUtils.takeScreenshot(driver, "Selected Items");

        // Get selected inventory items
        List<Item> selectedInventoryItems = inventoryPage.getSelectedInventoryItems();

        HeaderSection headerSection = new HeaderSection(driver);
        int numberItemsInCart = Integer.parseInt(headerSection.getNumberOfItemsInCart());

        // Check that the number of selected items to purchase appears in the cart icon
        assertEquals(selectedInventoryItems.size(), numberItemsInCart);

        // Go to the Cart page
        CartPage cartPage = headerSection.clickCartIcon();

        // Get items that appear in the cart page
        List<Item> cartItems = cartPage.getCartItems();

        ScreenshotUtils.takeScreenshot(driver, "Cart Page");

        // Check that the number of items in the cart page is the same as the number
        // of previously selected items
        assertEquals(selectedInventoryItems.size(), cartItems.size());

        // Check that the items in the cart page appear in the selected order defined in the inventory page
        for (int i = 0; i < selectedInventoryItems.size(); i++) {
            Item inventoryItem = selectedInventoryItems.get(i);
            Item cartItem = cartItems.get(i);
            // Check that the items in the cart page, have the same name, description and price than
            // the items selected in the inventory page
            assertAll("Verify items in the cart page",
                    () -> assertEquals(inventoryItem.getName(), cartItem.getName(), "Names should match"),
                    () -> assertEquals(inventoryItem.getDescription(), cartItem.getDescription(), "Descriptions should match"),
                    () -> assertEquals(inventoryItem.getPrice(), cartItem.getPrice(), "Prices should match")
            );
        }

        // Go to checkout
        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        // Getting checkout information from test data file
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String zipcode = testData.getZipcode();

        // Enter information in the Checkout Step One Page
        checkoutStepOnePage.enterInformation(firstName, lastName, zipcode);

        ScreenshotUtils.takeScreenshot(driver, "Checkout Step 1 - Information");

        // Go to the Checkout Step 2 Page
        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.clickContinueButton();

        // Get the items displayed in the overview page
        List<Item> checkoutOverviewItems = checkoutStepTwoPage.getCheckoutOverviewItems();

        ScreenshotUtils.takeScreenshot(driver, "Checkout Step 2 - Overview");

        // Check that the number of items in the overview page is the same as the number
        // of previously selected items
        assertEquals(selectedInventoryItems.size(), checkoutOverviewItems.size());

        // Check that the items in the overview page appear in the same order as items in the cart page
        // While iterating , get the sum of all item prices

        double totalPrice = 0;
        for (int i = 0; i < checkoutOverviewItems.size(); i++) {
            Item cartItem = cartItems.get(i);
            Item overViewItem = checkoutOverviewItems.get(i);
            // Check that the items in the cart page, have the same name, description, price and quantity
            // than the items selected in the overview page
            assertAll("Verify items in the overview page",
                    () -> assertEquals(cartItem.getName(), overViewItem.getName(), "Names should match"),
                    () -> assertEquals(cartItem.getDescription(), overViewItem.getDescription(), "Descriptions should match"),
                    () -> assertEquals(cartItem.getPrice(), overViewItem.getPrice(), "Prices should match"),
                    () -> assertEquals(cartItem.getQuantity(), overViewItem.getQuantity(), "Quantities should match")
            );
            totalPrice += overViewItem.getPriceAsDouble();
        }

        // Check that Item total contains the actual sum of all the prices in the overview page
        assertEquals(totalPrice, checkoutStepTwoPage.getItemTotal());

        // Check that the total is the sum of all prices + tax
        assertEquals(totalPrice + checkoutStepTwoPage.getTax(), checkoutStepTwoPage.getTotal());

        // Click Finish and go to the Checkout Complete Page
        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickFinishButton();

        // Check the Header text for successful order
        assertEquals("Thank you for your order!",checkoutCompletePage.getHeaderText());

        // Check the Title text for successful order
        assertEquals("Checkout: Complete!",checkoutCompletePage.getTitleText());

        // Check the Order Complete text for successful order
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!",
                checkoutCompletePage.getOrderCompleteText());

        ScreenshotUtils.takeScreenshot(driver, "Checkout Complete");
    }

    /**
     * Test checkout process with no items in the cart.
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void checkoutWithNoItems() throws Exception {
        // Go to  login page
        loginPage.visit();

        ScreenshotUtils.takeScreenshot(driver, "Login Page");

        // Get login credentials from test data file
        String username = testData.getUsername();
        String password = testData.getPassword();

        // Login and get to the Inventory page
        InventoryPage inventoryPage = loginPage.login(username, password);

        // Check that the shopping cart doesn't have any number displayed
        HeaderSection headerSection = new HeaderSection(driver);
        String itemsInCart = headerSection.getNumberOfItemsInCart();
        assertEquals("", itemsInCart);

        ScreenshotUtils.takeScreenshot(driver, "Inventory Page");

        // Click the cart icon to go to the cart page
        CartPage cartPage = headerSection.clickCartIcon();

        // Check that there are no items displayed
        assertEquals(0, cartPage.getCartItems().size());

        ScreenshotUtils.takeScreenshot(driver, "Cart Page");

        // Go to checkout step one page
        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        // Getting checkout information from test data file
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String zipcode = testData.getZipcode();

        // Enter information in the Checkout Step One Page
        checkoutStepOnePage.enterInformation(firstName, lastName, zipcode);

        ScreenshotUtils.takeScreenshot(driver, "Checkout Step 1 - Information");

        // Click continue button and go to checkout step 2
        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.clickContinueButton();

        // Verify that there a no items displayed
        assertEquals(0,checkoutStepTwoPage.getCheckoutOverviewItems().size());

        // Verify item total = 0
        assertEquals(0, checkoutStepTwoPage.getItemTotal());

        // Verify tax = 0
        assertEquals(0, checkoutStepTwoPage.getTax());

        // Verify total = 0
        assertEquals(0, checkoutStepTwoPage.getTotal());

        ScreenshotUtils.takeScreenshot(driver, "Checkout Step 2 - Overview");

    }
}