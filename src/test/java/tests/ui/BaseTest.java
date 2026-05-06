package tests.ui;

import com.microsoft.playwright.*;
import config.ConfigReader;
import driver.DriverFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import reporting.AllureUtils;

public class BaseTest {

    protected static DriverFactory driverFactory;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    protected String baseUrl;

    @BeforeAll
    static void globalSetup() {
        driverFactory = new DriverFactory();
        browser = driverFactory.initBrowser();
    }

    @BeforeEach
    void setup() {
        baseUrl = ConfigReader.get("base.url");

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
        );

        context.setDefaultTimeout(ConfigReader.getInt("timeout"));

        page = context.newPage();
        page.navigate(baseUrl);
        page.waitForLoadState();
    }

    @RegisterExtension
    AfterTestExecutionCallback allureWatcher = context -> {
        if (page == null) {
            return;
        }

        if (context.getExecutionException().isPresent()) {
            Throwable throwable = context.getExecutionException().get();

            AllureUtils.attachScreenshot(page, "FAILED - Screenshot at failure point");
            AllureUtils.attachText("Error Message", throwable.getMessage());
            AllureUtils.attachText("Stack Trace", getStackTrace(throwable));

        } else {
            AllureUtils.attachScreenshot(page, "PASSED - Final screenshot");
        }
    };

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    static void globalTearDown() {
        if (driverFactory != null) {
            driverFactory.closeBrowser();
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();

        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element).append("\n");
        }

        return sb.toString();
    }
}