package com.lombardrisk.page.administration;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.PageUtils;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.util.Objects.requireNonNull;

public class SecuritySettingsPage {


    private final SelenideElement accountSuspensionEnabled = $(By.id("securitySettingsForm:suspension"));
    private final SelenideElement maxAllowedInactivityPeriod = $(By.id("securitySettingsForm:inActivetyPeriod_input"));

    private final SelenideElement maxAllowedFailedLoginAttempts = $(By.id("securitySettingsForm:loginAttempts_input"));

    private final SelenideElement passwordExpirationEnabled = $(By.id("securitySettingsForm:passworExpiration"));
    private final SelenideElement expirationWarningPeriod = $(By.id("securitySettingsForm:expirationWarningPeriod_input"));
    private final SelenideElement passwordLife = $(By.id("securitySettingsForm:passwordLife_input"));

    private final SelenideElement strongPasswordEnabled = $(By.id("securitySettingsForm:strongPasswor"));
    private final SelenideElement passwordMinLength = $(By.id("securitySettingsForm:passwordLength_input"));
    private final SelenideElement containLowerCase = $(By.id("securitySettingsForm:containLowerCase"));
    private final SelenideElement containUpperCase = $(By.id("securitySettingsForm:containUpperCase"));
    private final SelenideElement containNumbers = $(By.id("securitySettingsForm:containNumbers"));
    private final SelenideElement containSpecial = $(By.id("securitySettingsForm:containSpecial"));

    private final SelenideElement mustChangePWD = $(By.id("securitySettingsForm:passwordUpdated"));

    private final SelenideElement confirmSettingsChange = $(By.id("securitySettingsForm:confirmSettingsChange"));


    private final MainMenu mainMenu;
    private final Notification notification;

    public SecuritySettingsPage(final MainMenu mainMenu, final Notification notification){
        this.mainMenu = requireNonNull(mainMenu);
        this.notification = requireNonNull(notification);
    }

    public SecuritySettingsPage open(){
        mainMenu.openSettings()
                .administration().securitySettings();
        return this;
    }

    public SecuritySettingsPage setMaxAllowedLoginAttempts(final String times){
        maxAllowedFailedLoginAttempts.setValue(times);
        confirmSettingsChange.click();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public SecuritySettingsPage setMaxAllowedInactivityPeriod(final String days){
        if(!PageUtils.isChecked(accountSuspensionEnabled)){
            accountSuspensionEnabled.click();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        maxAllowedInactivityPeriod.setValue(days);
        confirmSettingsChange.click();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public SecuritySettingsPage disableAccountSuspension(){
        if(PageUtils.isChecked(accountSuspensionEnabled)){
            accountSuspensionEnabled.click();
            notification.loadingProgressShouldNotBeDisplayed();
            confirmSettingsChange.click();
            notification.loadingProgressShouldNotBeDisplayed();
            return this;
        }else {
            return this;
        }
    }

    public SecuritySettingsPage setPasswordExpiration(final long pwdLife, final long exWarningPeriod){
        if(!PageUtils.isChecked(passwordExpirationEnabled)){
            passwordExpirationEnabled.click();
            notification.loadingProgressShouldNotBeDisplayed();
        }
        expirationWarningPeriod.setValue(String.valueOf(exWarningPeriod));
        passwordLife.setValue(String.valueOf(pwdLife));
        confirmSettingsChange.click();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

}
