package com.lombardrisk.page.header;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Thread.sleep;

public class JobManagerPage {

    private static int MAX_FAILS = 10;

    private final DashboardPage dashboardPage;
    private final Notification notification;
    private String resultStatus;

    public JobManagerPage(final DashboardPage dashboardPage, final Notification notification) {
        this.dashboardPage = dashboardPage;
        this.notification = notification;
    }

    public JobManagerPage open() {
        dashboardPage.jobManagerButton.shouldBe(Condition.visible).click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public JobManagerPage backToDashboardButton() {

        int retries = 0;
        boolean found = false;
        while (!found && retries < 3) {
            try {
                SelenideElement button = $(By.xpath("//td/input[@value='Back to Dashboard']"));
                if(button.exists())
                    if(button.isDisplayed()) {
                        button.click();
                        found = true;
                    }
            } catch (Exception e) {
                System.out.println("retrying back to dashboard");
                retries++;
            }
        }
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public String retrieveJobName(final String jobName) {
        ElementsCollection taskName =
                $$(By.xpath("//tr/td/a[text()='" + jobName + "']/../../td/a[contains(@id,'jobManagerName')]"));
        String processedJob = taskName.first().getText();
        return processedJob;
    }

    public String retrieveStatus(final String jobName) {
        SelenideElement jobStatus = $(By.xpath("//tr[1]/td/a[text()='"
                + jobName
                + "']/../../td[contains(@class,'jobExecutionProgressColumn')]/span"));

        int failCount = 0;
        boolean ok = true;
        do {
            try {
                while (!jobStatus.isDisplayed()) {
                    System.out.println("waiting for jobStatus of " + jobName);
                    Thread.sleep(2000);
                    Selenide.refresh();
                    while (notification.loadingDialog().isDisplayed()) {
                        System.out.println("waiting for loading dialog to disappear");
                        notification.loadingProgressShouldNotBeDisplayed();
                        Thread.sleep(2000);
                    }
                }

                ok = true;
            } catch (Exception e) {
                System.out.println("failed with exception:" + e.getMessage() + "\nso retrying");
                ok = false;
                failCount++;
            } finally {

            }
        } while (!ok && failCount < MAX_FAILS);

        int delay = 60000;
        while (jobStatus.shouldBe(Condition.visible).is(Condition.text("IN PROGRESS"))) {
            System.out.println("The retrieval job is still running, waiting "
                    + (delay+" milliseconds")
                    + " for "
                    + jobName
                    + " to complete. "
                    + "Presently: "
                    + jobStatus.shouldBe(Condition.visible).getText());
            Selenide.refresh();
            while (notification.loadingDialog().isDisplayed()) {
                notification.loadingProgressShouldNotBeDisplayed();
            }
            Selenide.sleep(delay);
            retrieveStatus(jobName);
        }
        resultStatus = jobStatus.shouldBe(Condition.visible).getText();

        return resultStatus;
    }
}
