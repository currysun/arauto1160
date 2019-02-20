package com.lombardrisk.page.entitysetup;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ImportSettingsDialog {

    private final SelenideElement importDialogTitle = $(By.xpath("//*[@id='listImportFilesDialog']/div[1]"));
    private final SelenideElement uploadButton = $(By.id("importFileForm:importFileUpload_input"));
    private final SelenideElement importButton = $(By.id("importFileForm:listimportBtn"));

    private final Config config;
    private final Notification notification;

    public ImportSettingsDialog(final Config config, final Notification notification) {
        this.config = config;
        this.notification = notification;
    }

    void shouldBeOpen() {
        importButton.shouldBe(visible);

        Selenide.actions()
                .clickAndHold(importDialogTitle)
                .moveByOffset(-200, -200)
                .release()
                .build()
                .perform();
    }

    public ImportSettingsDialog addSettingsFile() {
        uploadButton.sendKeys(config.importSettingsFile().toString());

        notification.loadingProgressShouldNotBeDisplayed();
        firstPageButton().shouldHave(text("1"));

        return this;
    }

    public void importSettingsFile() {
        importButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        notification.shouldBeNoErrors();
        notification.shouldCloseGrowlNotification();

        uploadButton.shouldNotBe(visible);
    }

    private static SelenideElement firstPageButton() {
        return $(By.xpath("//div[@id='importFileForm:ImportInfoTable_paginator_bottom']/span[3]"));
    }
}
