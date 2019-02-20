@AnalysisModule @AnalysisModule-SelectionPanel
Feature: Analysis Module - Selection Panel - Comparison Returns
  As a User I want to be able to choose the selection criteria that defines a set historic Return
  against which to compare the Return I am interested in analysing Trend or Variance
  As a user I want to be able to select the reference periods I wish to use,
  for comparison against the Return I wish to analyse

  Background:
    Given a user is logged in with username "admin" and password "password"

  @AR-2775
  Scenario: Regulator field - Appropriate options
    Given I click Analysis Module button from the header
    And I should see listed all regulators for which I have Access Rights which are:
      | Australian Prudential Regulation Authority |
      | European Common Reporting                  |
      | Hong Kong Monetary Authority               |
      | Monetary and Financial Stat Division       |
      | My First Regulator                         |
      | Reserve Bank of India                      |
      | US FED Reserve                             |

  @AR-2776
  Scenario: Entity field - Appropriate options
    Given I click Analysis Module button from the header
    When I select the "European Common Reporting" Regulator in the Selection Panel
    And I should see listed all entities for which I have Access Rights which are:
      | 4Regs1000 |
      | A25       |
      | ECR2999   |

  @AR-2777
  Scenario: Return field - Appropriate options
    Given I click Analysis Module button from the header
    And I select the "European Common Reporting" Regulator in the Selection Panel
    When I select the "4Regs1000" Entity in the Selection Panel
    And I should see listed all returns for which I have Access Rights which are:
      | C69 |
      | CAR |

  @AR-2778
  Scenario: Reporting Date field - Appropriate options
    Given I click Analysis Module button from the header
    And I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "4Regs1000" Entity in the Selection Panel
    When I select the "C69" Return in the Selection Panel
    Then I should see listed all reporting date/s for which I have Access Rights, which are:
      | November 30, 2017 |

  @AR-2779
  @Pipeline
  Scenario: Check multi instance form displays in analysis module and can be updated against grid view
    Given I click Analysis Module button from the header
    When I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "ECR2999" Entity in the Selection Panel
    And I select the "MKIR" Return in the Selection Panel
    Then I should see listed all Instances for which I have Access Rights, Which Are:
      | Total          |
      | Euro           |
      | Pound Sterling |
      | Swiss Franc    |
      | Yen            |
    When I select "Swiss Franc" as my Instance
    And I select the "September 13, 2017" Reporting Date in the Selection Panel
    And I select the "All Cells" cellgroup and create
    Then the grid should contain the following cells and variance difference:
      |                   | MKIRR321C060                         |
      | description       | Specific risk; Own funds requirement |
      | 12/09/2017        | 15,698,555,609.00                    |
      | difference        | 600,729,672.00                       |
      | percentDifference | 3.83%                                |
      | 13/09/2017        | 16,299,285,281.00                    |

  @AR-2395
  Scenario: Check Trends within a return displays prepopulated results for total and can be updated to new instance and previous reporting date correct
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    When I click the Trends button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 02/09/2017
    And I should see a prepopulated trends previous reporting date of 31/08/2017
    And I should see a prepopulated instance of "Total"
    And the grid should contain the following cells and trend differences:
      |                   | MKIRR321C060                         |
      | description       | Specific risk; Own funds requirement |
      | 31/08/2017        | 1,896,060,000.00                     |
      | difference        | 148,074,000.00                       |
      | percentDifference | 7.81%                                |
      | 1/09/2017         | 2,044,134,000.00                     |
      | difference        | 0.00                                 |
      | percentDifference | 1,280.49%                            |
      | 2/09/2017         | 28,219,068,000.00                    |

  @AR-2723 @AR-2395
  Scenario: Check Trends from within a return displays prepopulated results and previous reporting date is correct
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 12/09/2017
    When I click the Trends button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 12/09/2017
    And I should see a prepopulated trends previous reporting date of 06/09/2017
    And I should see a prepopulated instance of "Total"
    And I click reset

  @AR-2723 @AR-2395
  Scenario: Check Trends from within a return for no historical periods displays prepopulated results and warning message for previous date
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 01/09/2017
    When I click the Trends button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 01/09/2017
    Then I should see a message informing me that trends needs at least two previous returns
    And The instance dropdown is hidden
    And I click reset

  @AR-2723 @AR-2395
  Scenario: Check Variance from within a return displays prepopulated results in AM
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 03/09/2017
    When I click the Variance button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 03/09/2017
    And I should see a prepopulated variance previous reporting date of "September 2, 2017"
    Then the grid should contain the following cells and variance difference:
      |                   | MKIRR321C060                         |
      | description       | Specific risk; Own funds requirement |
      | 2/09/2017         | 28,219,068,000.00                    |
      | difference        | (26,162,572,520.00)                  |
      | percentDifference | (92.71%)                             |
      | 3/09/2017         | 2,056,495,480.00                     |
    When I select "Swiss Franc" as my Instance
    And I click the create button
    Then the grid should contain the following cells and variance difference:
      |                   | MKIRR321C060                         |
      | description       | Specific risk; Own funds requirement |
      | 2/09/2017         | 14,639,772,000.00                    |
      | difference        | 57,477,525.00                        |
      | percentDifference | 0.39%                                |
      | 3/09/2017         | 14,697,249,525.00                    |

  @AR-2723 @AR-2395
  @Pipeline
  Scenario: Check Variance from within a return displays prepopulated results in AM and previous reporting date warning
    Given I select regulator "ECR", entity "ECR2999", return type "C69 v1" on the dashboard
    And I select the return "C69" v1 with reference date 31/12/2016
    When I click the Variance button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "C69", reporting date 31/12/2016
    Then I should see a message informing me that variance needs a previous return
    And The instance dropdown is hidden

  @AR-2620
  Scenario: Analysis Module variance displays an error message for a new return
    Given I click Analysis Module button from the header
    When I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "4Regs1000" Entity in the Selection Panel
    And I select the "C69" Return in the Selection Panel
    And I select the "November 30, 2017" Reporting Date in the Selection Panel
    Then I should see a message informing me that variance needs a previous return

  @AR-2622
  Scenario: Analysis Module display an error message when there are future, but no historic periods
    Given I click Analysis Module button from the header
    When I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "ECR2999" Entity in the Selection Panel
    And I select the "NSF" Return in the Selection Panel
    And I select the "September 30, 2016" Reporting Date in the Selection Panel
    Then I should see a message informing me that variance needs a previous return

  @AR-2623
  Scenario: Analysis Module displays the correct prior return period when choosing a reference date that is not current period
    Given I click Analysis Module button from the header
    And Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 30/09/2015
    And I click the create button
    Then I should see a list of only the historic return periods of the selected return, which are:
      | August 31, 2015 |
      | July 31, 2015   |

  @AR-2948
  Scenario: Analysis Module -Trends- Radio Buttons - Appropriate options
    Given I click Analysis Module button from the header
    And Analysis Module's selected return details are regulator "ECR", entity "ECR2999", return "CAR" and reference date 31/12/2015
    When I click the trends button
    Then I should see listed all radio button options:
      | Date Range          |
      | Number of Periods   |
      | All Available Dates |
    And the Date Range option is set as selected by default
    And I should see a prepopulated trends previous reporting date of 31/07/2015

  @AR-2645
  Scenario: In trend analysis, when I Select Number of periods option, the default is 6 periods which i can update.
    Given I click Analysis Module button from the header
    And I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "ECR2999" Entity in the Selection Panel
    And I select the "MKIR" Return in the Selection Panel
    And I click the trends button
    And I select the Number of Periods Radio Button
    Then I see 6 periods as default
    And I update the number of periods to "8"

  @AR-2922
  Scenario: In trend analysis, when I Select the Number of periods option, I get validation errors when selecting <2 or >12
    Given I click Analysis Module button from the header
    And I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "ECR2999" Entity in the Selection Panel
    And I select the "MKIR" Return in the Selection Panel
    And I click the trends button
    And I select the Number of Periods Radio Button
    And I update the number of periods to "13"
    Then I get a too many periods selected error
    And I update the number of periods to "1"
    Then I get a too few periods selected error

  @AR-2646 @AR-2650
  Scenario: When I select Ad hoc selection option I should be able to do a multi select of historic returns
    Given I click Analysis Module button from the header
    When I select the "European Common Reporting" Regulator in the Selection Panel
    And I select the "ECR2999" Entity in the Selection Panel
    And I select the "MKIR" Return in the Selection Panel
    And I click the trends button
    When I select the Ad Hoc Selection option
    Then I should see listed all Ad Hoc Selection Previous Reporting Dates, which are:
      | description        | checked |
      | September 12, 2017 | True    |
      | September 11, 2017 | True    |
      | September 10, 2017 | False   |
      | September 9, 2017  | False   |
      | September 8, 2017  | False   |
      | September 7, 2017  | False   |
      | September 6, 2017  | False   |
      | September 5, 2017  | False   |
      | September 4, 2017  | False   |
      | September 3, 2017  | False   |
      | September 2, 2017  | False   |
      | September 1, 2017  | False   |
      | August 31, 2017    | False   |
    When I click on the adHoc selection previous reporting dates:
      | September 11, 2017 |
    Then I get too few adHoc dates selected error
    And I click on the adHoc selection previous reporting dates:
      | August 31, 2017    |
      | September 11, 2017 |
      | September 10, 2017 |
      | September 9, 2017  |
      | September 8, 2017  |
      | September 7, 2017  |
      | September 6, 2017  |
      | September 5, 2017  |
      | September 4, 2017  |
      | September 3, 2017  |
      | September 2, 2017  |
      | September 1, 2017  |
    Then I get too many adHoc dates selected error

  @AR-2643
  Scenario: Date Range option is selected by default for Trend Analysis
    Given I click Analysis Module button from the header
    And Analysis Module's selected return details are regulator "MFSD", entity "4Regs1000", return "ER" and reference date 31/10/2017
    When I click the trends button
    Then the Date Range option is set as selected by default

  @AR-2647
  @Pipeline
  Scenario: No historic returns available
    Given I click Analysis Module button from the header
    And I select the "Monetary and Financial Stat Division" Regulator in the Selection Panel
    And I select the "LRM100" Entity in the Selection Panel
    When I select the "BT" Return in the Selection Panel
    Then the following no previous returns are available message is displayed

  @AR-2648
  Scenario: When there is only one historic return then a message is shown informing that there is only one retun
    Given I click Analysis Module button from the header
    And Analysis Module's selected return details are regulator "FED", entity "4Regs1000", return "FC1" and reference date 30/11/2017
    When I click the trends button
    Then I should see a message informing me that only one historic form exists and inviting me to switch to Variance View