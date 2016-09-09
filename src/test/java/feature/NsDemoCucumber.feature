Feature: calculate a journey

  Scenario: calculate the route you input
    Given user goes to the NS Site
    When user clicks the Accept cookies message
    And user is able to enter his from/to destination
    And user is on the NS site
    And user inputs "Rotterdam Centraal" in the field
    And user inputs "Amsterdam Centraal" in the field
    And user clicks on "Plannen" button
    Then in the overview it should display "€ 15,10"
