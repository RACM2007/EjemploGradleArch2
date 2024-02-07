package cl.bci.ecodig.customersupport.cases.validation

import cl.bci.ecodig.customersupport.cases.validation.LocalDateFormatWithCustomMessage
import cl.bci.ecodig.customersupport.cases.validation.LocalDateValidator
import spock.lang.Specification
import javax.validation.ConstraintValidatorContext

class LocalDateValidatorSpec extends Specification {

    def "should initialize required property with default value when not specified in annotation"() {
        given:
        def validator = new LocalDateValidator()
        def annotation = Mock(LocalDateFormatWithCustomMessage)
        annotation.required() >> false

        when:
        validator.initialize(annotation)

        then:
        validator.required == false
    }

    def "should return true for valid date"() {
        given:
        def validator = new LocalDateValidator()
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid("2021-01-01", context)

        then:
        result == true
    }

    def "should return false for date in the future"() {
        given:
        def validator = new LocalDateValidator()
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid("2050-01-01", context)

        then:
        result == false
    }

    def "should return false for invalid date format"() {
        given:
        def validator = new LocalDateValidator()
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid("01-01-2021", context)

        then:
        result == false
    }

    def "should return true for null value when not required"() {
        given:
        def validator = new LocalDateValidator(required: false)
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid(null, context)

        then:
        result == true
    }

    def "should return false for null value when required"() {
        given:
        def validator = new LocalDateValidator(required: true)
        def context = Mock(ConstraintValidatorContext)

        when:
        def result = validator.isValid("01-01-2021", context)

        then:
        result == false
    }
}