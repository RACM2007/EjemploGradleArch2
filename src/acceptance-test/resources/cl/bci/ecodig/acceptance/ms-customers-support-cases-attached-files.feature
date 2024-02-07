Feature: Functional tests attached files ms-customers-support

  Background:
    * def varEnv = defEnv
    And print varEnv
    * def apiBaseUrl = baseUrlApim
    * header Accept = 'application/json'
    * def resultToken = karate.callSingle('obtener-token.feature')
    * def subscriptionKey = customerSupportBciSubscription

  @name=attach-file-to-case-notFound
  Scenario: Update case with non-existing id - Not Found
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/testData.json")['caseKO']
    * header karate-name = "attach-file-to-case-notFound"
    * def bodyData = read("json/"+defEnv+"/updateCaseData.json")
    Given url path + '/cases/' + data + '/attachments?id-attachment=bart.jpg'
    And request bodyData
    When method PATCH
    Then status 404

  @name=attach-file-to-case-BadRequest
  Scenario: Update case with existing id - Bad Request
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/testData.json")['findCase']
    Given url path + '/cases/' + data + '/attachments'
    And multipart file util_tcreditoonline.log = { read: 'json/common/util_tcreditoonline.log', filename: 'util_tcreditoonline.log', contentType: 'text/plain' }
    When method PATCH
    Then status 400

  @name=delete-attached-file-failed
  Scenario: Delete a file to a case failed test
    And print token = resultToken.response.access_token
    * header Authorization = 'Bearer ' + token
    * header x-apikey = subscriptionKey
    * header karate-name = "delete-attached-file-failed"
    * def path = apimBaseUrlMs
    * def data = read("json/"+defEnv+"/testData.json")['caseOK']
    Given url path + '/cases/' + data + '/attachments?id-attachment=bart.jpg'
    When method DELETE
    Then status 404
