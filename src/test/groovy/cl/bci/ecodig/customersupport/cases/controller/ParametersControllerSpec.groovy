package cl.bci.ecodig.customersupport.cases.controller

import cl.bci.ecodig.customersupport.cases.service.ParametersService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class ParametersControllerSpec extends Specification{

    ParametersService parametersService = Stub()

    ParametersController controller

    def setup(){
        controller = new ParametersController(parametersService)
    }

    def "test getParameters"(){
        when:
         def response = controller.getParameters()
        then:
            response.status == HttpStatus.OK
    }
}
