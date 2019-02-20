@AnalysisModule @AnalysisModule-Grid
Feature: Analysis Module Grid

  AR-2451 Sort and filter on the Grid view -
  As a User with appropriate privileges,
  I want to filter and sort on the Grid view

  AR-2596 Trend Analysis -
  As a User with appropriate privileges,
  I want to create Trend Analysis for various periods
  so that I can see the difference in values for selected periods

  AR-2448 Variance Analysis -
  As a user with appropriate privileges,
  I want to be able to view a grid of variance data for the Returns I have selected
  So that I can see the difference in values between periods

  Background:
    Given a user is logged in with username "admin" and password "password"
    And I click Analysis Module button from the header

  @AR-2944
  @Filter
  Scenario: Filter by Cell Reference & Difference column
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    When I create a Variance Analysis for previous reference date 28/02/2017
    And I filter the "Cell Description" column for values that "Contains" "Gold"
    And I filter the "Difference" column for values "Greater than" "411"
    Then I should see only 3 rows
    And the grid should contain the following cells and variance difference:
      |                   | PMG3A                                                                              | PMG1 (Subtotal)                                             | PMG3 (Subtotal)                                                         |
      | description       | FormPM-Metalowner_Metalowner_ClientBalances_OtherUKBanks_Total_Finetroyounces_Gold | FormPM-Metalowner_Metalowner_Total_Finetroyounces_Gold      | FormPM-Metalowner_Metalowner_ClientBalances_Total_Finetroyounces_Gold   |
      | 28/02/2017        | 463,556.00                                                                         | 488,556.00                                                  | 488,556.00                                                              |
      | difference        | 436,444.00                                                                         | 411,444.00                                                  | 411,444.00                                                              |
      | percentDifference | 94.15%                                                                             | 84.22%                                                      | 84.22%                                                                  |
      | 31/03/2017        | 900,000.00                                                                         | 900,000.00                                                  | 900,000.00                                                              |

  @AR-2945
  @Pipeline @Sort
  Scenario: I can sort a difference column
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 31/12/2015
    When I create a Variance Analysis for previous reference date 30/09/2015
    And I filter the "Difference" column for values "Greater than" "30"
    And I sort the "Previous Period" column by descending order
    Then the grid rows should have the following:
      | CAOFR360            |
      | CAOFR285            |
      | CAOFR340 (Subtotal) |

  @AR-2835
  @Pipeline @Trend
  Scenario: Create Trend Analysis for a Date Range
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "ER" and reference date 31/10/2017
    When I create a Date Range Trend Analysis for the previous reference date 10/11/2014
    Then the grid should contain the following cells and trend differences:
      |                   | ERB39 (Subtotal)        | ERB22 (Subtotal)                                                                                 | ERB55A3 (Subtotal)                                                              |
      | description       | DEPOSITS_Total_Interest | FormER-Deposits_PrivateNonFinancialCorporations_TimeFixedOriginalMaturity-1YrTo2Yrs_InterestFlow | FormER-LoansAndAdvances_IndividualsAndIndividualTrusts_OfWhichBRT_InterestFlow  |
      | 10/11/2014        | 12,347.00               | 0.00                                                                                             |                                                                                 |
      | difference        | (12,313.00)             | 7.00                                                                                             |                                                                                 |
      | percentDifference | (99.72%)                | Division By Zero                                                                                 |                                                                                 |
      | 31/08/2017        | 34.00                   | 7.00                                                                                             | 8.00                                                                            |
      | difference        | 1.00                    | 0                                                                                                | 0                                                                               |
      | percentDifference | 2.94%                   | 0.00%                                                                                            | 0.00%                                                                           |
      | 31/10/2017        | 35.00                   | 7.00                                                                                             | 8.00                                                                            |

  @AR-2837
  @Pipeline @Trend
  Scenario:  Create Trend Analysis for an Ad-Hoc selection
    Given Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 31/12/2015
    When I create an Ad-hoc Trend Analysis for the previous reference dates 30/09/2015, 31/07/2015
    Then the grid should contain the following cells and trend differences:
      |                   | CAOFR030 (Subtotal)                          |
      | description       | Capital instruments eligible as CET1 Capital |
      | 31/07/2015        | (3,828.00)                                   |
      | difference        | 50.00                                        |
      | percentDifference | (1.31%)                                      |
      | 30/09/2015        | (3,778.00)                                   |
      | difference        | 0.00                                         |
      | percentDifference | 0.00%                                        |
      | 31/12/2015        | (3,778.00)                                   |

  @AR-2838
  @Pipeline @Trend
  Scenario: Create Trend Analysis for a Number of Periods
    Given Analysis Module's selected return details are regulator "MFSD", entity "MSFD300", return "BE" and reference date 30/06/2017
    When I create a Trend Analysis for the last 5 periods
    Then the grid should contain the following cells and trend differences:
      |                   | BEE26EC (Subtotal)                                |
      | description       | FormBE-Assets_UKBills_OtherUKResidents_Total_Euro |
      | 31/01/2017        | 0.00                                              |
      | difference        | 0.00                                              |
      | percentDifference | 0.00%                                             |
      | 28/02/2017        | 0.00                                              |
      | difference        | 0.00                                              |
      | percentDifference | 0.00%                                             |
      | 31/03/2017        | 0.00                                              |
      | difference        | 0.00                                              |
      | percentDifference | 0.00%                                             |
      | 30/04/2017        | 0.00                                              |
      | difference        | 0.00                                              |
      | percentDifference | 0.00%                                             |
      | 31/05/2017        | 0.00                                              |
      | difference        | 0.00                                              |
      | percentDifference | 0.00%                                             |
      | 30/06/2017        | 0.00                                              |

  @AR-2680
  @Pipeline @Variance
  Scenario: Variance Analysis Grid with multiple values
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 31/03/2017
    When I create a Variance Analysis for previous reference date 28/02/2017
    Then the grid should contain the following cells and variance difference:
      |                   | PMG3AA                                                                                              | PMG3B                                                                            | PMG3C                                                                         |
      | description       | FormPM-Metalowner_Metalowner_ClientBalances_OtherUKBanks_OtherReportingClearers_Finetroyounces_Gold | FormPM-Metalowner_Metalowner_ClientBalances_OtherUKResidents_Finetroyounces_Gold | FormPM-Metalowner_Metalowner_ClientBalances_Non-residents_Finetroyounces_Gold |
      | 28/02/2017        | 1,000.00                                                                                            | 25,000.00                                                                        | 0.00                                                                          |
      | difference        | (100.00)                                                                                            | (25,000.00)                                                                      | 0.00                                                                          |
      | percentDifference | (10.00%)                                                                                            | (100.00%)                                                                        | 0.00%                                                                         |
      | 31/03/2017        | 900.00                                                                                              | 0.00                                                                             | 0.00                                                                          |

  @AR-2682
  @Variance
  Scenario: Variance Analysis with a value is 0 in previous period of the Return
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "PM" and reference date 28/02/2017
    When I create a Variance Analysis for previous reference date 31/01/2017
    Then the grid should contain the following cells and variance difference:
      |                   | PMG3B                                                                              |
      | description       | FormPM-Metalowner_Metalowner_ClientBalances_OtherUKResidents_Finetroyounces_Gold   |
      | 31/01/2017        | 0.00                                                                               |
      | difference        | 25,000.00                                                                          |
      | percentDifference | Division by Zero                                                                   |
      | 28/02/2017        | 25,000.00                                                                          |

  @AR-2684
  @Pipeline @Variance
  Scenario: Variance Analysis with a cell did not exist in the previous period of the Return
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "ER" and reference date 31/08/2017
    When I create a Variance Analysis for previous reference date 10/11/2014
    Then the grid should contain the following cells and variance difference:
      |                   | ERA26A (Subtotal)                                                                |
      | description       | FormER-Deposits_IndividualsAndIndividualTrusts_InterestBearingSight_AverageDaily |
      | 10/11/2014        |                                                                                  |
      | difference        |                                                                                  |
      | percentDifference |                                                                                  |
      | 31/08/2017        | 589,300.00                                                                       |

  @AR-2685
  @Pipeline @Variance
  Scenario: Variance Analysis with a cell is removed in the current period of the Return
    Given Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "ER" and reference date 31/08/2017
    When I create a Variance Analysis for previous reference date 10/11/2014
    Then the grid should contain the following cells and variance difference:
      |                   | ERA26 (Subtotal)                                                                |
      | description       | FormER-Deposits_HouseholdsAndIndividualTrusts_InterestBearingSight_AverageDaily |
      | 10/11/2014        | 123,456.00                                                                      |
      | difference        |                                                                                 |
      | percentDifference |                                                                                 |
      | 31/08/2017        |                                                                                 |