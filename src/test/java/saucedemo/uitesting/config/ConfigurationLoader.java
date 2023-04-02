package saucedemo.uitesting.config;

/**
 * A utility class to load the test configuration from system properties.
 */
public class ConfigurationLoader {

    /**
     * Reads the test configuration from system properties and returns a TestConfiguration instance.
     *
     * @return the TestConfiguration instance
     * @throws IllegalStateException if the required properties are not set
     */
    public static TestConfiguration loadConfiguration() {
        TestConfiguration config = new TestConfiguration();

        // Load configuration from system properties
        String browserProperty = System.getProperty("browser");
        if (browserProperty != null) {
            config.setBrowser(browserProperty);
        }

        String headlessModeProperty = System.getProperty("headlessMode");
        if (headlessModeProperty != null) {
            config.setHeadlessMode(Boolean.parseBoolean(headlessModeProperty));
        }

        // Validate the configuration
        if (config.getBrowser() == null) {
            throw new IllegalStateException("Browser property must be set as a system property");
        }

        return config;
    }
}
