package com.lombardrisk.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.header.RegionalLanguage.EN_GB;
import static java.util.Objects.requireNonNull;

public class LoginPage {

    private final SelenideElement userNameInput = $(By.id("loginForm:inputUsername"));
    private final SelenideElement passwordInput = $(By.id("loginForm:inputPassword"));
    private final SelenideElement signInButton = $(By.id("loginForm:btnSignIn"));
    private final SelenideElement timeoutWarningMessage = $(By.id("timeoutBar"));

    private final MainMenu mainMenu;
    private final Config config;
    private final Notification notification;

    public LoginPage(final Config config, final MainMenu mainMenu, final Notification notification) {
        this.config = requireNonNull(config);
        this.mainMenu = requireNonNull(mainMenu);
        this.notification = requireNonNull(notification);
    }

    public LoginPage open() {
        Selenide.open(config.arFullUrl() + "/core/page/login.xhtml");
        return this;
    }

    public void login(final String username, final String password) {
        userNameInput.setValue(username);
        passwordInput.setValue(password);

        signInButton.click();

        mainMenu.openCurrentUser()
                .openUserPreferences()
                .regionalLanguage(EN_GB)
                .save();
    }

    public void failLoginWithErrorInfo(final String username, final String password) {
        userNameInput.setValue(username);
        passwordInput.setValue(password);
        signInButton.click();

        notification.growlNotificationShouldBeDisplayed();
    }

    public void shouldBeDisplayed() {
        userNameInput.waitUntil(visible, config.maxTimeout());
    }

    public boolean isDisplayed() {
        return userNameInput.isDisplayed();
    }

    public void shouldHaveTimeoutMessage() {
        timeoutWarningMessage.shouldBe(visible);
    }
}
