package reporting;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public final class AllureUtils {

    private AllureUtils() {
    }

    public static void attachScreenshot(Page page, String screenshotName) {
        byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true)
        );

        Allure.addAttachment(
                screenshotName,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }
}