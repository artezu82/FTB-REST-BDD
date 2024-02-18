Feature: Testing FTB REST API AIRCRAFTS resource
  Clients should be able to READ/CREATE/UPDATE/DELETE an aircraft record.

  Scenario: Get specific aircraft with null seats data by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1157
    Then aircraft data to be manufacturer='Wierdo' and model='Strange Thing' and null seats



  Scenario: Create an aircraft with null seats
  Given FTB is up and running and the tests are configured
  When client tries to create an Aircraft having manufacturer='Wierdo' and model='Strange Thing' and null seats
  Then returned aircraft data to be manufacturer='Wierdo' and model='Strange Thing' and null seats
  When client gets details of just created Aircraft
  Then aircraft data to be manufacturer='Wierdo' and model='Strange Thing' and null seats