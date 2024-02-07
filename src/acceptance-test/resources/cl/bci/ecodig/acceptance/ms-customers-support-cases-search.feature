Feature: Functional tests case search ms-customers-support

  Background:
    * def varEnv = defEnv
    And print varEnv
    * def apiBaseUrl = baseUrlApim
    * header Accept = 'application/json'
    * def resultToken = karate.callSingle('obtener-token.feature')
    * def subscriptionKey = customerSupportBciSubscription


  @name=get-case-by-id-ok
  Scenario: Get case with id - OK
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * header karate-name = "get-case-by-id-ok"
    * def data = read("json/"+defEnv+"/testData.json")['findCase']
    Given url path + '/cases/' + data
    When method GET
    Then status 200

  @name=get-case-by-id-notfound
  Scenario: Get case with non-existing id - Not Found
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/testData.json")['caseKO']
    Given url path + '/cases/' + data
    When method GET
    Then status 404

  @name=get-historical-case-ok
  Scenario: Get Historical case - OK
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * header karate-name = "get-historical-case-ok"
    * def data = read("json/"+defEnv+"/testData.json")['historicalCaseDocumentNumberOK']
    Given url path + '/cases/historical/' + data
    When method GET
    Then status 200

   @name=get-historical-case-notFound
   Scenario: Get historical cases for a given documentNumber - Not Found
     And print token = resultToken.response.access_token
     * header Authorization = 'Bearer ' + token
     * header x-apikey = subscriptionKey
     * def path = apimBaseUrlMs
     * def data = read("json/"+defEnv+"/testData.json")['historicalCaseDocumentNumberNoData']
     Given url path + '/cases/historical/' + data
     When method GET
     Then status 404

   @name=get-historical-case-badInitialDate
   Scenario: Get historical cases for a given documentNumber - Bad Request Initial Date
     And print token = resultToken.response.access_token
     * header Authorization = 'Bearer ' + token
     * header x-apikey = subscriptionKey
     * def path = apimBaseUrlMs
     * def data = read("json/"+defEnv+"/testData.json")['historicalCaseDocumentNumberOK']
     Given url path + '/cases/historical/' + data + '?start=2026'
     When method GET
     Then status 400
     And match response.errors[0].message == "start value not allowed"

   @name=get-historical-case-badEndDate
   Scenario: Get historical cases for a given documentNumber - Bad Request End Date
     And print token = resultToken.response.access_token
     * header Authorization = 'Bearer ' + token
     * header x-apikey = subscriptionKey
     * def path = apimBaseUrlMs
     * def data = read("json/"+defEnv+"/testData.json")['historicalCaseDocumentNumberOK']
     Given url path + '/cases/historical/' + data + '?end=2028-01-01'
     When method GET
     Then status 400
     And match response.errors[0].message == "end value not allowed"

   @name=get-historical-case-badBothDates
   Scenario: Get historical cases for a given documentNumber - Bad Request Both Dates
     And print token = resultToken.response.access_token
     * header Authorization = 'Bearer ' + token
     * header x-apikey = subscriptionKey
     * def path = apimBaseUrlMs
     * def data = read("json/"+defEnv+"/testData.json")['historicalCaseDocumentNumberOK']
     Given url path + '/cases/historical/' + data + '?start=2026-01-01&end=2028-01-01'
     When method GET
     Then status 400