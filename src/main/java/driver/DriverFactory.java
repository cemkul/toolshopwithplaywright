package driver;

import com.microsoft.playwright.*;
import config.ConfigReader;

public class DriverFactory {

    private Playwright playwright;
    private Browser browser;

    public Browser initBrowser() {
        playwright = Playwright.create();

        String browserName = ConfigReader.get("browser");
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", String.valueOf(ConfigReader.getBoolean("headless"))));
        int slowMo = ConfigReader.getInt("slow.motion");

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(options);
                break;
        }

        return browser;
    }

    public void closeBrowser() {
        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}