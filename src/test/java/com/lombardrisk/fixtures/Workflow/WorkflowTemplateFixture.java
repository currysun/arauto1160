package com.lombardrisk.fixtures.Workflow;

import com.google.common.collect.ImmutableList;
import com.lombardrisk.page.administration.ApprovalTemplatePage;
import org.apache.commons.lang3.tuple.Pair;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

public class WorkflowTemplateFixture {

    private final ApprovalTemplatePage approvalTemplatePage;

    private String templateName;
    private String templateDescription;
    private ImmutableList<Pair<String, Integer>> stepNameAndCountPairs;

    public WorkflowTemplateFixture(final ApprovalTemplatePage approvalTemplatePage) {
        this.approvalTemplatePage = requireNonNull(approvalTemplatePage);
    }

    public WorkflowTemplateFixture addNewTemplate(final String templatePrefix, final String templateDescription) {
        approvalTemplatePage
                .open()
                .addNewTemplate();
        templateName = approvalTemplatePage.setUniqueTemplateName(templatePrefix);

        this.templateDescription = templateDescription;
        approvalTemplatePage
                .shouldBeActive()
                .templateDescription(templateDescription);

        return this;
    }

    public WorkflowTemplateFixture addTemplateSteps(final ImmutableList<Pair<String, Integer>> stepNameAndCountPairs) {
        this.stepNameAndCountPairs = stepNameAndCountPairs;

        String firstStepName = stepNameAndCountPairs.get(0).getLeft();
        int firstStepCount = stepNameAndCountPairs.get(0).getRight();
        approvalTemplatePage
                .setFirstStep(firstStepName, firstStepCount);

        addNextSteps(stepNameAndCountPairs);

        return this;
    }

    private void addNextSteps(final ImmutableList<Pair<String, Integer>> stepNameAndCountPairs) {
        if (stepNameAndCountPairs.size() > 1) {
            for (int i = 1; i < stepNameAndCountPairs.size(); i++) {

                String stepName = stepNameAndCountPairs.get(i).getLeft();
                int stepCount = stepNameAndCountPairs.get(i).getRight();

                approvalTemplatePage.addNewStep(stepName, stepCount);
            }
        }
    }

    public WorkflowTemplateFixture create() {
        approvalTemplatePage.createTemplate();
        return this;
    }

    public WorkflowTemplateFixture checkTemplateDetails() {
        approvalTemplatePage
                .selectTemplateWithName(templateName)
                .shouldHaveTemplateDescription(trimToEmpty(templateDescription));

        return this;
    }

    public void checkTemplateSteps() {
        for (int i = 0; i < stepNameAndCountPairs.size(); i++) {
            Pair<String, Integer> anotherStep = stepNameAndCountPairs.get(i);

            approvalTemplatePage.shouldHaveStep(
                    i, anotherStep.getLeft(), anotherStep.getRight());
        }
    }
}

