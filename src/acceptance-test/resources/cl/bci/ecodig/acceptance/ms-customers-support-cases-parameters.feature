Feature: Functional tests parameters list ms-customers-support

  Background:
    * def varEnv = defEnv
    And print varEnv
    * def apiBaseUrl = baseUrlApim
    * header Accept = 'application/json'
    * def resultToken = karate.callSingle('obtener-token.feature')
    * def subscriptionKey = customerSupportBciSubscription


  @name=get-parameters-list
  Scenario: Get available parameters list
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * header karate-name = "get-parameters-list"
    * def path = apimBaseUrlMs
    Given url path + '/cases/parameters'
    When method GET
    Then status 200
    And print response
    And assert response != null

