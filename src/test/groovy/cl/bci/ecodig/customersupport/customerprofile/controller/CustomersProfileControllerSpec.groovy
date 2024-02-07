package cl.bci.ecodig.customersupport.customerprofile.controller

import cl.bci.ecodig.customersupport.customerprofile.controller.CustomersProfileController
import cl.bci.ecodig.customersupport.customerprofile.service.CustomersProfileService
import spock.lang.Specification

class CustomersProfileControllerSpec extends Specification{

    CustomersProfileService customersProfileService = Stub()

    CustomersProfileController controller

    def setup(){
        controller = new CustomersProfileController(customersProfileService)
    }

    def "test get customers profile info"(){
        given:
            def documentNumber = "1-9"
        when:
         def response = controller.getCustomerProfileSummary(documentNumber)
        then:
            response.body
    }
}
