package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.Waiter;
import com.google.common.base.Predicate;
import com.lombardrisk.config.Config;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;

public class AnalysisModuleWaiter {

    private final SelenideElement spinner = $(By.xpath("//mat-spinner"));

    private final Config config;
    private final Waiter waiter;

    public AnalysisModuleWaiter(final Config config, final Waiter waiter) {
        this.config = config;
        this.waiter = waiter;
    }

    public void waitForFetch() {
        spinner.waitUntil(
                not(visible)
                        .because("fetching OFSAA did not finish"),
                config.maxTimeout());
    }

    public void waitForQuickAnimation() {
        sleep(config.minTimeout());
    }

    public void waitForTransitionAnimation() {
        sleep(config.minTimeout() * 2);
    }

    public void waitForVisibleAndDo(
            final SelenideElement selenideElement,
            final Consumer<SelenideElement> makeVisibleAction) {

        long maxTimeout = config.maxTimeout();

        waiter.wait(
                selenideElement,
                makeVisibleAndCheck(makeVisibleAction),
                maxTimeout, config.minTimeout() / 2);

        selenideElement.waitUntil(visible, maxTimeout, config.minTimeout());
    }

    private static Predicate<SelenideElement> makeVisibleAndCheck(final Consumer<SelenideElement> makeVisibleAction) {
        return element -> {
            if (makeVisibleAction != null
                    && element != null && !element.isDisplayed())
                makeVisibleAction.accept(element);
            return requireNonNull(element).isDisplayed();
        };
    }
}
