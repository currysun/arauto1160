@AnalysisModule @AnalysisModule-DrillDown
Feature: Analysis Module Drilldowns - Derived Cells/Allocation Cells

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header

  @AR-2719
  @Pipeline
  Scenario: Drilldown derived cells access - Variance
    And Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    And I create a Variance Analysis for previous reference date 28/02/2017
    When I drilldown into the derived cell "PMG1 (Subtotal)"
    And the grid should contain the following cells and variance difference:
      |                   | PMG2  | PMG3 (Subtotal)                                                       |
      | description       | Gold  | FormPM-Metalowner_Metalowner_ClientBalances_Total_Finetroyounces_Gold |
      | 28/02/2017        | 0.00  | 488,556.00                                                            |
      | difference        | 0.00  | 411,444.00                                                            |
      | percentDifference | 0.00% | 84.22%                                                                |
      | 31/03/2017        | 0.00  | 900,000.00                                                            |

  @AR-2720
  @Pipeline
  Scenario: Drilldown derived cells access - Trend
    And Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    And I create a Date Range Trend Analysis for the previous reference date 31/01/2017
    When I drilldown into the derived cell "PMG1 (Subtotal)"
    And I drilldown further into the derived cell "PMG3 (Subtotal)"
    Then the grid should contain the following cells and trend differences:
      |                   | PMG3A      | PMG3B            | PMG3C |
      | description       | Gold       | Gold             | Gold  |
      | 31/01/2017        | 461,556.00 | 0.00             | 0.00  |
      | difference        | 2,000.00   | 25,000.00        | 0.00  |
      | percentDifference | 0.43%      | Division by Zero | 0.00% |
      | 28/02/2017        | 463,556.00 | 25,000.00        | 0.00  |

  @AR-3738
  Scenario: Allocations cell with Adjustments drilldown
    Given Analysis Module's selected return details are regulator "HKMA", entity "FCR", return "S" and reference date 31/12/2019
    And I create a Variance Analysis for previous reference date 31/12/2018
    When I drilldown into the Allocation cell "SP5C196CO"
    Then the grid should contain the following Allocation cells and variance difference:
      |                | 32150          | 32250        | 310373       | Changed      | New        | Dropped      |
      | 31/12/2018     | 149,999,400.00 | 6,464,515.00 | 1,723,695.00 | 100.00       |            | (397,374.00) |
      | 31/12/2019     | 149,999,400.00 | 7,978,332.57 |              | (316,986.00) | 316,986.00 |              |
      | DIFFERENCE     | 0.00           | 1,513,817.57 |              | (317,086.00) |            |              |
      | TYPE OF CHANGE |                | CHANGED      | DROPPED      | CHANGED      | NEW        | DROPPED      |
    And the Sum of allocations values with adjustments should be:
      |                | SP5C196CO |
      | 31/12/2018     | 100.00    |
      | 31/12/2019     | 100.00    |
      | DIFFERENCE     | 0.00      |
      | TYPE OF CHANGE |           |