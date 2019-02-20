package com.lombardrisk.page.dashboard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Selenide.$;

public class ReturnSourcesDialog {

    @Autowired
    private Notification notification;

    private SelenideElement title = $(By.id("returnSourcesDialog_title"));
    private SelenideElement updateReturnRadioButton = $(By.xpath("//table[@id='returnSourcesForm:radioReturnSource']//label[text()='Update']"));
    private SelenideElement newReturnRadioButton = $(By.xpath("//table[@id='returnSourcesForm:radioReturnSource']//label[text()='Retrieve New']"));
    private SelenideElement preserveAdjustmentsCheckbox = $(By.id("returnSourcesForm:checkPreserveAdjustments"));
    private SelenideElement cancelButton = $(By.id("returnSourcesForm:cancel"));

    public ReturnSourcesDialog setSourceOptions(boolean update, boolean preserve){
        title.shouldBe(Condition.visible);
        if(update == true){
            updateReturnRadioButton.shouldBe(Condition.visible).click();
            $(By.xpath("returnSourcesForm:update")).shouldBe(Condition.visible).click();
        }else{
            newReturnRadioButton.shouldBe(Condition.visible).click();
            if(preserve == true){
                preserveAdjustmentsCheckbox.click();
                $(By.xpath("//button[contains(@id,'preserveAdjustment')]")).shouldBe(Condition.visible);
            }else{
                $(By.xpath("//button[contains(@id,'discardAdjustment')]")).shouldBe();
            }
            $(By.xpath("//button[contains(@id,'Adjustment')]")).shouldBe(Condition.visible).click();
        }

        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

}
