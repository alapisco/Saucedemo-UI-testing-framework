# SauceDemo UI Testing Framework

This is an implementation of the `QA Automation Test part 1` for `Ultra`

This is a Test Automation Framework to develop tests for the `SauceDemo` web application.

It demonstrates the use of Selenium WebDriver with Java for web testing, incorporating design patterns like Page 
Object Model, Page Factory, and Builder Pattern. 

The project structure is organized and easy to understand, with separate packages for configuration, 
factories, models, page  objects, and tests.

Manual test cases are provided in file `Test_Cases_Saucedemo.pdf`

Refer to the [Tests Section](#tests) for full details on how those testcases are implemented

## Table of Contents

1. [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Installing](#installing)
   - [Running the test](#running-the-tests)
   - [Test Results](#tests-results)
2. [Dependencies](#dependencies)
3. [Project structure](#project-structure)
4. [Framework Design](#framework-design)
   - [Factories](#factories)
     - [Driver Factory](#driverfactory)
     - [Options Factory](#optionsfactory)
   - [Page Object Design](#page-object-design)
     - [Base Page](#basepage)
     - [Header Section](#headersection)
     - [Login Page](#loginpage)
     - [Inventory Page](#inventorypage)
     - [Cart Page](#cartpage)
     - [Checkout Step One Page](#checkoutsteponepage)
     - [Checkout Step Two Page](#checkoutsteptwopage)
     - [Checkout Complete Page](#checkoutcompletepage)
   - [Models](#models) 
     - [Item Model](#item-model)
       - [Builder Design Pattern](#builder-design-pattern)
     - [TestData Model](#testdata-model) 
       - [TestData Files](#testdata-files)
   - [Test Configuration](#test-configuration)
     - [Configuration Loader](#configurationloader)
     - [Test Configuration](#test-configuration)
   - [Utilities](#utilities)
     - [ScreenshotUtils](#screenshotutils)
   - [Tests](#tests)
       - [BaseTest](#basetest)
       - [CartTests](#carttests)
       - [InventoryTests](#inventorytests)
       - [LoginTests](#logintests)
       - [PurchaseFlowTests](#purchaseflowtests)
   - [CI/CD Pipeline](#cicd-pipeline)
     - [GitHub Actions Configuration](#github-actions-configuration)
     - [Test Execution Reports](#test-execution-reports)
       - [Junit Reports](#junit-reports)
       - [Allure Reports](#allure-reports)

## Getting Started

These instructions will help you set up the project on your local machine for development and testing purposes.

### Prerequisites

- JDK 11 or higher
- Maven 3.6.0 or higher
- Install Chrome and Firefox

### Installing

1. Clone the repository:
```
git clone https://github.com/your-username/saucedemo-ui-testing.git
```

2. Navigate to the project root directory:
```
cd saucedemo-ui-testing
```
3. Install the required dependencies:
```
mvn clean install
```

### Running the tests

You have to provide the following System Properties:

 - `browser` : the browser where the tests will run. Supported browsers are `chrome` and `firefox`
 - `headlessMode` : a flag that indicates if the tests will run in headless mode.
If set to `false` you will be able to see the execution in browser.

To run the tests execute `mvn clean test` providing the browser and headlessMode parameters.

Example: Running the tests in chrome , headed mode

```
mvn clean test  -Dbrowser=chrome -DheadlessMode=true
```

Example: Running the tests in firefox , headless mode

```
mvn clean test  -Dbrowser=firefox -DheadlessMode=false
```

The framework `automatically` manages setting up and downloading `Selenium Drivers`
for the browser specified. 

There is no need to install drivers manually.

### Tests Results

At the end of the execution you will get in console information about the tests executions.

The framework generates `Junit` and ` Allure html` reports with more details and `screenshots`

#### Junit report

Junit will provide tests results summary in `console` and will also generate `xml files` located
in directory `target/surefire-reports` 

#### Allure report

To see a more detailed `Allure html report`, after execution:

```
mvn allure:serve
```


## Dependencies

This project uses the following dependencies:

- **Selenium WebDriver**: A browser automation framework for web testing
- **JUnit 5**: A testing framework for Java applications
- **Maven**: A build tool and dependency management system for Java projects
- **WebdriverManager** library that carries out the management (i.e., download, setup, and maintenance) of the drivers required by Selenium WebDriver in a fully automated manner
- **Allure** a test report tool for visual reports
- **Jackson Databind** a library that converts JSON to Java Objects

## Project Structure

The project is organized into the following packages:

- `config`: Contains classes for loading and managing test configuration.
- `factories`: Includes factory classes for creating WebDriver instances and browser-specific options.
- `models`: Contains the `Item` class, representing an item in the shop.
- `page_objects`: Organized into two subpackages:
  - `pages`: Contains classes for each web page, extending the `BasePage` class.
  - `sections`: Includes the `HeaderSection` class, representing a common section across multiple pages.
- `tests`: Contains the test classes, including `PurchaseFlowTest` and `TestData`.
- `utils`: Includes utility classes like `ScreenshotUtils` for taking screenshots during test execution.

## Framework Design 

### Factories

#### DriverFactory

The `DriverFactory` class is responsible for creating WebDriver instances for different browsers, such as Chrome and Firefox. It provides a single `createDriver` method that takes the browser name and a boolean flag for headless mode as input parameters. Based on the provided browser name, it creates the appropriate WebDriver instance with the desired options.

The `DriverFactory` class has two private methods, `createFirefoxDriver` and `createChromeDriver`, which are responsible for creating the respective WebDriver instances. These methods use the `OptionsFactory` class to obtain the browser-specific options.

#### OptionsFactory
The `OptionsFactory` class is responsible for creating browser-specific options (e.g., ChromeOptions and FirefoxOptions). It provides a `createOptions` method that takes the browser name and a boolean flag for headless mode as input parameters. Based on the provided browser name, it creates the appropriate options object with the desired settings.

The `OptionsFactory` class has two public methods, `createFirefoxOptions` and `createChromeOptions`, which are responsible for creating the respective browser-specific options. These methods take a boolean flag for headless mode and set the appropriate arguments for enabling JavaScript, disabling notifications, and setting the window size.

### Page Object Design
The Page Object Model (POM) is a design pattern followed in this automation framework, which helps create an object repository for web UI elements. Each web page in the application is represented by a separate class, containing the necessary locators and methods to interact with the page's elements. This design pattern allows for a clean separation between test code and page-specific logic, making the test code more maintainable, reusable, and scalable.

The following classes represent the different pages of the SauceDemo website and their corresponding UI elements and actions:

#### BasePage
The BasePage class is an abstract class that serves as the foundation for all other page object classes. It contains common methods, such as visit, waitForElement, and clickWhenClickable, that can be reused across different pages.

#### HeaderSection
The HeaderSection class represents the header section present on multiple pages, containing methods to click the cart icon and get the number of items in the cart.

#### LoginPage
The LoginPage class represents the login page, providing methods to enter username, password, click the login button, and perform a login action with given credentials.

#### InventoryPage
The InventoryPage class represents the inventory page, containing methods to get inventory items, select or deselect items to purchase, and interact with the items by their name.

#### CartPage
The CartPage class represents the cart page and contains methods to retrieve cart items, click the checkout button, and remove items from the cart.

#### CheckoutStepOnePage
The CheckoutStepOnePage class represents the first step of the checkout process, offering methods to enter user information and click the continue button.

#### CheckoutStepTwoPage
The CheckoutStepTwoPage class represents the second step of the checkout process. It provides methods to click the finish button, retrieve the checkout overview items, item total, tax, and total.

#### CheckoutCompletePage
The CheckoutCompletePage class represents the final checkout complete page, providing methods to retrieve the title, header, and order complete text.

### Models 

#### Item Model
The Item model represents an item in the SauceDemo shop. It appears in the Inventory Page, the Cart Page, and the Checkout Step Two Page. The model has the following attributes:

- `name`: The name of the item.
- `description`: The description of the item.
- `price`: The price of the item as a string, including the currency symbol.
- `buttonText`: The text displayed on the button associated with the item.
- `quantity`: The quantity of the item in the cart (if applicable).

##### Builder Design Pattern
The Item class uses the Builder design pattern to facilitate the creation of Item objects. The Builder pattern is a creational design pattern that helps in constructing complex objects step by step, separating the construction process from the representation of the final object. It is particularly helpful when the object has a large number of attributes or when the object construction process involves multiple steps or optional parameters.

The Builder design pattern for the Item class is implemented using the nested Builder class. The Builder class has the same attributes as the Item class, and it provides a fluent interface to set the attributes by using methods that return the Builder object itself. This approach allows for chaining of method calls, making the object creation process more readable and flexible.

Here's an example of how to use the Builder class to create an Item object:

#### TestData Model
The TestData model class is used to store the test data required for executing test cases. This class encapsulates the necessary information such as login credentials, user information, and items to purchase. The properties of the TestData class include:

- `username`: The username required for logging in to the SauceDemo website.
- `password`: The password required for logging in to the SauceDemo website.
- `firstName`: The first name of the user, used during the checkout process.
- `lastName`: The last name of the user, used during the checkout process.
- `zipcode`: The user's zip code, used during the checkout process.
- `itemsToPurchase`: An array of item names that are intended to be added to the cart and purchased.

- The class provides getter and setter methods for each property to facilitate the manipulation and retrieval of test data. This model allows for a clean separation of test data from the test logic, improving the maintainability and readability of the test suite.

#### TestData Files

The TestData model class is used by BasePage to map the contents of the test data files
to a java object 

The test data files are `JSON` files , located in the `resources` directory under tests
 - purchase_flow_data.json
 - locked_out_user_data.json

Example:

```json
{
  "username": "standard_user",
  "password": "secret_sauce",
  "firstName": "John",
  "lastName": "Doe",
  "zipcode": "12345",
  "itemsToPurchase": [
    "Sauce Labs Backpack",
    "Sauce Labs Bike Light",
    "Sauce Labs Bolt T-Shirt"
  ]
}
```

### Test Configuration
The test framework is designed to handle configuration options `browser` and `headlessMode`. These options can be set as system properties, providing flexibility in executing tests under different configurations.

#### ConfigurationLoader
The ConfigurationLoader class is responsible for loading test configurations from system properties. It has a single static method, loadConfiguration(), which creates an instance of TestConfiguration and sets its properties based on the values of the corresponding system properties.

- `browserProperty`: This system property defines the browser where the test will run. It can be set using the key "browser".
- `headlessModeProperty`: This system property is a flag to define whether the test should run in headless mode. It can be set using the key "headlessMode".
The loadConfiguration() method first checks if the system properties are set and, if so, updates the TestConfiguration object with the provided values. It then validates the configuration by ensuring that the browser property is set. If the browser property is not set, it throws an IllegalStateException.

#### TestConfiguration
The TestConfiguration class is a simple data class that holds the configuration options for the test framework. It has two attributes:

- browser: A string representing the browser where the test will run (e.g., "chrome" or "firefox").
- headlessMode: A boolean flag indicating whether the test should run in headless mode.
The class provides getters and setters for each attribute. The setBrowser() method includes a call to the validateBrowser() method, which checks if the provided browser is supported. The supported browsers are "chrome" and "firefox". If an unsupported browser is provided, it throws an IllegalArgumentException.



### Utilities

#### ScreenshotUtils
The ScreenshotUtils class is a utility class designed to capture screenshots during test execution and attach them to Allure reports. This can be helpful in analyzing test failures and understanding the state of the application at various stages during the test.

### Tests

The file `Test_Cases_Saucedemo.pdf` includes 7 testcases that were implemented in the
Test Classes. The file describes in detail all the 7 scenarios and which suite they belong to. In this file
test cases are assigned a unique ID.

In the following table you can see where the testcase is implemented


| Testcase ID | Test Class        | Test Method  | 
|-------------|-------------------|--------------|
| TC001       | PurchaseFlowTests | purchaseItems()          |
| TC002       | PurchaseFlowTests | checkoutWithNoItems()          |
| TC003       | LoginTests        | lockedOutUser()          |
| TC004       | LoginTests        | invalidCredentials()         |
| TC005       | InventoryTests    | AddRemoveItems()          |
| TC006       | Cart Tests        |itemsPreserveSelectionOrder()        |
| TC007       | Cart Tests | removeItemsFromCart()          |

Check `Test_Cases_Saucedemo.pdf` for full details on each test case

#### BaseTest
An abstract class that serves as a base for all test classes. It provides common setup and teardown methods for managing the WebDriver and loading test data. It initializes the LoginPage and handles the configuration and WebDriver instantiation.

#### CartTests
This class extends BaseTest and contains test cases related to the cart functionality of the SauceDemo website. It verifies that items are added to the cart in the correct order and that items can be removed from the cart. The CartTests class utilizes the InventoryPage, HeaderSection, and CartPage page objects to perform actions and validate the state of the cart.

#### InventoryTests
This class extends BaseTest and focuses on test cases related to the inventory page functionality of the SauceDemo website. The InventoryTests class tests the functionality of adding and removing items from the cart using the InventoryPage and HeaderSection page objects.

#### LoginTests
This class extends BaseTest and contains test cases related to the login functionality of the SauceDemo website. It verifies successful login, failed login due to invalid credentials, and error messages displayed for incorrect inputs. The LoginTests class uses the LoginPage page object to perform actions and validate the login process.

#### PurchaseFlowTests
This class extends BaseTest and contains test cases related to the end-to-end purchase flow of the SauceDemo website. It tests the entire purchase process from adding items to the cart, providing user information, and verifying the final order summary. The PurchaseFlowTests class utilizes the InventoryPage, HeaderSection, CartPage, CheckoutPage, and CheckoutCompletePage page objects to perform actions and validate the purchase flow.

### CI/CD pipeline

This framework is setup as part of step in a CI/CD pipeline.

It is configured and deployed in `Github Actions`. Using Github hosted runners.

Every time there is an update to the project or if requested manually. The framework will execute the test cases.

After the execution, a brief `junit report` will be shown in the Summary section, and also a more detailed html `Allure report`
will be deployed to `Github Pages`. This report contains screenshots of all the test cases executed, as well as other
test execution data.

To see the test results from the latest run go to:
[https://alapisco.github.io/sauce-test](https://alapisco.github.io/sauce-test)


#### GitHub Actions Configuration

The project includes file `.github/workflow/ui-tests.yml` which, calls workflow file
`.github/workflow/execute-tests.yml` for chrome and firefox.

The `execute-tests.yml` contains the steps to execute the framework and generate test reports.

In summary , the tests are run in `Ubuntu`, using `JDK11` and `maven`.

Both `firefox` and `chrome` are already installed in the `Ubuntu Github hosted runner`

The `Selenium driver setup `is managed automatically by the framework

After the execution you can see` Junit summary results` and a visual `html Allure report`

Every time there is a change in the repository, the `UI tests` for `chrome` and `firefox` will run
in `parallel` 

```
Allure reports are deployed to the Github Pages of the reporitory
```

#### Test Execution Reports

To see all the executions, go to the `Actions` section of the repository:

[https://github.com/alapisco/sauce-test/actions](https://github.com/alapisco/sauce-test/actions)

In the `Workflows` section, on the left, select `UI Tests`. This is the flow that runs the workflows
that run the chrome and firefox tests.

On the left menu, at the `Jobs` section you will see `run-ui-tests-chrome` and  `run-ui-tests-firefox` 

Click on each of them, and click the `build` section that will be expanded 

On the right it will show the output of all the steps. 
 - Expand  `Run chrome tests` or `Run firefox tests` to see the output of the `mvn` command that runs the tests
 - Expand `Report URL` to see where the `Allure report` is available

On the left you will see the `Summary` section that will show junit test results in the `Build Summary`
section

#### Junit reports

- Click the `Summary` section

- On the right, there will be a `Build Summary` section with the information of the tests passes/failed  

#### Allure reports

To see the `Allure report` url:

- Click `build` inside the `Jobs` name section on the left menu
- On the right, in the `Build section`, expand the `Report URL` section. The url for the
report of that execution will be shown there

```
The Allure reports take a minute or two to be deployed to Github Pages.

The URL might not be immediately available while the report is being deployed.
```
