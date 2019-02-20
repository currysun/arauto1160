package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class DeleteReturnDialog {
    private SelenideElement deleteConfirmTitle = $(By.xpath("//span[@id='deleteReturnMessageDlg_title']"));
    private SelenideElement okButton = $(By.xpath("//button[@id='deleteReturnMessageForm:deleteReturnConfirm']/span[text()='OK']"));
    private SelenideElement cancelButton = $(By.xpath("//button[@id='deleteReturnMessageForm:deleteReturnCancel']/span[text()='Cancel']"));

    private SelenideElement confirmDeletionCommentTitle = $(By.xpath("//span[@id='deleteReturnCommentDialog_title']"));
    private SelenideElement confirmDeleteCommentArea = $(By.xpath("//div/textarea[@id='deleteReturnCommentForm:deleteReturnCommentTxt']"));
    private SelenideElement confirmDeleteOKButton = $(By.xpath("//button[@id='deleteReturnCommentForm:deleteReturn']/span[text()='OK']"));

    private Notification notification;

    public DeleteReturnDialog confirmDeletion(){
        deleteConfirmTitle.shouldBe(Condition.visible);
        okButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
        confirmDeletionCommentTitle.shouldBe(Condition.visible);
        confirmDeleteCommentArea.setValue("Test"+RandomStringUtils.randomAlphanumeric(4));
        confirmDeleteOKButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.growlNotificationShouldBeDisplayed();
        notification.closeGrowlNotificationIfPresent();
        return this;
    }
}
