package cl.bci.ecodig.customersupport.cases.validation

import cl.bci.ecodig.customersupport.cases.configuration.ParametersConfiguration
import cl.bci.ecodig.customersupport.cases.validation.ParameterValueExists
import cl.bci.ecodig.customersupport.cases.validation.ParametersValidator
import spock.lang.Shared
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext

class ParametersValidatorSpec extends Specification {

    @Shared
    ParametersConfiguration configuration

    def setupSpec() {
        configuration = new ParametersConfiguration()
        configuration.partners = [1: "Partner 1", 2: "Partner 2"]
        configuration.products = [1: "Product 1", 2: "Product 2"]
        configuration.reason = [1: "Reason 1", 2: "Reason 2"]
        configuration.communicationChannel = [1: "Communication Channel 1", 2: "Communication Channel 2"]
        configuration.type = [1: "Type 1", 2: "Type 2"]
        configuration.status = [1: "Status 1", 2: "Status 2"]
    }

    def "should initialize mapFieldName property with value from annotation"() {
        given:
        def validator = new ParametersValidator()
        def annotation = Mock(ParameterValueExists)
        annotation.mapFieldNameToValidate() >> "partners"

        when:
        validator.initialize(annotation)

        then:
        validator.mapFieldName == "partners"
    }

    def "should return true for valid parameter value"() {
        given:
        def validator = new ParametersValidator()
        validator.mapFieldName = fieldNames
        validator.parametersConfiguration = configuration
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid(1, context)

        then:
        result == true

        where:
        fieldNames << ["partners", "products","reason", "communicationChannel", "type", "status"]
    }

    def "should return false for invalid parameter value"() {
        given:
        def validator = new ParametersValidator()
        validator.mapFieldName = "partners"
        validator.parametersConfiguration = configuration
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid(3, context)

        then:
        result == false
    }

    def "should return false when null mapFieldName is passed"() {
        given:
        def validator = new ParametersValidator()
        validator.mapFieldName = "potato"
        validator.parametersConfiguration = configuration
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid(1, context)

        then:
        result == false
    }
}