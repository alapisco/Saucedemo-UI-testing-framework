package saucedemo.uitesting.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * A factory class to create WebDriver instances based on the specified browser and headless mode.
 */
public class DriverFactory {

    /**
     * Creates a WebDriver instance for the specified browser and headless mode.
     *
     * @param browser     the browser to create a WebDriver for, e.g., "chrome" or "firefox"
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a WebDriver instance for the specified browser and headless mode
     * @throws IllegalArgumentException if the browser is unsupported
     */
    public static WebDriver createDriver(String browser, boolean headlessMode) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return createFirefoxDriver(headlessMode);
            case "chrome":
                return createChromeDriver(headlessMode);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Creates a FirefoxDriver instance with the specified headless mode.
     *
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a FirefoxDriver instance with the specified headless mode
     */
    private static WebDriver createFirefoxDriver(boolean headlessMode) {
        // Set up the WebDriverManager for Firefox
        WebDriverManager.firefoxdriver().setup();

        // Get FirefoxOptions from OptionsFactory
        FirefoxOptions firefoxOptions = OptionsFactory.createFirefoxOptions(headlessMode);

        // Create and return the FirefoxDriver instance with the appropriate options
        return new FirefoxDriver(firefoxOptions);
    }

    /**
     * Creates a ChromeDriver instance with the specified headless mode.
     *
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a ChromeDriver instance with the specified headless mode
     */
    private static WebDriver createChromeDriver(boolean headlessMode) {
        // Set up the WebDriverManager for Chrome
        WebDriverManager.chromedriver().setup();

        // Get ChromeOptions from OptionsFactory
        ChromeOptions chromeOptions = OptionsFactory.createChromeOptions(headlessMode);

        // Create and return the ChromeDriver instance with the appropriate options
        return new ChromeDriver(chromeOptions);
    }
}