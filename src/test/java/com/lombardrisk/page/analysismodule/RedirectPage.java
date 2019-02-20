package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RedirectPage {

    private final SelenideElement redirectMessage = $(By.tagName("app-auth-dialog"));
    private final SelenideElement loginButton = $(By.xpath("//*[@id=\"cdk-overlay-0\"]//button/span"));

    public void shouldShowRedirectMessage() {
        redirectMessage.shouldBe(visible);
        loginButton.click();
    }
}
