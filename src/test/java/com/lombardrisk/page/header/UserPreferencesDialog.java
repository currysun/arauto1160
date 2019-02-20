package com.lombardrisk.page.header;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.PageUtils.isChecked;
import static java.util.Objects.requireNonNull;

public class UserPreferencesDialog {

    private final SelenideElement regionalLanguageDropdown = $(By.id("preferencesForm:selectLanguage"));
    private final SelenideElement deriveLanguageFromBrowserCheckbox =
            $(By.xpath("//div[@id='preferencesForm:languageCheck']/div[2]"));
    private final SelenideElement currentRegionalLanguageLabel =
            $(By.xpath("//div[@id='preferencesForm:preferencesDlg']/div[2]/div/div[2]/div[1]/label[2]"));
    private final SelenideElement saveButton = $(By.id("preferencesForm:confirm"));

    private final Notification notification;

    public UserPreferencesDialog(final Notification notification) {
        this.notification = requireNonNull(notification);
    }

    void shouldBeOpen() {
        saveButton.shouldBe(visible);
    }

    public UserPreferencesDialog regionalLanguage(final RegionalLanguage targetRegionalLanguage) {
        String currentRegionalLanguage = currentRegionalLanguageLabel.getText();

        if (targetRegionalLanguage.isDifferentFrom(currentRegionalLanguage)) {
            selectRegionalLanguage(targetRegionalLanguage);
        }
        return this;
    }

    public void save() {
        saveButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        saveButton.shouldNotBe(visible);
    }

    private void selectRegionalLanguage(final RegionalLanguage targetRegionalLanguage) {
        if (isChecked(deriveLanguageFromBrowserCheckbox)) {
            deriveLanguageFromBrowserCheckbox.click();
        }
        regionalLanguageDropdown.selectOption(targetRegionalLanguage.fullName());
    }
}
