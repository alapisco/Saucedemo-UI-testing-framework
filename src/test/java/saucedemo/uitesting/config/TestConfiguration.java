package saucedemo.uitesting.config;

/**
 * Represents the test configuration that is loaded from system properties.
 * Contains the browser and headlessMode properties.
 */
public class TestConfiguration {
    private String browser;
    private boolean headlessMode;

    // Getters and setters

    /**
     * Returns the browser for the test configuration.
     *
     * @return the browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Sets the browser for the test configuration.
     * The browser should be one of the following: "chrome", "firefox", "edge".
     *
     * @param browser the browser to set
     * @throws IllegalArgumentException if the browser is not supported
     */
    public void setBrowser(String browser) {
        validateBrowser(browser);
        this.browser = browser;
    }

    /**
     * Returns whether the headless mode is enabled or not.
     *
     * @return true if headless mode is enabled, false otherwise
     */
    public boolean isHeadlessMode() {
        return headlessMode;
    }

    /**
     * Sets the headless mode for the test configuration.
     *
     * @param headlessMode true to enable headless mode, false to disable it
     */
    public void setHeadlessMode(boolean headlessMode) {
        this.headlessMode = headlessMode;
    }

    /**
     * Validates the browser to make sure it's one of the supported browsers.
     *
     * @param browser the browser to validate
     * @throws IllegalArgumentException if the browser is not supported
     */
    private void validateBrowser(String browser) {
        if (browser == null || !browser.matches("chrome|firefox|edge")) {
            throw new IllegalArgumentException("Unsupported browser: " + browser +
                    "\n Supported browsers: chrome|firefox|edge " );
        }
    }
}
