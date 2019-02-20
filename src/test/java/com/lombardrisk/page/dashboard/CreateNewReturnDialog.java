package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Objects.requireNonNull;

public class CreateNewReturnDialog {

    private final SelenideElement datePicker = $(By.id("createForm:processDate_input"));
    private final SelenideElement confirmButton = $(By.id("createconfirm"));
    private final SelenideElement createConfirmDialog = $(By.id("createConfirmDialog"));
    private final SelenideElement createButton = $(By.id("createForm:createformInstanceBtn"));
    private final SelenideElement entitySelectDropDown = $(By.id("createForm:selectGroup"));

    private final Notification notification;

    public CreateNewReturnDialog(final Notification notification) {
        this.notification = requireNonNull(notification);
    }

    public void createAnyReturn(final String entity) {
        entitySelectDropDown.selectOption(entity);
        selectFirstDayMonth();
        createButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        if (createConfirmDialog.isDisplayed()) {
            confirmButton.click();
        }
    }

    private void selectFirstDayMonth() {
        datePicker.click();
        findFirstDay().click();
        datePicker.shouldNotBe(empty);
    }

    private static SelenideElement findFirstDay() {
        return $$(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[1]/td/a")).get(0);
    }
}
