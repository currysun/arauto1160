@AgileREPORTER  @AR1.16.1 @AR-4594 @ORACLE
Feature: AR-4594 GlobalView Phase 2
  API type including: allocationReportContent, rejectionDatasetContent, formDatasetContent
  Query parameters including: formInstanceId, reportId, search, sort, page, size

  #http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/rejectionDatasetContent?formInstanceId=430398
  #430398  IB  0001  31/08/2018
  @AR-5546 @Allocation
  Scenario: AR-4594-GV-01-01 None parameters
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |allocationReportContent |
      |formInstanceId |430398                  |
    Then I get a response from AR of 200
    Then I see total Row count is 4173 and page size is 50
    Then I see start Row index is 1
    And the AR rest response data of allocation includes
      |rn|stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1|1606|1|0001|31|2135|300|KMCUST055|70200|2019-05-31|CNY|150629|HK|IBD16CN|107373.07971|87000|2014-09-30|107373.07971|
      |2|1627|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150650|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |3|1720|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150743|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |4|1738|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150761|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |5|1607|1|0001|31|2136|202|KMCUST056|70100|2020-01-28|CNY|150630|HK|IBD16CN|107249.662377|86900|2015-05-30|107249.662377|
      |6|1919|1|0001|31|2153|201|KMCUST052|145100|2020-01-28|CNY|150942|HK|IBD16CN|101078.795727|81900|2015-05-30|101078.795727|
      |7|1920|1|0001|31|2153|201|KMCUST052|153200|2021-05-02|CNY|150943|HK|IBD16CN|98733.8664|80000|2016-09-01|98733.8664|
      |49|1533|1|0001|12|260|202|KMCUST054|77500|2019-01-13|CNY|150556|HK|IBD16CN|212030.978094|94300|2014-05-15|116382.545019|
      |50|1927|1|0001|31|2153|201|KMCUST052|209900|2019-01-13|CNY|150950|HK|IBD16CN|82319.361111|66700|2014-05-15|82319.361111|
    And the AR rest response data of dataset includes
      |RN|STBIMPINDEX|STBInstance|STBGROUP|HKGLC1|HKGLC2|HKINST|QA_CustomerID|QA_AmountCurrency|HKMATDATE|HKCCY|STBDrillRef|HKBRANCHCTRY|STBITEM|S_Amount|QA_AccrualAmount|QA_MaturityDate|HKACCAMNT|
      |1|1606|1|0001|31|2135|300|KMCUST055|70200|2019-05-31|CNY|150629|HK|IBD16CN|107373.07971|87000|2014-09-30|107373.07971|
      |2|1627|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150650|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |3|1720|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150743|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |4|1738|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150761|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |5|1607|1|0001|31|2136|202|KMCUST056|70100|2020-01-28|CNY|150630|HK|IBD16CN|107249.662377|86900|2015-05-30|107249.662377|
      |6|1919|1|0001|31|2153|201|KMCUST052|145100|2020-01-28|CNY|150942|HK|IBD16CN|101078.795727|81900|2015-05-30|101078.795727|
      |7|1920|1|0001|31|2153|201|KMCUST052|153200|2021-05-02|CNY|150943|HK|IBD16CN|98733.8664|80000|2016-09-01|98733.8664|
      |49|1533|1|0001|12|260|202|KMCUST054|77500|2019-01-13|CNY|150556|HK|IBD16CN|212030.978094|94300|2014-05-15|116382.545019|
      |50|1927|1|0001|31|2153|201|KMCUST052|209900|2019-01-13|CNY|150950|HK|IBD16CN|82319.361111|66700|2014-05-15|82319.361111|


  @AR-5547 @Allocation
  Scenario: AR-4594-GV-01-02 parameter filter and pagination
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |STBITEM=IBD17HK,HKACCAMNT>120000,QA_MaturityDate<01/09/2019  |
      |size            |10                      |
      |page            |3                       |
    Then I get a response from AR of 200
    Then I see total Row count is 133 and page size is 10
    Then I see start Row index is 31
    Then I see response data is filtered with column "STBITEM" on "IBD17HK" by using operator "="
    Then I see response data is filtered with column "HKACCAMNT" on "120000" by using operator ">"
    Then I see response data is filtered with column "QA_MaturityDate" on "01/09/2019" by using operator "<"
    And the AR rest response data of allocation includes
      |stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1167|1|0001|12|260|102|KMCUST059|114400|2019-10-02|HKD|150190|HK|IBD17HK|268400|154000|2015-02-01|154000|
      |1889|1|0001|12|220|200|KMCUST022|91500|2019-01-13|CNY|150912|HK|IBD17HK|588700.67841|385500|2014-05-15|475773.818715|
      |1890|1|0001|12|220|200|KMCUST022|91500|2019-05-31|CNY|150913|HK|IBD17HK|588700.67841|385500|2014-09-30|475773.818715|
      |1891|1|0001|12|220|200|KMCUST022|91500|2020-01-28|CNY|150914|HK|IBD17HK|588700.67841|385500|2015-05-30|475773.818715|
      |1892|1|0001|12|220|200|KMCUST022|91500|2021-05-02|CNY|150915|HK|IBD17HK|588700.67841|385500|2016-09-01|475773.818715|
      |1893|1|0001|12|220|200|KMCUST022|91500|2022-05-02|CNY|150916|HK|IBD17HK|588700.67841|385500|2017-09-01|475773.818715|
      |1391|1|0001|12|260|200|KMCUST035|91700|2022-05-02|CNY|150414|HK|IBD17HK|613630.979676|405500|2017-09-01|500457.285315|
      |1461|1|0001|12|260|300|KMCUST055|84700|2020-05-30|CNY|150484|HK|IBD17HK|229803.074046|101500|2015-09-30|125268.592995|
      |1463|1|0001|12|260|201|KMCUST053|84500|2021-05-02|CNY|150486|HK|IBD17HK|229309.404714|101300|2016-09-01|125021.758329|
      |1454|1|0001|11|140|101|KMCUST058|85400|2022-05-02|CNY|150477|HK|IBD17HK|231530.916708|102200|2017-09-01|126132.514326|


  #pass
  @AR-5548 @Allocation
  Scenario: AR-4594-GV-01-03 sort and pagination
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |QA_MaturityDate>30/05/2015,QA_MaturityDate<01/09/2019  |
      |size            |20                       |
      |page            |0                        |
      |sort            |S_Amount:desc,STBITEM,STBDrillRef:desc                 |
    Then I get a response from AR of 200
    Then I see total Row count is 553 and page size is 20
    Then I see start Row index is 1
    Then I see response data is filtered with column "QA_MaturityDate" on "30/05/2015" by using operator ">"
    Then I see response data is filtered with column "QA_MaturityDate" on "01/09/2019" by using operator "<"
    And the AR rest response data of allocation includes
      |rn|stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |7|1288|7|0001|14|480|300|KMCUST051|102000|2021-05-02|CNY|150311|HK|IBB4HK|1576656.429075|1175500|2016-09-01|1450770.749415|
      |8|1288|7|0001|14|480|300|KMCUST051|102000|2021-05-02|CNY|150311|HK|IBB5HK|1576656.429075|1175500|2016-09-01|1450770.749415|
      |9|1901|7|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBA8HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |10|1269|7|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBA8HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |11|1901|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBD16CN|1574188.082415|1275500|2017-09-01|1574188.082415|
      |12|1269|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBD16CN|1574188.082415|1275500|2017-09-01|1574188.082415|
      |13|1901|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBD17HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |14|1269|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBD17HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |15|1901|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBD1HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |16|1269|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBD1HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |17|1901|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBD2HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |18|1269|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBD2HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |19|1901|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150924|HK|IBD6HK|1574188.082415|1275500|2017-09-01|1574188.082415|
      |20|1269|1|0001|31|2172|103|KMCUST060|103000|2022-05-02|CNY|150292|HK|IBD6HK|1574188.082415|1275500|2017-09-01|1574188.082415|


  #pass
  @AR-5549 @Allocation
  Scenario: AR-4594-GV-01-04 parameter filter contains and pagination
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |QA_CustomerID:er00003,HKACCAMNT>25000,HKACCAMNT<50000   |
      |size            |5                       |
      |page            |1                       |
    Then I get a response from AR of 200
    Then I see total Row count is 65 and page size is 5
    Then I see start Row index is 6
    Then I see response data is filtered with column "QA_CustomerID" on "er00003" by using operator ":"
    And the AR rest response data of allocation includes
      |stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1947|1|0001|11|140|201|201order0000302|371900|2021-05-02|HKD|150970|HK|IBD16UY|400600|28700|2016-09-01|28700|
      |1948|1|0001|11|140|201|201order0000302|380000|2022-05-02|HKD|150971|HK|IBD16UY|406800|26800|2017-09-01|26800|
      |1936|1|0001|11|140|201|201order0000302|282800|2018-10-30|HKD|150959|HK|IBD16UY|332400|49600|2014-03-01|49600|
      |1937|1|0001|11|140|201|201order0000302|290900|2019-01-13|HKD|150960|HK|IBD16UY|338600|47700|2014-05-15|47700|
      |1938|1|0001|11|140|201|201order0000302|299000|2019-05-31|HKD|150961|HK|IBD16UY|344800|45800|2014-09-30|45800|


  #pass
  @AR-5550 @Rejection
  Scenario: AR-4594-GV-01-05 Verify rejectionContent content with none parameter---IB v1
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
    Then I get a response from AR of 200
    Then I see total Row count is 1086 and page size is 50
    Then I see start Row index is 1
    And the AR rest response data of rejection includes
      |rn|stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|
      |1|1305|0001|30|2020|112|KMCUST050|100300|2022-05-02|CNY|150328|HK|123787.584999|1005500|2017-09-01|1240961.283315|Decision rejection|
      |2|1306|0001|30|2020|300|KMCUST051|100200|2024-05-01|CNY|150329|HK|123664.167666|995500|2019-09-01|1228619.550015|Decision rejection|
      |3|1511|0001|30|2010|112|KMCUST050|79700|2018-09-01|CNY|150534|HK|98363.614401|96500|2014-01-01|119097.726345|Decision rejection|
      |4|1474|0001|12|250|300|KMCUST051|83400|2018-09-01|CNY|150497|HK|102930.055722|100200|2014-01-01|123664.167666|Decision rejection|
      |5|1476|0001|12|270|201|KMCUST052|83200|2018-09-25|CNY|150499|HK|102683.221056|100000|2014-01-25|123417.333|Decision rejection|
      |6|1481|0001|11|120|100|KMCUST057|82700|2022-05-02|CNY|150504|HK|102066.134391|99500|2017-09-01|122800.246335|Decision rejection|
      |47|1783|0001|30|2020|300|KMCUST051|64400|2024-08-31|CNY|150806|HK|79480.762452|81200|2020-01-01|100214.874396|Decision rejection|
      |48|1784|0001|30|2022|300|KMCUST051|64300|2024-08-31|CNY|150807|HK|79357.345119|81100|2020-01-01|100091.457063|Decision rejection|
      |49|1785|0001|30|2030|300|KMCUST051|64200|2024-08-31|CNY|150808|HK|79233.927786|81000|2020-01-01|99968.03973|Decision rejection|
      |50|1453|0001|11|140|100|KMCUST057|85500|2020-01-28|CNY|150476|HK|105521.819715|102300|2015-05-30|126255.931659|Decision rejection|


  #rejectionDatasetContent
  #pass
  @AR-5551 @Rejection
  Scenario: AR-4594-GV-01-06 Verify rejection report content with parameter filter---IB v1 (size is more than total)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |search          |QA_CustomerID:UST051,QA_AccrualAmount>90000,QA_MaturityDate>30/05/2015  |
      |size            |20                      |
    Then I get a response from AR of 200
    Then I see total Row count is 19 and page size is 19
    Then I see response data is filtered with column "QA_MaturityDate" on "30/05/2015" by using operator ">"
    And the AR rest response data of rejection includes
      |rn|stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|stbitem|stbinstance|
      |7|1297|0001|30|2020|300|KMCUST051|101100|2024-05-01|CNY|150320|HK|124774.923663|1085500|2019-09-01|1339695.149715|Decision rejection|||
      |8|1501|0001|30|2020|300|KMCUST051|80700|2024-05-01|CNY|150524|HK|99597.787731|97500|2019-09-01|120331.899675|Decision rejection|||
      |9|1510|0001|30|2029|300|KMCUST051|79800|2024-05-01|CNY|150533|HK|98487.031734|96600|2019-09-01|119221.143678|Decision rejection|||
      |10|1913|0001|31|2158|300|KMCUST051|96500|2024-08-31|CNY|150936|HK|119097.726345|93300|2020-01-01|115148.371689|Not include in B1&C1|A8||
      |11|1537|0001|12|260|300|KMCUST051|77100|2024-05-01|CNY|150560|HK|211043.63943|93900|2019-09-01|115888.875687|No need report - Column 13|D13|7|
      |12|1528|0001|12|260|300|KMCUST051|78000|2024-05-01|CNY|150551|HK|213265.151424|94800|2019-09-01|116999.631684|No need report - Column 13|D13|7|
      |13|1555|0001|12|260|300|KMCUST051|75300|2024-05-01|CNY|150578|HK|206600.615442|92100|2019-09-01|113667.363693|No need report - Column 13|D13|7|

  #pass
  @AR-5552 @Rejection
  Scenario: AR-4594-GV-01-07 Verify rejection report Content with parameter filter and sort---IB v1
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |search          |QA_CustomerID=KMCUST051,QA_AccrualAmount>90000,QA_MaturityDate>30/05/2015  |
      |sort            |HKGLC2,S_Amount:desc     |
    Then I get a response from AR of 200
    Then I see total Row count is 19 and page size is 19
    And the AR rest response data of rejection includes
      |rn|stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|
      |7|1564|0001|12|260|300|KMCUST051|74400|2024-05-01|CNY|150587|HK|204379.103448|91200|2019-09-01|112556.607696|No need report - Column 13|
      |8|1573|0001|12|260|300|KMCUST051|73500|2024-05-01|CNY|150596|HK|202157.591454|90300|2019-09-01|111445.851699|No need report - Column 13|
      |9|1297|0001|30|2020|300|KMCUST051|101100|2024-05-01|CNY|150320|HK|124774.923663|1085500|2019-09-01|1339695.149715|Decision rejection|
      |10|1306|0001|30|2020|300|KMCUST051|100200|2024-05-01|CNY|150329|HK|123664.167666|995500|2019-09-01|1228619.550015|Decision rejection|
      |11|1492|0001|30|2020|300|KMCUST051|81600|2024-05-01|CNY|150515|HK|100708.543728|98400|2019-09-01|121442.655672|Decision rejection|
      |12|1501|0001|30|2020|300|KMCUST051|80700|2024-05-01|CNY|150524|HK|99597.787731|97500|2019-09-01|120331.899675|Decision rejection|
      |13|1356|0001|30|2029|300|KMCUST051|95200|2024-05-01|CNY|150379|HK|117493.301016|755500|2019-09-01|932417.950815|Decision rejection|


 
  @AR-5553 @Rejection
  Scenario: AR-4594-GV-01-08 Verify rejection report Content with parameter filter, sort and Pagination---IB v1
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |search          |QA_MaturityDate=30/05/2015  |
      |sort            |S_Amount:desc           |
      |size            |15                     |
      |page            |5                      |
    Then I get a response from AR of 200
    Then I see total Row count is 81 and page size is 6
    Then I see start Row index is 76
    And the AR rest response data of rejection includes
      |stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|stbinstance|
      |1703|0001|31|2170|202|KMCUST056|68000|2020-01-28|CNY|150726|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1||
      |1685|0001|31|2170|202|KMCUST056|68000|2020-01-28|CNY|150708|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1||
      |1649|0001|31|2156|202|KMCUST056|68000|2020-01-28|CNY|150672|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1||
      |1637|0001|31|2178|202|KMCUST056|67100|2020-01-28|CNY|150660|HK|82813.030443|83900|2015-05-30|103547.142387|Not include in B1&C1||
      |1640|0001|31|2181|202|KMCUST056|66800|2020-01-28|CNY|150663|HK|82442.778444|83600|2015-05-30|103176.890388|Not include in B1&C1||
      |1929|0001|31|2153|201|KMCUST052|226100|2020-01-28|CNY|150952|HK|77629.502457|62900|2015-05-30|77629.502457|No need report - Column 13|7|


  #http://10.20.0.83:8080/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=430398
  #pass
  @AR-5604 @Allocation
  Scenario: AR-4594-GV-02-01 Verify failed response when get allocation report by user unauthorized
    Given a user is authenticated by "admin" and "1111111"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
    Then I get a response from AR of 401

  #http://10.20.0.83:8080/agilereporter/rest/api/ZZHKMA/allocationReportContent?formInstanceId=430398
  #pass
  @AR-5605 @Allocation
  Scenario: AR-4594-GV-02-02 Verify failed response when get allocation report by invalid product prefix
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |ZZHKMA                  |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
    Then I get a response from AR of 400
    Then I get error info "configPackage ZZHKMA can not be found"

  #http://10.20.0.83:8080/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=100000
  #pass
  @AR-5606 @Allocation
  Scenario: AR-4594-GV-02-03 Verify failed response when get allocation report by invalid formInstanceId
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |100000                  |
    Then I get a response from AR of 400

