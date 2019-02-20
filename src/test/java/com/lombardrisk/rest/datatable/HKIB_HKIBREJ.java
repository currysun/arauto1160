package com.lombardrisk.rest.datatable;

public class HKIB_HKIBREJ {

    //T_HKIB_310818_REG_HKMA2004  T_QAHKMAFA_310818_QHKMA_QHCH
    //T_HKIBREJ_310818_REG_HKMA2004    T_QAHKMAFA_310818_QHKMA_QHCH

    private String rn;
    private String stbimpindex;
    private String stbinstance;
    private String stbgroup;
    private String hkglc1;
    private String hkglc2;
    private String hkinst;
    private String qa_customerID;
    private String qa_amountCurrency;
    private String hkmatdate;
    private String hkccy;
    private String s_DealRef;
    private String stbdrillRef;
    private String hkbranchctry;
    private String stbitem;
    private String s_Amount;
    private String qa_AccrualAmount;
    private String qa_MaturityDate;
    private String hkaccamnt;
    private String s_RejectedRecord;

    public HKIB_HKIBREJ(String rn, String stbimpindex, String stbinstance, String stbgroup, String hkglc1, String hkglc2, String hkinst,
                        String qa_customerID, String qa_amountCurrency, String hkmatdate, String hkccy, String s_DealRef,
                        String stbdrillRef, String hkbranchctry, String stbitem, String s_Amount, String qa_AccrualAmount,
                        String qa_MaturityDate, String hkaccamnt, String s_RejectedRecord) {
        this.rn = rn;
        this.stbimpindex = stbimpindex;
        this.stbinstance = stbinstance;
        this.stbgroup = stbgroup;
        this.hkglc1 = hkglc1;
        this.hkglc2 = hkglc2;
        this.hkinst = hkinst;
        this.qa_customerID = qa_customerID;
        this.qa_amountCurrency = qa_amountCurrency;
        this.hkmatdate = hkmatdate;
        this.hkccy = hkccy;
        this.s_DealRef = s_DealRef;
        this.stbdrillRef = stbdrillRef;
        this.hkbranchctry = hkbranchctry;
        this.stbitem = stbitem;
        this.s_Amount = s_Amount;
        this.qa_AccrualAmount = qa_AccrualAmount;
        this.qa_MaturityDate = qa_MaturityDate;
        this.hkaccamnt = hkaccamnt;
        this.s_RejectedRecord =s_RejectedRecord;
    }

    public String getS_RejectedRecord() {
        return s_RejectedRecord;
    }

    public void setS_RejectedRecord(String s_RejectedRecord) {
        this.s_RejectedRecord = s_RejectedRecord;
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getStbimpindex() {
        return stbimpindex;
    }

    public void setStbimpindex(String stbimpindex) {
        this.stbimpindex = stbimpindex;
    }

    public String getStbinstance() {
        return stbinstance;
    }

    public void setStbinstance(String stbinstance) {
        this.stbinstance = stbinstance;
    }

    public String getStbgroup() {
        return stbgroup;
    }

    public void setStbgroup(String stbgroup) {
        this.stbgroup = stbgroup;
    }

    public String getHkglc1() {
        return hkglc1;
    }

    public void setHkglc1(String hkglc1) {
        this.hkglc1 = hkglc1;
    }

    public String getHkglc2() {
        return hkglc2;
    }

    public void setHkglc2(String hkglc2) {
        this.hkglc2 = hkglc2;
    }

    public String getHkinst() {
        return hkinst;
    }

    public void setHkinst(String hkinst) {
        this.hkinst = hkinst;
    }

    public String getQa_customerID() {
        return qa_customerID;
    }

    public void setQa_customerID(String qa_customerID) {
        this.qa_customerID = qa_customerID;
    }

    public String getQa_amountCurrency() {
        return qa_amountCurrency;
    }

    public void setQa_amountCurrency(String qa_amountCurrency) {
        this.qa_amountCurrency = qa_amountCurrency;
    }

    public String getHkmatdate() {
        return hkmatdate;
    }

    public void setHkmatdate(String hkmatdate) {
        this.hkmatdate = hkmatdate;
    }

    public String getHkccy() {
        return hkccy;
    }

    public void setHkccy(String hkccy) {
        this.hkccy = hkccy;
    }

    public String getS_DealRef() {
        return s_DealRef;
    }

    public void setS_DealRef(String s_DealRef) {
        this.s_DealRef = s_DealRef;
    }

    public String getStbdrillRef() {
        return stbdrillRef;
    }

    public void setStbdrillRef(String stbdrillRef) {
        this.stbdrillRef = stbdrillRef;
    }

    public String getHkbranchctry() {
        return hkbranchctry;
    }

    public void setHkbranchctry(String hkbranchctry) {
        this.hkbranchctry = hkbranchctry;
    }

    public String getStbitem() {
        return stbitem;
    }

    public void setStbitem(String stbitem) {
        this.stbitem = stbitem;
    }

    public String getS_Amount() {
        return s_Amount;
    }

    public void setS_Amount(String s_Amount) {
        this.s_Amount = s_Amount;
    }

    public String getQa_AccrualAmount() {
        return qa_AccrualAmount;
    }

    public void setQa_AccrualAmount(String qa_AccrualAmount) {
        this.qa_AccrualAmount = qa_AccrualAmount;
    }

    public String getQa_MaturityDate() {
        return qa_MaturityDate;
    }

    public void setQa_MaturityDate(String qa_MaturityDate) {
        this.qa_MaturityDate = qa_MaturityDate;
    }

    public String getHkaccamnt() {
        return hkaccamnt;
    }

    public void setHkaccamnt(String hkaccamnt) {
        this.hkaccamnt = hkaccamnt;
    }
}
