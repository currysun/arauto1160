package com.lombardrisk.stepdef.analysismodule.grid;

@SuppressWarnings({"unused", "FieldNamingConvention"})
public enum ChartViewType {
    Value("value"),
    Difference("valueDiff"),
    PercentDifference("percentageDiff");

    private final String selectValueName;

    ChartViewType(final String selectValueName) {
        this.selectValueName = selectValueName;
    }

    public String selectValueName() {
        return selectValueName;
    }
}
