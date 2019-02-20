package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static java.util.Objects.requireNonNull;

public abstract class AnalysisModuleDialog {

    private final AnalysisModuleWaiter waiter;

    public AnalysisModuleDialog(final AnalysisModuleWaiter waiter) {
        this.waiter = requireNonNull(waiter);
    }

    public abstract SelenideElement getTitle();

    public SelenideElement getCloseSelenideElement() {
        return $$x("//mat-toolbar/button/span").last();
    }

    public void shouldBeVisible() {
        waiter.waitForFetch();

        getTitle().shouldBe(visible);

        waiter.waitForFetch();
    }

    public void shouldNotBeVisible() {
        waiter.waitForFetch();

        getTitle().shouldNotBe(visible);

        waiter.waitForFetch();
    }

    public void close() {
        getCloseSelenideElement()
                .shouldBe(visible)
                .click();
    }

    public AnalysisModuleWaiter waiter() {
        return waiter;
    }
}
