@ignore
@report=false
Feature: obtiene grupos de variables desde azure devops para setear en karate-config dichos valores como variables globales

  Scenario:
    Given url urlApiVariableGroups
    And header Authorization = 'basic ' + token
    When method get
    Then status 200
