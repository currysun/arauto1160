package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.lombardrisk.page.event.Notification;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class RetrieveReturnDialog {

    private int MAX_RETRIES = 10;

    private SelenideElement entityDropDown = $(By.id("retrieveForm:selectGroup"));
    private SelenideElement referenceDateDropdown = $(By.id("retrieveForm:referenceDate_input"));
    private SelenideElement formDropdown = $(By.id("retrieveForm:selectForm"));
    public SelenideElement okButton = $(By.xpath(
            "//button[@id='retrieveForm:doRetrieve']/span[text()='OK'] | //button[@id='retrieveForm:toReturnSource']/span[text()='OK']"));
    private SelenideElement jobResultsOKButton = $(By.xpath("//button[@id='jobResultForm:ok']/span[text()='OK']"));
    private SelenideElement cancelButton = $(By.xpath("//button[@id='retrieveForm:cancel']/span[text()='Cancel']"));

    private final Notification notification;
    private final DashboardPage dashboardPage;
    private final DeleteReturnDialog deleteReturnDialog;

    public RetrieveReturnDialog(
            final Notification notification,
            final DashboardPage dashboardPage,
            final DeleteReturnDialog deleteReturnDialog) {
        this.notification = notification;
        this.dashboardPage = dashboardPage;
        this.deleteReturnDialog = deleteReturnDialog;
    }

    public RetrieveReturnDialog selectEntity(final String entity) {
        entityDropDown
                .shouldBe(Condition.visible)
                .selectOption(entity);
        notification.loadingProgressShouldNotBeDisplayed();

        return this;
    }

    public RetrieveReturnDialog selectReferenceDate(final LocalDate refDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        referenceDateDropdown.setValue(refDate.format(formatter));
        sleep(500);
        SelenideElement activeDate =
                $(By.xpath("//table[@class='ui-datepicker-calendar']//td/a[contains(@class,'ui-state-active')]"));
        activeDate.click();
        int addedDate = 0;
        while (notification.loadingDialog().isDisplayed()) {
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    private static int MAX_FORM_INSTANCE_RETRIES = 20;

    public RetrieveReturnDialog selectReturn(final String form) {
        int retries = 0;
        while(!formDropdown.isEnabled() && retries < MAX_FORM_INSTANCE_RETRIES) {
            System.out.println("Waiting for formInstance dropdown");
            Selenide.sleep(10000);
        }

        if(retries == MAX_FORM_INSTANCE_RETRIES) {
            System.out.println("Timed out waiting for Loading dialog to disappear");
        }

        formDropdown.shouldBe(Condition.enabled).selectOption(form);
        notification.loadingProgressShouldNotBeDisplayed();

        return this;
    }

    public SelenideElement existingReturnMessage() {
        return $(By.xpath("//span[text()='This return already exists.']"));
    }

    public SelenideElement retrieveReturnErrorMessage() {
        return $(By.xpath(
                "//div[@id='retrieveForm:retrievemessages']//li/span[not(contains(text(),'This return already exists.'))]"));
    }

    public RetrieveReturnDialog checkAndRemoveExistingReturn(
            final String returnName,
            final int returnVersion,
            final LocalDate returnDate) {
        if (existingReturnMessage().isDisplayed()) {
            cancelButton.shouldBe(Condition.enabled).click();
            while (notification.loadingDialog().isDisplayed()) {
                notification.loadingProgressShouldNotBeDisplayed();
            }
            dashboardPage.deleteReturn(returnName, returnVersion, returnDate);
            notification.loadingProgressShouldNotBeDisplayed();
        }
        return this;
    }

    public RetrieveReturnDialog startRetrieveReturn() {

        int retries = 0;
        while (!okButton.exists() && retries < MAX_RETRIES) {
            System.out.println("waiting for OK button");
            Selenide.sleep(1000);
            retries++;
        }

        okButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        if (notification.loadingDialog().isDisplayed()) {
            Selenide.refresh();
        }

        jobResultsOKButton.shouldBe(Condition.visible).click();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }
}
