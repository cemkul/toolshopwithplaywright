package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void click(Locator locator) {
        locator.waitFor();
        locator.click();
    }

    protected void clickWhenEnabled(Locator locator) {
        locator.waitFor();
        page.waitForCondition(locator::isEnabled);
        locator.click();
    }

    protected void type(Locator locator, String text) {
        locator.waitFor();
        locator.click();
        locator.clear();
        page.keyboard().type(text);
        locator.blur();
    }

    protected void selectByLabel(Locator locator, String label) {
        locator.waitFor();
        locator.selectOption(new SelectOption().setLabel(label));
        locator.blur();
    }

    protected void selectByValue(Locator locator, String value) {
        locator.waitFor();
        locator.selectOption(value);
        locator.blur();
    }

    protected String getText(Locator locator) {
        locator.waitFor();
        return locator.textContent().trim();
    }

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    protected void waitForLoad() {
        page.waitForLoadState();
    }
}