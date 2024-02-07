package cl.bci.ecodig.customersupport.customerprofile.service

import cl.bci.ecodig.core.spring.exception.NotFoundException
import cl.bci.ecodig.customersupport.customerprofile.repository.CustomersProductsClient
import cl.bci.ecodig.customersupport.customerprofile.repository.CustomersProfileClient
import cl.bci.ecodig.customersupport.customerprofile.helper.ProfileFakeData
import spock.lang.Specification
import spock.lang.Subject

@Subject(CustomersProfileServiceImpl)
class CustomersProfileServiceImplSpec extends Specification{

    CustomersProductsClient productsClient = Stub()

    CustomersProfileClient profileClient = Stub()

    CustomersProfileService service

    def setup() {
        service = new CustomersProfileServiceImpl(productsClient, profileClient)
    }

    def "getProfileSummary OK"(){
        given:
        def documentNumber = "16778231-0"
        profileClient.getCustomersProfile(documentNumber) >> ProfileFakeData.getProfileResponse()
        when:
        def response = service.getProfileSummary(documentNumber)
        then:
        response

    }

    def "getProfileSummary Profile Not Found"(){
        given:
        def documentNumber = "16778231-0"
        when:
        profileClient.getCustomersProfile(documentNumber) >> null
        def response = service.getProfileSummary(documentNumber)
        then:
        def e = thrown(NotFoundException.class)
        e.getMessage() == "No profile summary found for 16778231-0"

    }
}
