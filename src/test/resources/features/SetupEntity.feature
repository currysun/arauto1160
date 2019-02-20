@AgileREPORTER @Smoke @AR-Admin
Feature: As a developer I want to see if AgileREPORTER is installed and running correctly

  Scenario: I can login
    When a user is logged in with username "admin" and password "password"
    Then I see the dashboard

  Scenario: I setup entities
    Given a user is logged in with username "admin" and password "password"
    When I am on the Entity setup page
    Then I have created an entity of name "0002", description "0002", codes of "0002"
    Then I have created an entity of name "0005", description "0005", codes of "0005"
    Then I have created an entity of name "0031", description "0031", codes of "0031"
    Then I have created an entity of name "0040", description "0040", codes of "0040"
    Then I have created an entity of name "1006_BHCP", description "1006_BHCP", codes of "1006_BHCP"
    Then I have created an entity of name "1008_BHCP", description "1008_BHCP", codes of "1008_BHCP"
    Then I have created an entity of name "1010- American Express Bank, FBS", description "1010- American Express Bank, FBS", codes of "1010"
    Then I have created an entity of name "1012- American Express Bank, FBS", description "1012- American Express Bank, FBS", codes of "1012"
    Then I have created an entity of name "1020_BHCP", description "1020_BHCP", codes of "1020_BHCP"
    Then I have created an entity of name "1027_BHCP", description "1027_BHCP", codes of "1027_BHCP"
    Then I have created an entity of name "1030_BHCP", description "1030_BHCP", codes of "1030_BHCP"
    Then I have created an entity of name "1034_BHCP", description "1034_BHCP", codes of "1034_BHCP"
    Then I have created an entity of name "1035_BHCP", description "1035_BHCP", codes of "1035_BHCP"
    Then I have created an entity of name "1071- American Express International, Inc. - Branch - Philippines", description "1071- American Express International, Inc. - Branch - Philippines", codes of "1071"
    Then I have created an entity of name "1075_BHCP", description "1075_BHCP", codes of "1075_BHCP"
    Then I have created an entity of name "1076- AE_EUPORE_LTD_BHC_C_BHCP", description "1076- AE_EUPORE_LTD_BHC_C_BHCP", codes of "1076_BHCP"
    Then I have created an entity of name "1078- AE_EUPORE_LTD_BHC_C_BH_CP", description "1078- AE_EUPORE_LTD_BHC_C_BH_CP", codes of "1078"