#pass
  @AR-5610 @Rejection
  Scenario: AR-4594-GV-02-07 Verify failed response when get rejection report by user unauthorized
    Given a user is authenticated by "test" and "1111111"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |rejectionDatasetContent |
      |formInstanceId  |430398                  |
    Then I get a response from AR of 401

#pass
  @AR-5611 @Rejection
  Scenario: AR-4594-GV-02-08 Verify failed response when get rejection report by invalid product prefix
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |XXHKMA                  |
      |ApiType         |rejectionDatasetContent |
      |formInstanceId  |430398                  |
    Then I get a response from AR of 400
    Then I get error info "configPackage XXHKMA can not be found"

#pass
  @AR-5612 @Rejection
  Scenario: AR-4594-GV-02-09 Verify failed response when get rejection report by invalid formInstanceId
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |rejectionDatasetContent |
      |formInstanceId  |100000                  |
    Then I get a response from AR of 400


  #pass
  @AR-5608 @Allocation
  Scenario: AR-4594-GV-02-04 Verify get allocation report content by invalid filter/sort column name -- (ABC=Y, ABC:desc -- ABC is not exist)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |QA_CustomerID:er00003,ABC=Y,HKACCAMNT>25000,HKACCAMNT<50000   |
      |size            |5                       |
      |page            |1                       |
      |sort            |ABC:desc      |
    Then I get a response from AR of 200
    Then I see total Row count is 65 and page size is 5
    Then I see start Row index is 6
    Then I see response data is filtered with column "QA_CustomerID" on "er00003" by using operator ":"
    And the AR rest response data of allocation includes
      |stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1947|1|0001|11|140|201|201order0000302|371900|2021-05-02|HKD|150970|HK|IBD16UY|400600|28700|2016-09-01|28700|
      |1948|1|0001|11|140|201|201order0000302|380000|2022-05-02|HKD|150971|HK|IBD16UY|406800|26800|2017-09-01|26800|
      |1936|1|0001|11|140|201|201order0000302|282800|2018-10-30|HKD|150959|HK|IBD16UY|332400|49600|2014-03-01|49600|
      |1937|1|0001|11|140|201|201order0000302|290900|2019-01-13|HKD|150960|HK|IBD16UY|338600|47700|2014-05-15|47700|
      |1938|1|0001|11|140|201|201order0000302|299000|2019-05-31|HKD|150961|HK|IBD16UY|344800|45800|2014-09-30|45800|



  #fail
  @AR-5607 @Allocation
  Scenario: AR-4594-GV-02-05 Verify get allocation report content by invalid filter value -- ( value N is not exist)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |QA_CustomerID:er00003,HKCCY=N   |
      |size            |5                       |
      |page            |1                       |
    Then I get a response from AR of 200
    Then I see no data return
    Then I see start Row index is 0


  #fail
  @AR-5609 @Allocation
  Scenario: AR-4594-GV-02-06 Verify get allocation report content by invalid page number( page number is more than total/size)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |STBITEM=IBD17HK,HKACCAMNT>120000,QA_MaturityDate<01/09/2019  |
      |size            |10                      |
      |page            |20                      |
    Then I get a response from AR of 200
    Then I see no data return


  @AR-5613 @Rejection
  Scenario: AR-4594-GV-02-10 Verify get rejection report content by invalid filter/sort column name -- (ABC=Y, ABC:asc -- ABC is not exist)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |search          |QA_MaturityDate=30/05/2015,ABC=Y  |
      |sort            |S_Amount:desc,ABC:asc  |
      |size            |15                     |
      |page            |5                      |
    Then I get a response from AR of 200
    Then I see total Row count is 81 and page size is 6
    Then I see start Row index is 76
    And the AR rest response data of rejection includes
      |stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|
      |1703|0001|31|2170|202|KMCUST056|68000|2020-01-28|CNY|150726|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1|
      |1685|0001|31|2170|202|KMCUST056|68000|2020-01-28|CNY|150708|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1|
      |1649|0001|31|2156|202|KMCUST056|68000|2020-01-28|CNY|150672|HK|83923.78644|84800|2015-05-30|104657.898384|Not include in B1&C1|
      |1637|0001|31|2178|202|KMCUST056|67100|2020-01-28|CNY|150660|HK|82813.030443|83900|2015-05-30|103547.142387|Not include in B1&C1|
      |1640|0001|31|2181|202|KMCUST056|66800|2020-01-28|CNY|150663|HK|82442.778444|83600|2015-05-30|103176.890388|Not include in B1&C1|
      |1929|0001|31|2153|201|KMCUST052|226100|2020-01-28|CNY|150952|HK|77629.502457|62900|2015-05-30|77629.502457|No need report - Column 13|


  #fail
  @AR-5614 @Rejection
  Scenario: AR-4594-GV-02-11 Verify get rejection report content by invalid filter/sort value - (HKCCY=Y where Y is not exist)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |search          |QA_MaturityDate=30/05/2015,HKCCY=Y  |
      |sort            |S_Amount:desc           |
      |size            |15                     |
      |page            |5                      |
    Then I get a response from AR of 200
    Then I see no data return
    Then I see start Row index is 0


  @AR-5615 @Rejection
  Scenario: AR-4594-GV-02-12 Verify get rejection report content by invalid Pagination (invalid page number == -1)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |rejectionDatasetContent |
      |formInstanceId |430398                  |
      |page           |-1                      |
    Then I get a response from AR of 200
    Then I see total Row count is 1086 and page size is 50
    And the AR rest response data of rejection includes
      |rn|stbimpindex|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|s_RejectedRecord|
      |1|1305|0001|30|2020|112|KMCUST050|100300|2022-05-02|CNY|150328|HK|123787.584999|1005500|2017-09-01|1240961.283315|Decision rejection|
      |2|1306|0001|30|2020|300|KMCUST051|100200|2024-05-01|CNY|150329|HK|123664.167666|995500|2019-09-01|1228619.550015|Decision rejection|
      |3|1511|0001|30|2010|112|KMCUST050|79700|2018-09-01|CNY|150534|HK|98363.614401|96500|2014-01-01|119097.726345|Decision rejection|
      |4|1474|0001|12|250|300|KMCUST051|83400|2018-09-01|CNY|150497|HK|102930.055722|100200|2014-01-01|123664.167666|Decision rejection|
      |5|1476|0001|12|270|201|KMCUST052|83200|2018-09-25|CNY|150499|HK|102683.221056|100000|2014-01-25|123417.333|Decision rejection|
      |6|1481|0001|11|120|100|KMCUST057|82700|2022-05-02|CNY|150504|HK|102066.134391|99500|2017-09-01|122800.246335|Decision rejection|
      |47|1783|0001|30|2020|300|KMCUST051|64400|2024-08-31|CNY|150806|HK|79480.762452|81200|2020-01-01|100214.874396|Decision rejection|
      |48|1784|0001|30|2022|300|KMCUST051|64300|2024-08-31|CNY|150807|HK|79357.345119|81100|2020-01-01|100091.457063|Decision rejection|
      |49|1785|0001|30|2030|300|KMCUST051|64200|2024-08-31|CNY|150808|HK|79233.927786|81000|2020-01-01|99968.03973|Decision rejection|
      |50|1453|0001|11|140|100|KMCUST057|85500|2020-01-28|CNY|150476|HK|105521.819715|102300|2015-05-30|126255.931659|Decision rejection|


  @AR-5772 @Allocation
  Scenario : AR-4594-GV-02-13 invalid page number (page == -1)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |allocationReportContent |
      |formInstanceId |430398                  |
      |page           |-1                      |
    Then I get a response from AR of 200
    Then I see total Row count is 4173 and page size is 50
    And the AR rest response data of allocation includes
      |rn|stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1|1606|1|0001|31|2135|300|KMCUST055|70200|2019-05-31|CNY|150629|HK|IBD16CN|107373.07971|87000|2014-09-30|107373.07971|
      |2|1627|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150650|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |3|1720|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150743|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |4|1738|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150761|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |5|1607|1|0001|31|2136|202|KMCUST056|70100|2020-01-28|CNY|150630|HK|IBD16CN|107249.662377|86900|2015-05-30|107249.662377|
      |6|1919|1|0001|31|2153|201|KMCUST052|145100|2020-01-28|CNY|150942|HK|IBD16CN|101078.795727|81900|2015-05-30|101078.795727|
      |7|1920|1|0001|31|2153|201|KMCUST052|153200|2021-05-02|CNY|150943|HK|IBD16CN|98733.8664|80000|2016-09-01|98733.8664|
      |49|1533|1|0001|12|260|202|KMCUST054|77500|2019-01-13|CNY|150556|HK|IBD16CN|212030.978094|94300|2014-05-15|116382.545019|
      |50|1927|1|0001|31|2153|201|KMCUST052|209900|2019-01-13|CNY|150950|HK|IBD16CN|82319.361111|66700|2014-05-15|82319.361111|

