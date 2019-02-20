package com.lombardrisk.page.event;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;

public class Notification {

    private static final Logger LOGGER = LoggerFactory.getLogger(Notification.class);

    private final Config config;

    public Notification(final Config config) {
        this.config = requireNonNull(config);
    }

    public void closeGrowlNotificationIfPresent() {
        if (growlNotificationContainer().isDisplayed()) {
            executeJavaScript("$('.ui-growl-item-container').remove()");
        }
    }

    public void shouldCloseGrowlNotification() {
        growlNotificationContainer().shouldBe(visible);
        executeJavaScript("$('.ui-growl-item-container').remove()");
    }

    public Notification shouldBeNoErrors() {
        loadingProgressShouldNotBeDisplayed();

        growlErrorNotifications()
                .waitUntil(not(visible), config.minTimeout());
        return this;
    }

    public void loadingProgressShouldNotBeDisplayed() {
        long timeout = config.maxTimeout();
        int attempts = waitForLoadingBarToDisappear(timeout, 1);
        logNumberOfAttemptsWaitingForLoadingBarToDisappear(attempts, timeout);
    }

    public void loadingProgressShouldNotBeDisplayed(final long timeout) {
        int attempts = waitForLoadingBarToDisappear(timeout, 1);
        logNumberOfAttemptsWaitingForLoadingBarToDisappear(attempts, timeout);
    }

    public void growlNotificationShouldBeDisplayed() {
        growlNotificationContainer().shouldBe(visible);
    }

    private static SelenideElement growlErrorNotifications() {
        return $(".ui-growl-image-error").parent();
    }

    private static SelenideElement growlNotificationContainer() {
        return $(".ui-growl-item-container");
    }

    public void shouldDisplayErrorMessage(final String errorMessage) {
        growlNotificationContainer().shouldHave(
                text(errorMessage));
    }

    private static SelenideElement passwordExpirationDialog(){
        return $(By.id("passwordExpirationDialog"));
    }

    public void shouldShowWarning(final String message){
        passwordExpirationDialog().shouldHave(text(message));
    }

    public void closeWarning(){
        passwordExpirationDialog().shouldBe(visible).find(".ui-dialog-titlebar-icon").click();
    }

    public static SelenideElement loadingDialog() {
        return $("#ajaxstatusDlg");
    }

    private static int waitForLoadingBarToDisappear(final long timeout, final int numberOfAttempts) {
        loadingDialog()
                .waitUntil(not(visible), timeout, 500);

        sleep(1000);

        if (loadingDialog().is(not(visible))) {
            return numberOfAttempts;
        }

        return waitForLoadingBarToDisappear(timeout, numberOfAttempts + 1);
    }

    private static void logNumberOfAttemptsWaitingForLoadingBarToDisappear(final int count, final long timeout) {
        if (count > 1) {
            LOGGER.warn("Took {} attempts with timeout of {}ms for loading bar to disappear", count, timeout);
        }
    }
}

