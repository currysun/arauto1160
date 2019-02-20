package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.analysismodule.RedirectPage;
import com.lombardrisk.page.event.Notification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class Login extends StepDef {

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private RedirectPage redirect;

    @Autowired
    private Notification notification;

    @Then("^I am redirected to login page with a timeout message$")
    public void iShouldSeeASelectorPanelWithNothingSelected() {
        redirect.shouldShowRedirectMessage();

        loginPage.shouldBeDisplayed();
        loginPage.shouldHaveTimeoutMessage();
    }

    @Given("^(?:a|another) user with username \"([^\"]*)\" and password \"([^\"]*)\" failed login$")
    public void aUserFailedLoggedIn(final String username, final String password) {

        //loginPage.shouldBeDisplayed();
        loginPage.open();
        loginPage.failLoginWithErrorInfo(username, password);
    }

    @Then("^I should see login error message \"([^\"]*)\"$")
    public void iShouldSeeLoginError(final String text) {
        notification.shouldDisplayErrorMessage(text);
    }

    @Then("^I see login warning message \"([^\"]*)\"$")
    public void iSeeLoginWarningMessage(final String message) {
        notification.shouldShowWarning(message);
        notification.closeWarning();
    }


}

