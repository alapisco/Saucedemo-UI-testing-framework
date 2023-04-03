package saucedemo.uitesting.factories;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * A factory class to create browser options instances for different browsers based on the specified headless mode.
 */
public class OptionsFactory {

    /**
     * Creates a browser options instance for the specified browser and headless mode.
     *
     * @param browser     the browser to create options for, e.g., "chrome" or "firefox"
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a browser options instance for the specified browser and headless mode
     * @throws IllegalArgumentException if the browser is unsupported
     */
    public static Object createOptions(String browser, boolean headlessMode) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return createFirefoxOptions(headlessMode);
            case "chrome":
                return createChromeOptions(headlessMode);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Creates a FirefoxOptions instance with the specified headless mode.
     *
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a FirefoxOptions instance with the specified headless mode
     */
    public static FirefoxOptions createFirefoxOptions(boolean headlessMode) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--enable-javascript");
        options.addArguments("--disable-notifications");
        options.addArguments("--width=1366");
        options.addArguments("--height=768");

        if (headlessMode) {
            options.addArguments("-headless");
        }

        return options;
    }

    /**
     * Creates a ChromeOptions instance with the specified headless mode.
     *
     * @param headlessMode true if the browser should be run in headless mode, false otherwise
     * @return a ChromeOptions instance with the specified headless mode
     */
    public static ChromeOptions createChromeOptions(boolean headlessMode) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-javascript");
        options.addArguments("--disable-notifications");
        options.addArguments("--window-size=1280,720");

        if (headlessMode){
            options.addArguments("--headless=new");
        }

        return options;
    }
}
