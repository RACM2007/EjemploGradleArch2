package cl.bci.ecodig.customersupport.customerprofile.webclient

import cl.bci.ecodig.core.spring.exception.ErrorEntity
import cl.bci.ecodig.core.spring.exception.TechnicalException
import cl.bci.ecodig.customersupport.customerprofile.configuration.EndpointsConfiguration
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.configuration.EcodigConfig
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.products.CustomersProductsResponseDTO
import cl.bci.ecodig.customersupport.customerprofile.repository.CustomersProductsClient
import cl.bci.ecodig.customersupport.customerprofile.repository.webclient.CustomersProductsClientImpl
import cl.bci.ecodig.httpclient.spring.WebClientBuilderFactory
import cl.bci.ecodig.httpclient.spring.configuration.HttpClientProperties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import spock.lang.Specification

class CustomersProductsClientImplSpec extends Specification{

    CustomersProductsClient customersProductsClient

    static MockWebServer mockBackEnd
    static String url
    static final ObjectMapper serializer = new ObjectMapper()

    def setupSpec() {
        mockBackEnd = new MockWebServer()
        mockBackEnd.start()
        url = String.format("http://localhost:%s/ms-customers-products/test",
                mockBackEnd.getPort())
    }

    def cleanupSpec() throws IOException {
        mockBackEnd.shutdown()
    }

    def endpointConfiguration = Mock(EndpointsConfiguration.class)

    def setup() {
        endpointConfiguration.getEcosistemaDigital() >> new EcodigConfig(urlMsCustomerProducts: url)
        customersProductsClient = new CustomersProductsClientImpl(WebClientBuilderFactory.getWebClientInternalBuilder(new HttpClientProperties(),"appName", 500).build(), endpointConfiguration)
    }

    def "getCustomersProducts correcto"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setBody(serializer.writeValueAsString(CustomersProductsResponseDTO.builder().build()))
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json;charset=UTF-8"))
        def documentNumber = "1-9"
        when:
        def response = customersProductsClient.getCustomersProducts(documentNumber)
        then:
        response
    }

    def "getCustomersProducts error servicio"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500))
        def documentNumber = "1-9"
        when:
        customersProductsClient.getCustomersProducts(documentNumber)
        then:
        def e = thrown(TechnicalException.class)
        e.getMessage() == "Problem calling ms-customers-products"
    }

    def "getCustomersProducts not found servicio"() {
        given:
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(serializer.writeValueAsString(ErrorEntity.builder().build()))
                .addHeader("Content-Type", "application/json"))
        def documentNumber = "1-9"
        when:
        def response = customersProductsClient.getCustomersProducts(documentNumber)
        then:
        response
    }


}