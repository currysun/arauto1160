package com.lombardrisk.page.analysismodule.grid;

public class PeriodDiff {

    private final String startDate;
    private final String startValue;
    private final String difference;
    private final String percentDifference;
    private final String typeOfChange;
    private final String endDate;
    private final String endValue;

    private PeriodDiff(final Builder builder) {
        percentDifference = builder.percentDifference;
        endValue = builder.endValue;
        startValue = builder.startValue;
        difference = builder.difference;
        startDate = builder.startDate;
        endDate = builder.endDate;
        typeOfChange = builder.typeOfChange;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartValue() {
        return startValue;
    }

    public String getDifference() {
        return difference;
    }

    public String getPercentDifference() {
        return percentDifference;
    }

    public String getTypeOfChange() {
        return typeOfChange;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndValue() {
        return endValue;
    }

    @Override
    public String toString() {
        return "CellDifference{" +
                "startPeriodDate='" + startDate + '\'' +
                ", startPeriodValue='" + startValue + '\'' +
                ", difference='" + difference + '\'' +
                ", percentDifference='" + percentDifference + '\'' +
                ", endPeriodDate='" + endDate + '\'' +
                ", endPeriodValue='" + endValue + '\'' +
                ", typeOfChange='" + typeOfChange + '\'' +
                '}';
    }

    public static final class Builder {

        private String startDate;
        private String startValue;
        private String difference;
        private String percentDifference;
        private String typeOfChange;
        private String endDate;
        private String endValue;

        public Builder startPeriodDate(final String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder startPeriodValue(final String startValue) {
            this.startValue = startValue;
            return this;
        }

        public Builder difference(final String difference) {
            this.difference = difference;
            return this;
        }

        public Builder percentDifference(final String percentDifference) {
            this.percentDifference = percentDifference;
            return this;
        }

        public Builder typeOfChange(final String typeOfChange) {
            this.typeOfChange = typeOfChange;
            return this;
        }

        public Builder endPeriodDate(final String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder endPeriodValue(final String endValue) {
            this.endValue = endValue;
            return this;
        }

        public PeriodDiff build() {
            return new PeriodDiff(this);
        }
    }
}
