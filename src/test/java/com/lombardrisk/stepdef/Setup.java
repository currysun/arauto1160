package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.administration.UserGroupsPage;
import com.lombardrisk.page.administration.UsersPage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.entitysetup.EntitySetupDialog;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import com.lombardrisk.page.header.MainMenu;
import cucumber.api.Delimiter;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class Setup extends StepDef {

    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private LoginPage loginPage;
    @Autowired
    private UsersPage usersPage;
    @Autowired
    private UserGroupsPage userGroupsPage;
    @Autowired
    private EntitySetupPage entitySetupPage;
    @Autowired
    private EntitySetupDialog entitySetupDialog;

    @Given("^I have a user of \"([^\"]*)\" and password of \"([^\"]*)\" in group \"([^\"]*)\" and assigned to entity \"([^\"]*)\" with the following \"([^\"]*)\" product and return \"([^\"]*)\" v(\\d+) assigned with privileges of (.*)$")
    public void iHaveAUserOfAndPasswordOfInGroupAndAssignedToEntityWithTheFollowingProductAndReturnVAssignedWithPrivilegesOf(
            final String user,
            final String password,
            final String userGroup,
            final String entity,
            final ProductPackage productPackage,
            final String returnAssign,
            final int version,
            final @Delimiter(", ") List<String> privileges) {
        String returnToAssign = returnAssign + " v" + version;
        String entityName = "LRM100";
        String entityDescription = "CR entity code";
        String entityCode = "LRM100";
        String entityExternalCode = "LRM100";
        String newUser = user.toLowerCase()+randomAlphanumeric(3).toLowerCase();
        String newUserGroup = userGroup + randomAlphanumeric(3).toLowerCase();

        usersPage
                .open();
        usersPage
                .addUsers(singletonList(newUser));
        userGroupsPage
                .open()
                .addNewUserGroup(newUserGroup, userGroup + randomAlphanumeric(5));
        userGroupsPage
                .addNewUserToGroup(newUserGroup, newUser);

        entitySetupPage
                .open()
                .openOrCreateEntity(entityName,entityDescription,entityCode,entityExternalCode)
                .openAssignReturnsTab();

        entitySetupDialog
                .selectReturnToConfigure(productPackage, returnToAssign);

        entitySetupPage
                .configureReturn(productPackage, returnToAssign);

        entitySetupDialog
                .setUpUserGroupsWithPrivileges(
                        singletonList(userGroup),
                        privileges);

        entitySetupPage
                .assignConfigForEntities();
    }
}