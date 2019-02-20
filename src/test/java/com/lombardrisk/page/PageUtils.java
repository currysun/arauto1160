package com.lombardrisk.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class PageUtils {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private PageUtils() {
        //no-op
    }

    public static void shouldBeDisplayedOnThePage(final String expectedText) {
        $(By.tagName("body")).shouldHave(text(expectedText));
    }

    public static void setValue(final SelenideElement inputElement, final String newValue) {
        inputElement.click();
        inputElement.click();
        inputElement.setValue(newValue);
        inputElement.shouldHave(value(newValue));
    }

    public static void setValue(final SelenideElement inputElement, final Integer newValue) {
        inputElement.click();
        inputElement.click();
        inputElement.setValue(newValue.toString());
        inputElement.shouldHave(value(newValue.toString()));
    }

    public static boolean isChecked(final SelenideElement checkboxDiv) {
        return checkboxDiv
                .$("span")
                .has(cssClass("ui-icon-check"));
    }

    public static void clickCheckbox(final SelenideElement checkbox) {
        checkbox.find(By.className("ui-chkbox-box")).click();
        checkbox.find(By.tagName("input")).shouldBe(checked);
    }

    public static void shouldBeDisabled(final SelenideElement checkboxDiv) {
        checkboxDiv.shouldHave(
                cssClass("ui-state-disabled"));
    }

    public static boolean isDisabled(final SelenideElement element){
        return element.has(cssClass("ui-state-disabled"));
    }

    public static boolean isActive(final SelenideElement element){
        return element.has(cssClass("ui-state-active"));
    }

    public static String getCurrentWinHandle(){
        return getWebDriver().getWindowHandle();
    }

    public static WebDriver switchToNewTab(String current){
        WebDriver newWD = null;
        Set<String> winH = getWebDriver().getWindowHandles();
        for (String handle : winH) {
            if (handle != current) {
                newWD = switchTo().window(handle);
            }
        }
        return newWD;
    }

    public static void compareURL(String expectUrl,String homepage){
        WebDriver webDriver = switchToNewTab(homepage);
        webDriver.getCurrentUrl();
        assertThat(webDriver.getCurrentUrl(), is(expectUrl));
        webDriver.close();
        switchTo().window(homepage);
    }

    public static String getCookie(){
        Cookie str = null;
        Set<Cookie> set = getWebDriver().manage().getCookies();
        Iterator iter = set.iterator();
        while (iter.hasNext()){
            str = (Cookie) iter.next();
            if(str.getName().equals("JSESSIONID")){
                break;
            }
        }
        return str.getValue();
    }

}
