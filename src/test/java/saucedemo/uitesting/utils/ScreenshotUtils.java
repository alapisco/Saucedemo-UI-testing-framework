package saucedemo.uitesting.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class for capturing and attaching screenshots to Allure reports.
 */
public class ScreenshotUtils {

    /**
     * Takes a full-page screenshot of the provided WebDriver instance and attaches it to the Allure report.
     *
     * @param driver         The WebDriver instance to capture the screenshot from.
     * @param attachmentName The name of the screenshot attachment in the Allure report.
     */
    public static void takeScreenshot(WebDriver driver, String attachmentName) {
        // Capture the full-page screenshot using AShot
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(driver);

        // Convert the captured screenshot to a byte array
        byte[] screenshotBytes = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(screenshot.getImage(), "PNG", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            screenshotBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Attach the screenshot to Allure
        if (screenshotBytes != null) {
            Allure.addAttachment(attachmentName, new ByteArrayInputStream(screenshotBytes));
        }
    }
}
