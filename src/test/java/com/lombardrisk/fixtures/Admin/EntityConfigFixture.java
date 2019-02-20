package com.lombardrisk.fixtures.Admin;

import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.entitysetup.EntitySetupDialog;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import cucumber.api.Delimiter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import static java.util.Collections.singletonList;

public class EntityConfigFixture {

    private final EntitySetupPage entitySetupPage;
    private final EntitySetupDialog entitySetupDialog;

    public EntityConfigFixture(final EntitySetupPage entitySetupPage, final EntitySetupDialog entitySetupDialog) {
        this.entitySetupPage = entitySetupPage;
        this.entitySetupDialog = entitySetupDialog;
    }

    public EntityConfigFixture assignReturnAndConfigureEntity(
            final String entityCode,
            final ProductPackage productPackage,
            final String returnAssign,
            final int version,
            final String userGroup,
            final @Delimiter(", ") List<String> privileges
    ) {
        assignReturnAndConfigureEntity(entityCode, productPackage, returnAssign, version, userGroup, privileges, null, null);
        return this;
    }

    public EntityConfigFixture assignReturnAndConfigureEntity(
            final String entityCode,
            final ProductPackage productPackage,
            final String returnAssign,
            final int version,
            final String userGroup,
            final @Delimiter(", ") List<String> privileges,
            final String consoleType,
            final String desiredEntityName) {
        String entityName;
        if (desiredEntityName == null) {
            entityName = entityCode;
        } else {
            entityName = desiredEntityName;
        }

        String entityExternalCode = entityCode;
        String returnToAssign = returnAssign + " v" + version;
        String entityDescription = entityName + RandomStringUtils.randomAlphanumeric(5);
        entitySetupPage
                .open()
                .openOrCreateEntity(entityName, entityDescription, entityCode, entityExternalCode, consoleType)
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
        return this;
    }
}