#pass
  @AR-5773 @Allocation
  Scenario: AR-4594-GV-02-14 invalid size number (size == -1)
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params         |value                   |
      |product        |HKMA                    |
      |ApiType        |allocationReportContent |
      |formInstanceId |430398                  |
      |size           |-1                      |
    Then I get a response from AR of 200
    Then I see total Row count is 4173 and page size is 50
    And the AR rest response data of allocation includes
      |rn|stbimpindex|stbinstance|stbgroup|hkglc1|hkglc2|hkinst|qa_customerID|qa_amountCurrency|hkmatdate|hkccy|stbdrillRef|hkbranchctry|stbitem|s_Amount|qa_AccrualAmount|qa_MaturityDate|hkaccamnt|
      |1|1606|1|0001|31|2135|300|KMCUST055|70200|2019-05-31|CNY|150629|HK|IBD16CN|107373.07971|87000|2014-09-30|107373.07971|
      |2|1627|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150650|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |3|1720|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150743|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |4|1738|1|0001|31|2135|300|KMCUST055|68100|2019-05-31|CNY|150761|HK|IBD16CN|104781.315717|84900|2014-09-30|104781.315717|
      |5|1607|1|0001|31|2136|202|KMCUST056|70100|2020-01-28|CNY|150630|HK|IBD16CN|107249.662377|86900|2015-05-30|107249.662377|
      |6|1919|1|0001|31|2153|201|KMCUST052|145100|2020-01-28|CNY|150942|HK|IBD16CN|101078.795727|81900|2015-05-30|101078.795727|
      |7|1920|1|0001|31|2153|201|KMCUST052|153200|2021-05-02|CNY|150943|HK|IBD16CN|98733.8664|80000|2016-09-01|98733.8664|
      |49|1533|1|0001|12|260|202|KMCUST054|77500|2019-01-13|CNY|150556|HK|IBD16CN|212030.978094|94300|2014-05-15|116382.545019|
      |50|1927|1|0001|31|2153|201|KMCUST052|209900|2019-01-13|CNY|150950|HK|IBD16CN|82319.361111|66700|2014-05-15|82319.361111|


  #pass
  @AR-5774 @Allocation
  Scenario: AR-4594-GV-02-15 Verify failed response when get allocation report by wrong format of date value
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |QA_MaturityDate=01092019|
    Then I get a response from AR of 400
    Then I get error info "inconsistent datatypes: expected DATE got NUMBER"

  #pass
  @AR-5775 @Allocation
  Scenario: AR-4594-GV-02-16 Verify failed response when get allocation report by wrong operator >=
    Given a user is authenticated by "admin" and "password"
    When When I make a Rest call of reports api on condition
      |params          |value                   |
      |product         |HKMA                    |
      |ApiType         |allocationReportContent |
      |formInstanceId  |430398                  |
      |search          |HKACCAMNT>=20000        |
    Then I get a response from AR of 400
    Then I get error info "HKACCAMNT>=20000 is not legal"