@AgileREPORTER  @AR1.16.1 @AR-4594 @ORACLE
Feature: AR-4594 GlobalView Phase 2
  API type including:  plainDatasetContent
  Query parameters including: table, batchId, processDate, chapter, tableType, search, sort, page, size

  #products/HKMA/plainDatasetContent?table=HKLNET:STB Data HKMA&batchId=REG&chapter=HKMA2007&referenceDate=31/12/2013&tableType=T&search=STBGROUP=0071
  @AR-5546 @PlainDataset
  Scenario: plainDatasetContent
    When When I make a Rest call of plaindataset on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |plainDatasetContent     |
      |table          |HKLNET:STB Data HKMA    |
      |batchId        |REG                     |
      |chapter        |HKMA2007                |
      |referenceDate  |31/12/2013              |
      |tableType      |T                       |
      |search         |STBGROUP=0071           |
    Then I get a response from AR of 200
    Then I see table is "T_HKLNET_311213_REG_HKMA2007"
    Then I see records is "158"
    Then I see alias is "STB Data HKMA"
    Then I see total Row count is 12 and page size is 12 under reportContent
    Then I see start Row index is 1 under reportContent
    Then the AR rest response data of plainDataset includes
      |STBIMPINDEX|STBIMPDATE|STBMAPID|STBSTATUS|STBGROUP|STBFORM|STBPAGE|STBITEM|STBFIELD|HKGLC1|HKGLC2|HKGLC3|HKGRADE|S_Amount|HKCUSTID|HKGUARID|HKWORKNUM|HKWORKNUM2|HKWORKALPHA|ReturnId|UniqueID|STBBRANCH|S_HK_ClientRecord|NETGROUP|
      |155|2016-07-10|8185|A|0071|L|LP075|C2A12|S_Amount|12|260|3|2|16476213.9555|RE22062140|0012038321|0|16476213.9555||746|1|0071|1422||
      |156|2016-07-10|8185|A|0071|L|LP075|C2A14|S_Amount|12|260|3|4|16328113.1559|RE22024153|0015012698|16328113.1559|0||746|1|0071|9375||
      |158|2016-07-10|8185|A|0071|L|LP075|C2A12|S_Amount|12|260|3|2|16414505.289|RE97014715|0012039853|0|16414505.289||746|1|0071|1427||
      |211|2016-07-10|8185|A|0071|L|LP075|C2A13|S_Amount|12|260|5|3|13310000|RE22055661|0096002853|13310000|0||746|1|0071|1423||
      |510|2016-07-10|8185|N|0071|L|LP075|C2A14|S_Amount|12|260|3|4|13280000|RE21062935|0012064480|13280000|0||746|1|0071|1498|89|
      |538|2016-07-10|8185|N|0071|L|LP075|C2A14|S_Amount|12|260|3|4|13330000|RE21062935|0022014411|13330000|0||746|1|0071|1424|89|
      |552|2016-07-10|8185|A|0071|L|LP075|C2A15|S_Amount|12|260|5|5|13320000|RE22055692|0022062193|13320000|0||746|1|0071|1425||
      |592|2016-07-10|8185|A|0071|L|LP075|C2A13|S_Amount|12|220|1|3|13290000|RE97014747|0012042886|13290000|0||746|1|0071|1497||
      |594|2016-07-10|8185|A|0071|L|LP075|C2A12|S_Amount|12|220|5|2|16352796.6225|RE22062193|0015007037|0|16352796.6225||746|1|0071|9373||
      |605|2016-07-10|8185|A|0071|L|LP075|C2A13|S_Amount|12|220|1|3|16340454.8892|RE22004720|0015010784|16340454.8892|0||746|1|0071|9374||
      |649|2016-07-10|8185|A|0071|L|LP075|C2A15|S_Amount|12|260|5|5|132700|RE22014391|0015003465|132700|0||746|1|0071|1499||
      |729|2018-08-20|8185|A|0071|L|LP075|C2A14|S_Amount|12|260|3|4|13330000|RE21062935|0022014411|26610000|0||746|1|0071|1424|89|

