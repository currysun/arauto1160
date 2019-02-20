package com.lombardrisk.page.analysismodule.returnselectionpanel;

import com.lombardrisk.page.ProductPackage;

import java.time.LocalDate;

public class ReturnSelection {

    private final ProductPackage regulator;
    private final String entityName;
    private final String returnName;
    private final String returnDescription;
    private final LocalDate referenceDate;

    public ReturnSelection(
            final ProductPackage regulator,
            final String entityName,
            final String returnName,
            final String returnDescription,
            final LocalDate referenceDate) {
        this.regulator = regulator;
        this.entityName = entityName;
        this.returnName = returnName;
        this.returnDescription = returnDescription;
        this.referenceDate = referenceDate;
    }

    public ProductPackage regulator() {
        return regulator;
    }

    public String entityName() {
        return entityName;
    }

    public String returnName() {
        return returnName;
    }

    public String returnDescription() {
        return returnDescription;
    }

    public LocalDate referenceDate() {
        return referenceDate;
    }

    @Override
    public String toString() {
        return "ReturnSelection{" +
                "regulator=" + regulator +
                ", entityName='" + entityName + '\'' +
                ", returnName='" + returnName + '\'' +
                ", returnDescription='" + returnDescription + '\'' +
                ", referenceDate=" + referenceDate +
                '}';
    }
}
