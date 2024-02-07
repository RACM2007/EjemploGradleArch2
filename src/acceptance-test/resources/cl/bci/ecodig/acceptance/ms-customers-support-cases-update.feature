Feature: Functional tests case update ms-customers-support

  Background:
    * def varEnv = defEnv
    And print varEnv
    * def apiBaseUrl = baseUrlApim
    * header Accept = 'application/json'
    * def resultToken = karate.callSingle('obtener-token.feature')
    * def subscriptionKey = customerSupportBciSubscription


  @name=update-case-ok
  Scenario: Update case with id - OK
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * header karate-name = "update-case-ok"
    * def data = read("json/"+defEnv+"/testData.json")['findCase']
    * def bodyData = read("json/"+defEnv+"/updateCaseData.json")
    Given url path + '/cases/' + data
    And request bodyData
    When method PATCH
    Then status 200

  @name=update-case-by-case-notFound
  Scenario: Update case with non-existing id - Not Found
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/testData.json")['caseKO']
    * def bodyData = read("json/"+defEnv+"/updateCaseData.json")
    Given url path + '/cases/' + data
    And request bodyData
    When method PATCH
    Then status 404

   @name=update-case-badBodyData
   Scenario: Update case with bad body Data - Bad Request
     And print token = resultToken.response.access_token
     * header Authorization = 'Bearer ' + token
     * header x-apikey = subscriptionKey
     * def path = apimBaseUrlMs
     * def data = read("json/"+defEnv+"/testData.json")['findCase']
     * def bodyData = read("json/"+defEnv+"/updateCaseBadData.json")
     Given url path + '/cases/' + data
     And request bodyData
     When method PATCH
     Then status 400