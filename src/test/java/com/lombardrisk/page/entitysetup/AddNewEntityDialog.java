package com.lombardrisk.page.entitysetup;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.util.Objects.requireNonNull;

public class AddNewEntityDialog {

    private final SelenideElement entityNameInput = $(By.id("addEntityDialogForm:EntityName:EntityName"));
    private final SelenideElement entityCodeInput = $(By.id("addEntityDialogForm:EntityCode:EntityCode"));
    private final SelenideElement entityExternalCodeInput = $(By.id("addEntityDialogForm:extCode:extCode"));
    private final SelenideElement entityDescriptionInput = $(By.id("addEntityDialogForm:EntityDescription"));
    private final SelenideElement consolidationBasisDropDown =
            $(By.xpath("//form[@id='addEntityDialogForm']//div[contains(@id,'addEntityDialogForm')]/div/span"));

    private final SelenideElement createNewEntityButton = $(By.id("addEntityDialogForm:confirm"));

    private final Notification notification;

    public AddNewEntityDialog(final Notification notification) {
        this.notification = notification;
    }

    AddNewEntityDialog entityName(final String entityName) {
        entityNameInput.setValue(entityName);
        return this;
    }

    AddNewEntityDialog entityCode(final String entityCode) {
        entityCodeInput.click();
        entityCodeInput
                .setValue(entityCode);
        return this;
    }

    AddNewEntityDialog entityExternalCode(final String entityExternalCode) {
        entityExternalCodeInput.click();
        entityExternalCodeInput.setValue(entityExternalCode);
        return this;
    }

    AddNewEntityDialog consolidationBasis(final String consoleType) {
        consolidationBasisDropDown.click();
        $(By.xpath("//div[contains(@id,'addEntityDialogForm')]/div/ul/li[text()='" + consoleType + "']")).click();
        return this;
    }

    AddNewEntityDialog entityDescription(final String entityDescription) {
        entityDescriptionInput.setValue(entityDescription);
        return this;
    }

    void addEntity() {
        createNewEntityButton.click();

        notification
                .shouldBeNoErrors()
                .shouldCloseGrowlNotification();
    }
}
