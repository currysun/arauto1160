@AgileREPORTER  @AR1.16.1 @AR-4594 @ORACLE
Feature: AR-4594 GlobalView Phase 2
  API type including: records, chapters, batchIds, processDates
  Filters including: table, batchId, processDate, chapter

  ##
  @AR-57 @SuperC
  Scenario: AR-4594-SuperC-
    When When I make a Rest call of superC filter on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |records                 |
      |batchId        |QASIA                   |
    Then I get a response from AR of 200
    Then the AR rest response data of records filter includes
      |QAHKCRMS           |QADBDATA                   |
      |QAHKCUST           |QADBDATA                   |
      |QAHKMAFA           |QADBDATA                   |
      |QAHKMARE           |QADBDATA                   |
      |QAHKRATE           |QADBDATA                   |
      |QAHKRTNS           |QADBDATA                   |


  @AR-57 @SuperC
  Scenario: AR-4594-SuperC-pd
    When When I make a Rest call of superC filter on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |processDates            |
    Then I get a response from AR of 200
    Then the AR rest response data of superC other filters includes
      |30/12/0015           |
      |31/12/0015           |
      |31/12/0016           |
      |01/01/2006           |


  #http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/chapters?processDate=31/12/2017&table=HKGDATE:STB Data HKMA
  @AR-57 @SuperC
  Scenario: AR-4594-SuperC-chapter
    When When I make a Rest call of superC filter on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |chapters                |
      |processDate    |31/12/2017              |
      |table          |HKGDATE:STB Data HKMA   |
    Then I get a response from AR of 200
    Then the AR rest response data of superC other filters includes
      |HKMA           |



