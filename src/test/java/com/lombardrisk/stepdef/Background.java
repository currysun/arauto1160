package com.lombardrisk.stepdef;

import com.codeborne.selenide.Screenshots;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.config.driver.ChromeHeadlessDownloadEnabler;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.PageUtils;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class Background extends StepDef {

    private static final Logger LOGGER = LoggerFactory.getLogger(Background.class);

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private Notification notification;
    @Autowired
    private ChromeHeadlessDownloadEnabler chromeHeadlessDownloadEnabler;

    @Before
    public void logScenario(final Scenario scenario) {
        LOGGER.info("Run scenario [{}]", scenario.getName());
    }

    @Given("^(?:a|another) user is logged in with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void aUserIsLoggedInWithUsernameAndPassword(final String username, final String password) {
        dashboardPage.open();

        chromeHeadlessDownloadEnabler.enableDownloads();

        Optional<String> currentUsername = mainMenu.getCurrentUsername();

        if (currentUsername.isPresent() && !currentUsername.get().equals(username)) {
            mainMenu.openCurrentUser().logOut();
            notification.loadingProgressShouldNotBeDisplayed();
            loginPage.shouldBeDisplayed();
        }

        if (!mainMenu.isLoggedIn(username)) {
            loginPage
                    .open()
                    .login(username, password);
        }
    }

    @Given("^I am logged out$")
    public void iAmLoggedOut() {
        if (loginPage.isDisplayed()) {
            return;
        }
        mainMenu.openCurrentUser().logOut();
        notification.loadingProgressShouldNotBeDisplayed();
        loginPage.shouldBeDisplayed();
    }

    @And("^the current page is the dashboard$")
    public void theCurrentPageIsTheDashboard() {
        dashboardPage.open();
    }

    @Then("^I should see \"([^\"]*)\"$")
    public void iShouldSee(final String text) {
        PageUtils.shouldBeDisplayedOnThePage(text);
    }


    @After
    public void takeScreenshotOnFailure(final Scenario scenario) {
        String scenarioName = scenario.getName();
        String scenarioStatus = scenario.getStatus().toUpperCase();

        if (!equalsIgnoreCase("passed", scenario.getStatus())) {
            takeScreenshot(scenario);

            LOGGER.warn("Ran scenario [{}]: {}", scenarioName, scenarioStatus);
        } else {
            LOGGER.info("Ran scenario [{}]: {}", scenarioName, scenarioStatus);
        }
    }

    @After("@ScreenshotAfterScenario")
    public void takeScreenshotAfterScenario(final Scenario scenario) {
        takeScreenshot(scenario);
    }

    private static void takeScreenshot(final Scenario scenario) {
        try {
            File screenShot = Screenshots.takeScreenShotAsFile();
            if (screenShot != null) {
                byte[] screenShotBytes = readAllBytes(screenShot.toPath());
                scenario.embed(screenShotBytes, "image/png");
            }
        } catch (IOException e) {
            LOGGER.error("There was an error while trying to take a screenshot", e);
        }
    }
}
