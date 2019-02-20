package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.administration.UserGroupsPage;
import com.lombardrisk.page.administration.UsersPage;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.refresh;
import static java.util.stream.Collectors.toList;

public class EntitySetup extends StepDef {

    @Autowired
    private UserGroupsPage userGroupsPage;
    @Autowired
    private UsersPage usersPage;
    @Autowired
    private EntitySetupPage entitySetupPage;

    private List<String> userGroups;
    private String entityName;

    @And("^I am on the Entity setup page$")
    public void iAmOnTheEntitySetupPage() {
        entitySetupPage.open();
    }

    @Given("^I have created an entity of name \"([^\"]*)\", description \"([^\"]*)\", codes of \"([^\"]*)\"$")
    public void iHaveCreatedAnEntityOfNameDescriptionCodesOf(
            final String entityName, final String entityDescription, final String entityCode) {
        this.entityName = entityName;

         entitySetupPage.openOrCreateEntity(entityName, entityDescription, entityCode, entityCode);
    }

    @And("^I have assigned the following \"(.*)\" returns to my entity (.*)$")
    public void iHaveAssignedProductPackageReturnsToMyEntity(
            final ProductPackage productPackage, final @Delimiter(", ") List<String> returns) {

        entitySetupPage
                .addPrivilegesToReturn(productPackage, returns, userGroups)
                .assignConfigForEntities();
    }

    @Given("^I select my created entity$")
    public void iSelectMyCreatedEntity() {
        refresh();

        entitySetupPage.selectEntity(entityName);
    }

    @And("^I navigate to the Assign Returns tab$")
    public void navigateToTheAssignReturnsTab() {
        entitySetupPage.openAssignReturnsTab();
    }

    @And("^I am on the UserGroups page$")
    public void iAmOnTheUserGroupsPage() {
        userGroupsPage.open();
    }

    @Given("^I have created the necessary UserGroups (.*)$")
    public void iHaveCreatedTheNecessaryUserGroups(final @Delimiter(", ") List<String> userGroups) {
        this.userGroups = userGroups;

        userGroupsPage.addNewUserGroups(userGroups);
    }

    @And("^I am on the Users page$")
    public void iAmOnTheUsersPage() {
        usersPage.open();
    }

    @Given("^I create the necessary Users(.*)$")
    public void iCreateTheNecessaryUsers(final @Delimiter(", ") List<String> users) {
        usersPage.addUsers(users.stream()
                .map(String::trim)
                .collect(toList())
        );
    }

    @And("^I have selected the entity \"([^\"]*)\"$")
    public void iHaveSelectedTheFollowingEntityOf(final String entityName) {
        entitySetupPage.selectEntity(entityName);
    }

    @And("^I have selected the \"(.*)\" return \"([^\"]*)\"$")
    public void iHaveSelectedTheFollowingReturnToConfigureOf(
            final ProductPackage productPackage, final String selectedReturn) {
        entitySetupPage.configureReturn(productPackage, selectedReturn);
    }

    @And("^I add the \"([^\"]*)\" workflow to my return with the user groups:$")
    public void iAddTheWorkflowToMyReturn(final String workflow, final DataTable dataTable) {
        entitySetupPage.assignWorkflow(workflow);

        Map<String, String> stepToUserGroupMap = dataTable.asMap(String.class, String.class);
        stepToUserGroupMap.forEach((step, userGroup) -> entitySetupPage.assignGroupToStep(userGroup, step));

        entitySetupPage.saveApprovalWorkflow();
    }

    @And("^I import the settings file$")
    public void importAccessSettingsFile() {
        entitySetupPage
                .openImportDialog()
                .addSettingsFile()
                .importSettingsFile();
    }
}
