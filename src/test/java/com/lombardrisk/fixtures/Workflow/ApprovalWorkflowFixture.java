package com.lombardrisk.fixtures.Workflow;

import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.entitysetup.ApprovalStepAssignment;
import com.lombardrisk.page.entitysetup.EntitySetupPage;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ApprovalWorkflowFixture {

    private final EntitySetupPage entitySetupPage;

    public ApprovalWorkflowFixture(final EntitySetupPage entitySetupPage) {
        this.entitySetupPage = requireNonNull(entitySetupPage);
    }

    public ApprovalWorkflowFixture assignApprovalWorkflow(
            final String approvalWorkflowPrefix,
            final List<ApprovalStepAssignment> approvalStepAssignments) {

        entitySetupPage.assignWorkflow(approvalWorkflowPrefix);

        approvalStepAssignments.forEach(this::assignApprovalStep);

        return this;
    }

    public ApprovalWorkflowFixture openReturnConfiguration(
            final String entityName, final ProductPackage productPackage, final String returnName) {
        entitySetupPage
                .open()
                .selectEntity(entityName)
                .openAssignReturnsTab();

        entitySetupPage.configureReturn(productPackage, returnName);

        return this;
    }

    public void save() {
        entitySetupPage.saveApprovalWorkflow();
    }

    private void assignApprovalStep(final ApprovalStepAssignment approvalStepAssignment) {
        for (String userGroupName : approvalStepAssignment.userGroupsToAssigned()) {

            entitySetupPage.assignGroupToStep(userGroupName, approvalStepAssignment.stepName());
        }
    }
}
