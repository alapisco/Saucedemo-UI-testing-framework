package saucedemo.uitesting.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import saucedemo.uitesting.config.ConfigurationLoader;
import saucedemo.uitesting.config.TestConfiguration;
import saucedemo.uitesting.factories.DriverFactory;
import saucedemo.uitesting.models.TestData;
import saucedemo.uitesting.page_objects.pages.LoginPage;

import java.io.File;
import java.io.IOException;

/**
 * BaseTest is an abstract class that serves as a base for all test classes. It
 * provides common setup and teardown methods for managing the WebDriver and
 * loading test data.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected TestConfiguration config;
    protected TestData testData;
    protected LoginPage loginPage;

    /**
     * Set up the test environment before each test, creating a WebDriver instance,
     * loading the test configuration, and initializing the LoginPage.
     *
     * @throws Exception if there's any issue during the test setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // loading browser and headlessMode flag from test configuration
        config = ConfigurationLoader.loadConfiguration();
        String browser = config.getBrowser();
        boolean headlessMode = config.isHeadlessMode();

        // Getting driver from factory
        driver = DriverFactory.createDriver(browser, headlessMode);

        // Initialize login page
        loginPage = new LoginPage(driver);
    }

    /**
     * Load the test data from the specified JSON file.
     *
     * @param testDataPath the path to the JSON file containing the test data.
     * @throws IOException if there's an issue loading the test data from the file.
     */
    public void loadTestData(String testDataPath) throws IOException {
        // Load test data from the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readValue(new File(testDataPath), TestData.class);
    }

    /**
     * Clean up the test environment after each test, quitting the WebDriver.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

