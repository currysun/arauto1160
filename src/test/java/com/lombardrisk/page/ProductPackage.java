package com.lombardrisk.page;

/**
 * <p>Should be used as parameter type when using a specific product package</p>
 * <p>Example:</p>
 * <p>BDD</p>
 * <p><code>And I have assigned the following "RBI" returns to my entity BSRVII v3,...</code></p>
 * <p>StepDef</p>
 * <p><pre>
 * {@literal @And}("^I have assigned the following \"(.*)\" returns to my entity (.*)$")
 *  public void iHaveAssignedProductPackageReturnsToMyEntity(
 *      ProductPackage productPackage, @Delimiter(", ") List<String> ecrReturns) {
 *      ...
 *  }
 * </pre></p>
 */
@SuppressWarnings("unused")
public enum ProductPackage implements Dropdown {

    RBI("Reserve Bank of India"),
    ECR("European Common Reporting"),
    FED("US FED Reserve"),
    MFSD("Monetary and Financial Stat Division"),
    HKMA("Hong Kong Monetary Authority"),
    TIC("U.S. Department of The Treasury");

    private final String fullName;

    ProductPackage(final String fullName) {
        this.fullName = fullName;
    }

    public String fullName() {
        return fullName;
    }
}
