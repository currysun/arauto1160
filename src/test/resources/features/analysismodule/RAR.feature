@AnalysisModule @AnalysisModule-RAR
Feature: AR-3362 & AR-3370 Viewing RAR from within Analysis Module, CellAudit - Adjustments
  AR-4053 Cell Audit - Comments
  AR-3370 Viewing of comments under the comments levelpanel of RAR

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header

  @AR-3862
  Scenario: Viewing RAR with in Analysis Module for a return
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "MKIR" and reference date 10/09/2017
    And I create a Variance Analysis for previous reference date 09/09/2017
    When I Open the RAR in analysis module
    Then I should see RAR with following details
      | Title          | Return Analysis Report for COREP MKR SA TDI |
      | Regulator      | European Common Reporting                   |
      | Entity         | ECR2999                                     |
      | Reporting Date | 10/09/2017                                  |
      | Return Edition | 5                                           |
      | Maker          | ADMIN                                       |
      | Approver(s)    | rar                                         |
      | Return Status  | Not Submitted                               |

  @AR-3863
  Scenario: Viewing of comments under the Return comments panel on RAR
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "AD" and reference date 31/07/2017
    And I create a Variance Analysis for previous reference date 30/04/2017
    When I add a comment "RAR Comment" and include in Return Analysis Report
    And I Open the RAR in analysis module
    And I open the Return Comments panel
    Then the comments are displayed with the following details
      | Type         | Variance                |
      | Period Range | 30/04/2017 - 31/07/2017 |
      | Author       | ADMIN                   |
      | Comment      | RAR Comment:            |
      | Attachment   |                         |

  @AR-4127
  Scenario: Cell Audit for Adjustment & Comments on RAR
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 30/04/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I add a Cell comment "RAR Comment" to cell "PMG1 (Subtotal)" and include in Return Analysis Report
    When I Open the RAR in analysis module
    And I open the Cell level comments panel
    Then the cell level comments are displayed with following details
      |                    | PMG1 (Subtotal)                                        |
      | description        | FormPM-Metalowner_Metalowner_Total_Finetroyounces_Gold |
      | Instance           |                                                        |
      | Type               | TRENDS                                                 |
      | Prior Date         |                                                        |
      | Prior Value        |                                                        |
      | Current Date       | 30/04/2017                                             |
      | Current Value      | 4,000.00                                               |
      | difference         |                                                        |
      | percent difference |                                                        |
      | Comment            | RAR Comment                                            |
      | context            | Show Details                                           |
      | Author             | ADMIN                                                  |
      | Attachment         |                                                        |
    And When I click on Show Details
    Then the trend analysis details grid contains
      | DATE        | 31/03/2017 | 28/02/2017 | 31/01/2017 |
      | VALUE       | 900,000.00 | 488,556.00 | 461,556.00 |
      | DIFFERENCE  | 411,444.00 | 27,000.00  |            |
      | %DIFFERENCE | 84.22%     | ,          |            |
    And I filter the grid by "ADJUSTMENT"
    Then the cell level comments are displayed with following details
      |                    | PMG3C                                                                         |
      | description        | FormPM-Metalowner_Metalowner_ClientBalances_Non-residents_Finetroyounces_Gold |
      | Instance           |                                                                               |
      | Type               | ADJUSTMENT                                                                    |
      | Prior Date         |                                                                               |
      | Prior Value        | 1,000.00                                                                      |
      | Current Date       | 30/04/2017                                                                    |
      | Current Value      | 2,000.00                                                                      |
      | difference         | 1,000.00                                                                      |
      | percent difference |                                                                               |
      | Comment            | direct cell edit                                                              |
      | context            |                                                                               |
      | Author             | ADMIN                                                                         |
      | Attachment         |                                                                               |

  @AR-4438
  Scenario: As a user I should be able to export a copy of RAR to PDF
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "MKIR" and reference date 10/09/2017
    When I create a Variance Analysis for previous reference date 09/09/2017
    When I Open the RAR in analysis module
    And I export the RAR
    Then the exported RAR filename should match the expected pattern
    Then the exported PDF file should not match the expected file "Notexpected.pdf"
    Then the exported PDF file should match the expected file "test_RAR.pdf"