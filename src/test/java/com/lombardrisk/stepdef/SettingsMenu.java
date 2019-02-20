package com.lombardrisk.stepdef;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.administration.SecuritySettingsPage;
import com.lombardrisk.page.administration.UserGroupsPage;
import com.lombardrisk.page.administration.UsersPage;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Condition.text;

public class SettingsMenu extends StepDef {

    @Autowired
    private UsersPage usersPage;

    @Autowired
    private SecuritySettingsPage securitySettingsPage;

    @Autowired
    private UserGroupsPage userGroupsPage;


    @When("^I am on the Security Settings page$")
    public void iAmOnTheSecuritySettingsPage() {
        securitySettingsPage.open();
    }

    @Then("^I change the maximum allowed failed login attempts to \"([^\"]*)\"$")
    public void iChangeTheMaximumAllowedFailedLoginAttemptsTo(final String times) {
        securitySettingsPage.setMaxAllowedLoginAttempts(times);
    }


    @Then("^I unlock user \"([^\"]*)\"$")
    public void iUnlockUser(String user)  {
        usersPage.editLockStatus(user);
    }

    @Then("^I active account suspension and set maximum allowed inactivity period to \"([^\"]*)\" days$")
    public void iActiveAccountSuspensionAndSetMaximumAllowedInactivityPeriodToDays(final String days) {
        securitySettingsPage.setMaxAllowedInactivityPeriod(days);

    }

    @And("^I remove suspension status of user \"([^\"]*)\"$")
    public void iRemoveSuspensionStatusOfUser(final String user) {
        usersPage.removeSuspensionStatus(user);

    }

    @Then("^I see the \"([^\"]*)\" status of user \"([^\"]*)\" is \"([^\"]*)\"$")
    public void iSeeTheStatusOfUserIs(final String statusName, final String user, final String status) throws Throwable {
        String statusString = "User ID, Active, Account Expiration Date, Last Login Date, Suspended, Password Reset Date, Locked, Locked Date";
        List<String> userStatus = Arrays.asList(statusString.split(", "));
        if(userStatus.contains(statusName)){
            switch (statusName){
                case "User ID":
                    usersPage.getUser(user).get().find("td",0).shouldBe(text(status));
                    break;
                case "Active":
                    usersPage.getUser(user).get().find("td",1).shouldBe(text(status));
                    break;
                case "Account Expiration Date":
                    usersPage.getUser(user).get().find("td",2).shouldBe(text(status));
                    break;
                case "Last Login Date":
                    usersPage.getUser(user).get().find("td",3).shouldBe(text(status));
                    break;
                case "Suspended":
                    usersPage.getUser(user).get().find("td",4).shouldBe(text(status));
                    break;
                case "Password Reset Date":
                    usersPage.getUser(user).get().find("td",5).shouldBe(text(status));
                    break;
                case "Locked":
                    usersPage.getUser(user).get().find("td",6).shouldBe(text(status));
                    break;
                case "Locked Date":
                    usersPage.getUser(user).get().find("td",7).shouldBe(text(status));
                    break;
            }
        }else {
            throw new PendingException();
        }
    }

    @Then("^I disable account suspension$")
    public void iDisableAccountSuspension()  {
        securitySettingsPage.disableAccountSuspension();
    }

    @Then("^I set the password expiration settings based on (\\d+/\\d+/\\d+)$")
    public void iSetThePasswordExpirationSettingsBasedOn(final @Transform(ToLocalDate.class) LocalDate date) {
        long pwdLife =  LocalDate.now().toEpochDay() - date.toEpochDay();
        long exWarningPeriod = pwdLife - 1;
        securitySettingsPage.setPasswordExpiration(pwdLife,exWarningPeriod);
    }


    @Then("^I reset password for user \"([^\"]*)\"$")
    public void iResetPasswordForUser(final String user)  {

    }
}
