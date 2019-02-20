@AnalysisModule @AnalysisModule-Access
Feature: Analysis Module General User Access - Entry Points - Dashboard/Return

  As a User I want to be able to access Analysis Module from the Agile Reporter
  dashboard; with context sensitive population of selection criteria where
  appropriate, so that I do not have to enter information already available
  to the module.

  Background:
    Given a user is logged in with username "admin" and password "password"

  Scenario: AR-2695 Default selector panel with no values for dashboard entry
    When I click Analysis Module button from the header
    Then I should see a Selector Panel with nothing selected

  @AR-2696
  @Pipeline
  Scenario: For a user not logged in to AR, accessing Analysis Module is redirected to AgileReporter login
    Given I am logged out
    When I navigate to Analysis Module
    Then I am redirected to login page with a timeout message

#  Scenario: AR-2588 Analysis Module throws an error on no data to return - In instance of deleted return
#    Given I am in a deleted return
#    When I access Analysis Module
#    Then I should be presented with an error message
#
#  Scenario: AR-2592 Analysis Module throws an error on no data to return - In instance of deleted return
#    When I access a now deleted return from via direct url access in Analysis Module results
#    Then I am presented with an error message

  @AR-2593
  @Pipeline
  Scenario: Analysis Module Access for variance - from the return - displays prepopulated return instance details
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    When I click the Variance button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 02/09/2017
    And I should see a prepopulated variance previous reporting date of "September 1, 2017"

#    And the grid should contain:
#      | cellReference             | previousPeriod | currentPeriod | difference | percentDifference |
#      | MKIRR321C060 (Subtotal)   | 2,044,134      | 2,044,134     | 0          | 0.00%             |
#    When I select "Swiss Franc" as my Instance
#    And I click the create button
#    Then the grid should contain:
#      | cellReference             | previousPeriod | currentPeriod | difference | percentDifference |
#      | MKIRR321C060 (Subtotal)   | 14,639,772     | 14,639,772    | 0          | 0.00%             |

#  Scenario: AR-2594 Analysis Module Access for trend - from the return
#    Given I am in a return
#    When I access trend analysis from the return instance
#    Then Analysis Module should present my return information on the report based on trend

  @AR-2594
  @Pipeline
  Scenario: Analysis Module Access for trend - from the return - displays prepopulated instance results
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    And I select the return "MKIR" v5 with reference date 02/09/2017
    When I click the Trends button from within a return
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 02/09/2017
    And I should see a prepopulated trends previous reporting date of 31/08/2017
    And I should see a prepopulated instance of "Total"

#    And the grid should contain:
#      | cellReference             | previousPeriod | currentPeriod | difference | percentDifference |
#      | MKIRR321C060 (Subtotal)   | 2,164,399.48   | 2,257,685.31  | 93,285.83  | 4.31%             |
#    When I select "Swiss Franc" as my Instance
#    And I click the create button
#    Then the grid should contain:
#      | cellReference             | previousPeriod | currentPeriod | difference | percentDifference |
#      | MKIRR321C060 (Subtotal)   | 15,698,555.61  | 16,299,285.28 | 600,729.67 | 3.83%             |

#  Scenario: AR-2599 Analysis Module return selection parameters match AgileREPORTER selection parameters - from the return variance
#    Given I am in a return
#    When I click the variance button from the return instance
#    Then My report should contain the same information as AgileREPORTER
#
#  Scenario: AR-2600 Analysis Module return selection parameters match AgileREPORTER selection parameters - from the return trend
#    Given I am in a return
#    When I click the trend button from the return instance
#    Then My report should contain the same information as AgileREPORTER
#
#  Scenario: AR-2601 Analysis Module selection parameters match AgileREPORTER selection parameters - from the dashboard (selecting variance)
#    Given I access Analysis Module from the dashboard and request return information with previous periods submitted
#    When I click the variance button
#    Then My report should contain the same information as AgileREPORTER
#
#  Scenario: AR-2602 Analysis Module selection parameters match AgileREPORTER selection parameters - from the dashboard (selecting trend)
#    Given I access Analysis Module from the dashboard and request return information with previous periods submitted
#    When I click the trends button
#    Then My report should contain the same information as AgileREPORTER
#
#  Scenario: AR-2603 Analysis Module - privilege check on no rights - dashboard
#    Given a user is logged in with username "NoAnalysisAccess" and password "password"
#    When I access Analysis Module from the dashboard
#    Then I should be presented with a denied access message
#
#  Scenario: AR-2606 Analysis Module - privilege check on no rights - return access
#    Given a user is logged in with username "NoAnalysisAccess" and password "password"
#    When I access Analysis Module from the return grid in AR dashboard
#    Then the access for trend or variance should not be available
#
#  Scenario: AR-2607 Analysis Module - privilege check on no rights - return instance access
#    Given a user is logged in with username "NoAnalysisAccess" and password "password"
#    And I enter a return
#    When I access Analysis Module from the return view
#    Then the access for trend or variance should not be available
#
#  Scenario: AR-2608 Analysis Module - privilege check no rights to return - direct AM URL access
#    When I access via direct URL access to a none entity assigned return instance id
#    Then I should be presented with a access denied message
#
#  Scenario: AR-2609 Analysis Module - no previous return periods submitted - return grid access
#    When I access Analysis Module from the return grid in AR dashboard
#    Then the access for trend or variance should not be available
#
#  Scenario: AR-2610 Analysis Module - no previous return periods submitted - return instance access
#    When I access Analysis Module from the return
#    Then the access for trend or variance should not be available in the return instance
#
#  Scenario: AR-2611 Analysis Module - no previous return periods submitted - dashboard access variance
#    Given I access Analysis Module from the dashboard and request return information
#    Then I should have no other periods available to select
#    When I click the trends button
#    And I click the create button
#    Then I should be presented with an error stating no previous returns
#
#  Scenario: AR-2612 Analysis Module - no previous return periods submitted - dashboard access trend
#    Given I access Analysis Module from the dashboard and request return information
#    Then I should have no other periods available to select
#    When I click the trends button
#    And I click the create button
#    Then I should be presented with an error stating no previous returns

  @Pipeline
  Scenario: Analysis Module Access for trends - in the dashboard for a return - displays prepopulated instance results
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    When I click the Trends button on the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 02/09/2017
    And I should see a prepopulated trends previous reporting date of 31/08/2017
    And I should see a prepopulated instance of "Total"

  @Pipeline
  Scenario: Analysis Module Access for variance - in the dashboard for a return - displays prepopulated instance results
    Given I select regulator "ECR", entity "ECR2999", return type "MKIR" on the dashboard
    When I click the Variance button on the return "MKIR" v5 with reference date 02/09/2017 from the dashboard
    Then I should see regulator "ECR", entity "ECR2999", return "MKIR", reporting date 02/09/2017
    And I should see a prepopulated variance previous reporting date of "September 1, 2017"
    And I should see a prepopulated instance of "Total"

  @AR-4112
  Scenario: Access Analysis Module help link
    Given I click Analysis Module button from the header
    When I click on Help link
    Then I can view the help content