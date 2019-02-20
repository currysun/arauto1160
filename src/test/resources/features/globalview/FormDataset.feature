@AgileREPORTER  @AR1.16.1 @AR-4594 @ORACLE
Feature: AR-4594 GlobalView Phase 2
  API type including: allocationReportContent, rejectionDatasetContent, formDatasetContent
  Query parameters including: formInstanceId, reportId, search, sort, page, size

  #499074  L 0007 31/12/2013
  #500238  E 0007 31/12/2013
  #http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/formDatasetContent?formInstanceId=499074&reportId=68
  #T_HKLSEC1_311213_REG_HKMA2007
  @AR-5546 @Allocation
  Scenario: formDatasetContent
    Given a user is authenticated by "test" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |formDatasetContent      |
      |formInstanceId |499074                  |
      |reportId       |68                      |
    Then I get a response from AR of 200
    Then I see total Row count is 1291 and page size is 50
    Then I see start Row index is 1
    And the AR rest response data of dataset includes
      |STBIMPINDEX|STBIMPDATE|STBSTATUS|STBFIELD|STBFORM|STBGROUP|STBITEM|STBPAGE|S_CollDealRef|S_TAVA_Ref|S_Amount|S_CRMAmount|STBDrillRef|HKPROCDATE|HKBOOK|HKCUSTID|
      |14|2016-02-25|N|||0071|||CC000341|||550000|497||||

