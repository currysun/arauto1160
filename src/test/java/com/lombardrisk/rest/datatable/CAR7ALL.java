package com.lombardrisk.rest.datatable;

public class CAR7ALL {

    //oracle alias: ECR_SYSTEM/ECR_DATA, 172.20.20.49/ora12c
    //Allocation drill table:T_CAR7ALL_311217_ECR_ECR

    private String rn;
    private String stbimpindex;
    private String stbpage;
    private String stbgroup;
    private String stbitem;
    private String s_FormValue;
    private String s_CustomerID;
    private String s_DateRef1;
    private String s_DealRef;
    private String s_GLC1;
    private String s_GLC2;
    private String s_InstitutionCode;
    private String s_Ref2;
    private String s_ReportingDate;
    private String s_TAVA_Ref;
    private String s_ApplicablePercentage;
    private String s_CapitalAmount;
    private String s_DeductedAmount;
    private String s_DeductFromCapital;
    private String s_Issuer;
    private String cCName;
    private String stbimpdate;

    public CAR7ALL(String rn, String stbimpindex, String stbpage, String stbgroup, String stbitem, String s_FormValue, String s_CustomerID,
                   String s_DateRef1, String s_DealRef, String s_GLC1, String s_GLC2, String s_InstitutionCode, String s_Ref2, String s_ReportingDate,
                   String s_TAVA_Ref, String s_ApplicablePercentage, String s_CapitalAmount, String s_DeductedAmount, String s_DeductFromCapital,
                   String s_Issuer, String cCName, String stbimpdate)
    {
        this.rn = rn;
        this.stbimpindex = stbimpindex;
        this.stbpage = stbpage;
        this.stbgroup = stbgroup;
        this.stbitem = stbitem;
        this.s_FormValue = s_FormValue;
        this.s_CustomerID = s_CustomerID;
        this.s_DateRef1 = s_DateRef1;
        this.s_DealRef = s_DealRef;
        this.s_GLC1 = s_GLC1;
        this.s_GLC2 = s_GLC2;
        this.s_InstitutionCode = s_InstitutionCode;
        this.s_Ref2 = s_Ref2;
        this.s_ReportingDate = s_ReportingDate;
        this.s_TAVA_Ref = s_TAVA_Ref;
        this.s_ApplicablePercentage = s_ApplicablePercentage;
        this.s_CapitalAmount = s_CapitalAmount;
        this.s_DeductedAmount = s_DeductedAmount;
        this.s_DeductFromCapital = s_DeductFromCapital;
        this.s_Issuer = s_Issuer;
        this.cCName = cCName;
        this.stbimpdate = stbimpdate;
    }

    public String getRn() {
        return rn;
    }

    public String getStbimpindex() {
        return stbimpindex;
    }

    public String getStbpage() {
        return stbpage;
    }

    public String getStbgroup() {
        return stbgroup;
    }

    public String getStbitem() {
        return stbitem;
    }

    public String getS_FormValue() {
        return s_FormValue;
    }

    public String getS_CustomerID() {
        return s_CustomerID;
    }

    public String getS_DateRef1() {
        return s_DateRef1;
    }

    public String getS_DealRef() {
        return s_DealRef;
    }

    public String getS_GLC1() {
        return s_GLC1;
    }

    public String getS_GLC2() {
        return s_GLC2;
    }

    public String getS_InstitutionCode() {
        return s_InstitutionCode;
    }

    public String getS_Ref2() {
        return s_Ref2;
    }

    public String getS_ReportingDate() {
        return s_ReportingDate;
    }

    public String getS_TAVA_Ref() {
        return s_TAVA_Ref;
    }

    public String getS_ApplicablePercentage() {
        return s_ApplicablePercentage;
    }

    public String getS_CapitalAmount() {
        return s_CapitalAmount;
    }

    public String getS_DeductedAmount() {
        return s_DeductedAmount;
    }

    public String getS_DeductFromCapital() {
        return s_DeductFromCapital;
    }

    public String getS_Issuer() {
        return s_Issuer;
    }

    public String getcCName() {
        return cCName;
    }

    public String getStbimpdate() {
        return stbimpdate;
    }
}
