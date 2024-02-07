package cl.bci.ecodig.customersupport.cases.service

import cl.bci.ecodig.customersupport.cases.configuration.ParametersConfiguration
import cl.bci.ecodig.customersupport.cases.service.ParametersService
import cl.bci.ecodig.customersupport.cases.service.ParametersServiceImpl
import spock.lang.Specification

class ParametersServiceImplSpec extends Specification {

    ParametersConfiguration parametersConfiguration = Stub()

    ParametersService service

    def setup() {
        service = new ParametersServiceImpl(parametersConfiguration)
    }

    def "get Parameters OK"() {
        when:
        def response = service.getParameters()

        then:
        response
    }
}
