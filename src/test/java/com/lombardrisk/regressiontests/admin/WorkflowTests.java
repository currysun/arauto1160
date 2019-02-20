package com.lombardrisk.regressiontests.admin;

import com.google.common.collect.ImmutableList;
import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.fixtures.LoginFixture;
import com.lombardrisk.fixtures.Workflow.ApprovalWorkflowFixture;
import com.lombardrisk.fixtures.Workflow.WorkflowTemplateFixture;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.administration.ApprovalTemplatePage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.entitysetup.ApprovalStepAssignment;
import com.lombardrisk.page.entitysetup.EntitySetupPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import com.lombardrisk.regressiontests.Issue;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkflowTests extends StepDef {

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private Notification notification;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private EntitySetupPage entitySetupPage;
    @Autowired
    private ApprovalTemplatePage approvalTemplatePage;

    private LoginFixture loginFixture;
    private WorkflowTemplateFixture workflowTemplateFixture;
    private ApprovalWorkflowFixture approvalWorkflowFixture;

    @Before
    public void setUp() {
        loginFixture = new LoginFixture(loginPage, dashboardPage, notification, mainMenu);
        workflowTemplateFixture = new WorkflowTemplateFixture(approvalTemplatePage);
        approvalWorkflowFixture = new ApprovalWorkflowFixture(entitySetupPage);
    }

    @Test
    @Issue("AR1036")
    public void assignApprovalTemplateToForm() {
        loginFixture.loginAsAdmin();

        approvalWorkflowFixture
                .openReturnConfiguration("LRM111", ProductPackage.RBI, "CICDP v1")
                .assignApprovalWorkflow(
                        "AR-1038",
                        ImmutableList.of(
                                ApprovalStepAssignment.builder()
                                        .stepName("STEP1")
                                        .userGroupsToAssigned("workgroup1")
                                        .build(),
                                ApprovalStepAssignment.builder()
                                        .stepName("STEP2")
                                        .userGroupsToAssigned("workgroup2")
                                        .build(),
                                ApprovalStepAssignment.builder()
                                        .stepName("STEP3")
                                        .userGroupsToAssigned("workgroup3")
                                        .build()))
                .save();
    }

    @Test
    @Issue("AR1036")
    public void createApprovalTemplate() {
        loginFixture.loginAsAdmin();

        workflowTemplateFixture
                .addNewTemplate("AR-1038", "WK-3STEP DESC")
                .addTemplateSteps(
                        ImmutableList.of(
                                Pair.of("STEP1", 2),
                                Pair.of("STEP2", 1),
                                Pair.of("STEP3", 1)))
                .create()
                .checkTemplateDetails()
                .checkTemplateSteps();
    }
}
