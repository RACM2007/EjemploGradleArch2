Feature: Functional tests case creation ms-customers-support

  Background:
    * def varEnv = defEnv
    And print varEnv
    * def apiBaseUrl = baseUrlApim
    * header Accept = 'application/json'
    * def resultToken = karate.callSingle('obtener-token.feature')
    * def subscriptionKey = customerSupportBciSubscription


  @name=create-case
  Scenario: create case in digital ecosystem
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * header karate-name = "create-case"
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/createCaseOKData.json")
    Given url path + '/cases'
    And request data
    When method POST
    Then status 201
    And print response
    And assert response != null

  @name=create-case-invalid-params
  Scenario: create case with input errors
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/createCaseKOData.json")
    Given url path + '/cases'
    And request data
    When method POST
    Then status 400
    And assert response.message == 'Error argumentos no validos'

