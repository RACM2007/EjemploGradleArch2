package cl.bci.ecodig.customersupport.customerprofile.webclient

import cl.bci.ecodig.core.spring.exception.ErrorEntity
import cl.bci.ecodig.core.spring.exception.TechnicalException
import cl.bci.ecodig.customersupport.customerprofile.configuration.EndpointsConfiguration
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.configuration.EcodigConfig
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.profile.CustomersProfileResponseDTO
import cl.bci.ecodig.customersupport.customerprofile.repository.CustomersProfileClient
import cl.bci.ecodig.customersupport.customerprofile.repository.webclient.CustomersProfileClientImpl
import cl.bci.ecodig.httpclient.spring.WebClientBuilderFactory
import cl.bci.ecodig.httpclient.spring.configuration.HttpClientProperties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import spock.lang.Specification

class CustomersProfileClientImplSpec extends Specification{

    CustomersProfileClient customersProfileClient

    static MockWebServer mockBackEnd
    static String url
    static final ObjectMapper serializer = new ObjectMapper()

    def setupSpec() {
        mockBackEnd = new MockWebServer()
        mockBackEnd.start()
        url = String.format("http://localhost:%s/ms-customers-profile/test",
                mockBackEnd.getPort())
    }

    def cleanupSpec() throws IOException {
        mockBackEnd.shutdown()
    }

    def endpointConfiguration = Mock(EndpointsConfiguration.class)

    def setup() {
        endpointConfiguration.getEcosistemaDigital() >> new EcodigConfig(urlMsCustomerProfile: url)
        customersProfileClient = new CustomersProfileClientImpl(WebClientBuilderFactory.getWebClientInternalBuilder(new HttpClientProperties(),"appName", 500).build(), endpointConfiguration)
    }

    def "getCustomersProfile correcto"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setBody(serializer.writeValueAsString(CustomersProfileResponseDTO.builder().build()))
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json;charset=UTF-8"))
        def documentNumber = "1-9"
        when:
        def response = customersProfileClient.getCustomersProfile(documentNumber)
        then:
        response
    }

    def "getCustomersProfile error servicio"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500))
        def documentNumber = "1-9"
        when:
        customersProfileClient.getCustomersProfile(documentNumber)
        then:
        def e = thrown(TechnicalException.class)
        e.getMessage() == "Problem calling ms-customers-profile"
    }

    def "getCustomersProfile not found servicio"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(serializer.writeValueAsString(ErrorEntity.builder().build()))
                .addHeader("Content-Type", "application/json"))
        def documentNumber = "1-9"
        when:
        def response = customersProfileClient.getCustomersProfile(documentNumber)
        then:
        response == null
    }


}