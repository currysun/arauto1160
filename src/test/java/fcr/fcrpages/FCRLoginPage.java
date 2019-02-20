package fcr.fcrpages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static java.util.Objects.requireNonNull;

public class FCRLoginPage {

    private SelenideElement title = $(By.xpath("//mat-card-title[text()='FCR Engine']"));
    private SelenideElement usernameField = $(By.xpath("//input[@formcontrolname='username']"));
    private SelenideElement passwordField = $(By.xpath("//input[@formcontrolname='password']"));
    private SelenideElement loginButton = $(By.xpath("//span[text()='Login']"));

    private final Config config;

    public FCRLoginPage(final Config config) {
        this.config = requireNonNull(config);
    }

    public FCRLoginPage open() {
        Selenide.open(config.fcrFullUrl());
        return this;
    }

    public FCRLoginPage loginWithAdmin() {
        open();
        title.shouldBe(Condition.visible);
        usernameField.shouldBe(Condition.visible).sendKeys("admin");
        passwordField.shouldBe(Condition.visible).sendKeys("password");
        loginButton.shouldBe(Condition.visible).click();
        return this;
    }
}
