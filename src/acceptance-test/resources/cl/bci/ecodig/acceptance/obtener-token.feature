@ignore
Feature: Obtencion de token

  Background:
    * url apimBaseUrlMs
    * def varEnv = defEnv
    * def tenantEcodig = tenantEcosistemas
    * header Accept = 'application/json'
    * def clientId = customerSupportClientId
    * def clientSecret = customerSupportClientSecret
    * def scope = customerSupportScope
    * def subscriptionKey = customerSupportBciSubscription
    * def urlToken = 'https://login.microsoftonline.com/' + tenantEcodig + '/oauth2/v2.0/token'

  #Consulta servicio de generacion de token
  Scenario: obtencion de bearer token
    * def path = urlToken
    Given url path
    And header Content-Type = 'application/x-www-form-urlencoded'
    * header Ocp-Apim-Subscription-Key = subscriptionKey
    * header Ocp-Apim-Trace = true
    * form field grant_type = 'client_credentials'
    * form field client_id = clientId
    * form field client_secret = clientSecret
    * form field scope = scope
    When method POST
    Then status 200
    And print varEnv
    And print urlToken
    And print response.token_type
    And print response.access_token
    
  

