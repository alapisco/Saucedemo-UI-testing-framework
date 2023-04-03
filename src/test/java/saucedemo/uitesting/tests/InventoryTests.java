package saucedemo.uitesting.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saucedemo.uitesting.page_objects.pages.InventoryPage;
import saucedemo.uitesting.page_objects.sections.HeaderSection;
import saucedemo.uitesting.utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains test cases related to the inventory page functionality.
 */
public class InventoryTests extends BaseTest{

    private InventoryPage inventoryPage;
    private HeaderSection headerSection;

    /**
     * Set up the test environment and initialize the InventoryPage and HeaderSection instances.
     *
     * @throws Exception if there's any issue during the test setup.
     */
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        loadTestData("src/test/resources/purchase_flow_data.json");

        // Go to  login page
        loginPage.visit();

        ScreenshotUtils.takeScreenshot(driver, "Login Page");

        // Get login credentials from test data file
        String username = testData.getUsername();
        String password = testData.getPassword();

        // Login and get to the Inventory page
        inventoryPage = loginPage.login(username, password);

        // get header section
        headerSection = new HeaderSection(driver);
    }

    /**
     * Test to add and remove items from the inventory page and verify the cart count.
     *
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void AddRemoveItems() throws Exception {

        // Add 4 items
        inventoryPage.selectItemToPurchase("Sauce Labs Bike Light");
        inventoryPage.selectItemToPurchase("Sauce Labs Bolt T-Shirt");
        inventoryPage.selectItemToPurchase("Sauce Labs Onesie");
        inventoryPage.selectItemToPurchase("Test.allTheThings() T-Shirt (Red)");

        // Check that 4 items are displayed in cart icon
        assertEquals("4", headerSection.getNumberOfItemsInCart());

        ScreenshotUtils.takeScreenshot(driver, "Added Items");

        // Remove 2 items
        inventoryPage.deselectItemToPurchase("Sauce Labs Onesie");
        inventoryPage.deselectItemToPurchase("Sauce Labs Bolt T-Shirt");

        // Check that 4 items are displayed in cart icon
        assertEquals("2", headerSection.getNumberOfItemsInCart());

        ScreenshotUtils.takeScreenshot(driver, "Removed Items");
    }

}
