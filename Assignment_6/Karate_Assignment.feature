Feature: Karate Assignment

  Scenario Outline: Reading Values from XL File and Printing at Runtime
    * def javafileobject = Java.type('support.Karate_Assignment')
    * def Pet_Name = javafileobject.get_pet_name('<Testcase>')
    * def Pet_ID = javafileobject.get_pet_id('<Testcase>')
    Then print Testcase,Pet_Name,Pet_ID
    Examples:
      |Testcase|
      |TC001|
      |TC002|
      |TC003|
      |TC004|
      |TC005|
      |TC006|
      |TC007|
      |TC008|
      |TC009|
      |TC010|
      |TC011|
      |TC012|
      |TC013|
      |TC014|
      |TC015|
      |TC016|
      |TC017|
      |TC018|
      |TC019|
      |TC020|
      |TC021|
      |TC022|
      |TC023|
      |TC024|
      |TC025|
      |TC026|
      |TC027|
      |TC028|
      |TC029|
      |TC030|

  Scenario Outline: Posting Pet Details and Validating Response
    Given url 'https://petstore.swagger.io/v2/pet'
    * def javafileobject = Java.type('support.Karate_Assignment')
    * def Pet_Name = javafileobject.get_pet_name('<Testcase>')
    * def Pet_ID = javafileobject.get_pet_id('<Testcase>')
    * def requestjsonBody = javafileobject.post_pet_details(Pet_ID,Pet_Name)
    When request requestjsonBody
    And header Content-type = 'application/json; charset=utf-8'
    And method post
    Then status 200
    And print 'Response is:' , response
    And print 'Expected Pet_ID is:',Pet_ID,'Actual Pet_ID is:',response.id
    And print 'Expected Pet_Name is:',Pet_Name,'Actual Pet_ID is:',response.name
    And match response.id == Pet_ID
    And match response.name == Pet_Name
    And match response.status == "available"
    Examples:
      |Testcase|
      |TC001|
      |TC002|
      |TC003|
      |TC004|
      |TC005|
      |TC006|
      |TC007|
      |TC008|
      |TC009|
      |TC010|
      |TC011|
      |TC012|
      |TC013|
      |TC014|
      |TC015|
      |TC016|
      |TC017|
      |TC018|
      |TC019|
      |TC020|
      |TC021|
      |TC022|
      |TC023|
      |TC024|
      |TC025|
      |TC026|
      |TC027|
      |TC028|
      |TC029|
      |TC030|

Scenario Outline: Getting Pet Details and Validating Response
  * def javafileobject = Java.type('support.Karate_Assignment')
  * def Pet_Name = javafileobject.get_pet_name('<Testcase>')
  * def Pet_ID = javafileobject.get_pet_id('<Testcase>')
  Given url 'https://petstore.swagger.io/v2/pet/'+Pet_ID
  When method Get
  Then status 200
  And print response
  And print 'Expected Pet_ID is:',Pet_ID,'Actual Pet_ID is:',response.id
  And print 'Expected Pet_Name is:',Pet_Name,'Actual Pet_ID is:',response.name
  And match response.id == Pet_ID
  And match response.name == Pet_Name
  And match response.status == "available"
   Examples:
     |Testcase|
     |TC001|
     |TC002|
     |TC003|
     |TC004|
     |TC005|
     |TC006|
     |TC007|
     |TC008|
     |TC009|
     |TC010|
     |TC011|
     |TC012|
     |TC013|
     |TC014|
     |TC015|
     |TC016|
     |TC017|
     |TC018|
     |TC019|
     |TC020|
     |TC021|
     |TC022|
     |TC023|
     |TC024|
     |TC025|
     |TC026|
     |TC027|
     |TC028|
     |TC029|
     |TC030|

Scenario Outline: Deleting Pet Details and Validating Response
  * def javafileobject = Java.type('support.Karate_Assignment')
  * def Pet_Name = javafileobject.get_pet_name('<Testcase>')
  * def Pet_ID = javafileobject.get_pet_id('<Testcase>')
  Given url 'https://petstore.swagger.io/v2/pet/'+Pet_ID
    When method delete
    Then status 200
    And print response
    And match response.message == Pet_ID + ''

  Examples:
    |Testcase|
    |TC001|
    |TC002|
    |TC003|
    |TC004|
    |TC005|
    |TC006|
    |TC007|
    |TC008|
    |TC009|
    |TC010|
    |TC011|
    |TC012|
    |TC013|
    |TC014|
    |TC015|
    |TC016|
    |TC017|
    |TC018|
    |TC019|
    |TC020|
    |TC021|
    |TC022|
    |TC023|
    |TC024|
    |TC025|
    |TC026|
    |TC027|
    |TC028|
    |TC029|
    |TC030|

