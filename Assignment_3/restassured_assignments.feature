Feature: Rest Assured Assignment

  @Assignment_001
  Scenario Outline: Celsius to Fahrenheit Validation
    Given User gives temperature in Celsius as "<Celsius_temp>"
    When User creates temperature conversion request body
    And User sends post call with url as "<url>"
    Then User sees status code value as "<status_code>"
    And User sees temperature in fahrenheit as "<Fahrenheit_Temp>"

    Examples:
    |Celsius_temp|url|status_code|Fahrenheit_Temp|
    |5|https://www.w3schools.com/xml/tempconvert.asmx|200|41|
    |60|https://www.w3schools.com/xml/tempconvert.asmx|200|140|
    |-5|https://www.w3schools.com/xml/tempconvert.asmx|200|23|
    |35|https://www.w3schools.com/xml/tempconvert.asmx|200|95|
    |100|https://www.w3schools.com/xml/tempconvert.asmx|200|212|
    |1000|https://www.w3schools.com/xml/tempconvert.asmx|200|1832|
    |82.5|https://www.w3schools.com/xml/tempconvert.asmx|200|180.5|
    |63|https://www.w3schools.com/xml/tempconvert.asmx|200|145.4|
    |2|https://www.w3schools.com/xml/tempconvert.asmx|200|35.6|
    |1|https://www.w3schools.com/xml/tempconvert.asmx|200|33.8|

  @Assignment_002
  Scenario Outline: Laptop Details Validation
    Given User gives Laptop Details as "<laptop_name>","<Year>" & "<Price>"
    When User creates Laptop Details request body
    And User sends post call for Laptop Details with url as "<url>"
    Then User can see that status code value is "<status_code>"
    And User sees Year as "<Expected_Year>"
    And User sees Price as "<Expected_Price>"
    And User validates that createdAt is not null

    Examples:
      |laptop_name|Year|Price|url|status_code|Expected_Year|Expected_Price|
      |Apple MacBook Pro 16|2019|1849.99|https://api.restful-api.dev/objects|200|2019|1849.99|

  @Assignment_003
  Scenario Outline: Mobile Details Validation
    Given User enters the "<url>"
    And User hits get the call request
    Then User gets status code as "<status_code>"
    When Response id is "<id>"
    Then Mobile Name is "<mobile_name>"

    Examples:
      |url|status_code|id|mobile_name|
      |https://api.restful-api.dev/objects|200|8|Apple Watch Series 8|
      |https://api.restful-api.dev/objects|200|11|Apple iPad Mini 5th Gen|
      |https://api.restful-api.dev/objects|200|2|Apple iPhone 12 Mini, 256GB, Blue|

  @Assignment_004
  Scenario Outline: Currency Details Validation
    Given User enters the currency URL "<url>"
    And User hits get the call request for viewing currency details
    Then User will be able to view that status code "<status_code>"
    Then User will be able to see that total number of Outcome Types are "<Outcome_Count>"
    And One of the Outcome is "<Outcome>"

    Examples:
      |url|status_code|Outcome_Count|Outcome|
      |https://www.xignite.com/xCurrencies.asmx?wsdl|200|4|SystemError|


