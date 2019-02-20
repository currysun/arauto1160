package com.lombardrisk.page.approval;

import com.codeborne.selenide.Condition;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum ApprovalStatus {

    NO_APPROVAL_REQUIRED("No Approval Required"),
    NOT_APPROVED("Not Approved"),
    READY_FOR_APPROVAL("Ready for Approval"),
    PENDING_APPROVAL("Pending Approval"),
    SENT_FOR_REVIEW("Sent for Review"),
    UNDER_REVIEW("Under Review"),
    APPROVED("Approved");

    private final String label;

    ApprovalStatus(final String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public Condition text() {
        return Condition.text(label);
    }

    public static ApprovalStatus toApprovalStatus(final String approvalStatusLabel) {
        return Arrays.stream(values())
                .filter(byEqualLabel(approvalStatusLabel))
                .findFirst()
                .orElseThrow(labelNotFoundError(approvalStatusLabel));
    }

    private static Predicate<ApprovalStatus> byEqualLabel(final String approvalStatusLabel) {
        return approvalStatus -> approvalStatus.label().equalsIgnoreCase(approvalStatusLabel);
    }

    private static Supplier<AssertionError> labelNotFoundError(final String approvalStatusLabel) {
        return () -> new AssertionError("Status with label [" + approvalStatusLabel + "] was not found");
    }
}
