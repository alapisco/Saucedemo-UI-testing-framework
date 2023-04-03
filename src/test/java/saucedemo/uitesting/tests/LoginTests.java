package saucedemo.uitesting.tests;

import org.junit.jupiter.api.Test;
import saucedemo.uitesting.utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains test cases related to login functionality.
 */
public class LoginTests extends BaseTest {

    /**
     * Test for a locked-out user trying to log in.
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void lockedOutUser() throws Exception {

        // Load test data from the JSON file
        loadTestData("src/test/resources/locked_out_user_data.json");

        // Go to  login page
        loginPage.visit();

        ScreenshotUtils.takeScreenshot(driver, "Login Page");

        // Get login credentials from test data file
        String username = testData.getUsername();
        String password = testData.getPassword();

        // Login and get to the Inventory page
        loginPage.login(username, password);

        // Check that the locked out user error message is displayed
        assertEquals("Epic sadface: Sorry, this user has been locked out.",
                loginPage.getErrorMessage());

        ScreenshotUtils.takeScreenshot(driver, "Locked Out User");

    }

    /**
     * Test for invalid user credentials.
     * @throws Exception if there's any issue during the test execution.
     */
    @Test
    public void invalidCredentials() throws Exception {
        // Go to  login page
        loginPage.visit();

        ScreenshotUtils.takeScreenshot(driver, "Login Page");

        // Get login credentials from test data file
        String username = "thisuserdoesntexist";
        String password = "somepassword";

        // Login and get to the Inventory page
        loginPage.login(username, password);

        // Check that the invalid credentials error message is displayed
        assertEquals("Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage());

        ScreenshotUtils.takeScreenshot(driver, "Invalid credentials");

    }

}