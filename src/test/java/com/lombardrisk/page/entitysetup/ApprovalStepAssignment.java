package com.lombardrisk.page.entitysetup;

import com.google.common.collect.ImmutableList;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class ApprovalStepAssignment {

    private final String stepName;
    private final ImmutableList<String> userGroupsToAssigned;

    private ApprovalStepAssignment(final Builder builder) {
        stepName = builder.stepName;
        userGroupsToAssigned = builder.userGroupsToAssigned;
    }

    public static ApprovalStepAssignment.Builder builder() {
        return new ApprovalStepAssignment.Builder();
    }

    public String stepName() {
        return stepName;
    }

    public ImmutableList<String> userGroupsToAssigned() {
        return userGroupsToAssigned;
    }

    public static class Builder {

        private String stepName;
        private ImmutableList<String> userGroupsToAssigned;

        public Builder stepName(final String stepName) {
            this.stepName = requireNonNull(trimToNull(stepName));
            return this;
        }

        public Builder userGroupsToAssigned(final String... userGroups) {
            userGroupsToAssigned = ImmutableList.copyOf(userGroups);
            return this;
        }

        public ApprovalStepAssignment build() {
            return new ApprovalStepAssignment(this);
        }
    }
}
