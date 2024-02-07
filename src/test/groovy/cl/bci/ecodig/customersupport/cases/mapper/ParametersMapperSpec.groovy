package cl.bci.ecodig.customersupport.cases.mapper

import cl.bci.ecodig.customersupport.cases.configuration.ParametersConfiguration
import cl.bci.ecodig.customersupport.cases.controller.mapper.ParametersMapper
import spock.lang.Specification

class ParametersMapperSpec extends Specification{

    ParametersMapper mapper

    def setup() {
        mapper = new ParametersMapper()
    }

    def "mapper mapperParametersConfiguration"(){
        when:
        def response = mapper.mapperParametersConfiguration(parameterConfig)
        then:
        response
        where:
        parameterConfig << [new ParametersConfiguration(
                            partners: [1: "Partner 1", 2: "Partner 2"],
                            products: [1: "Product 1", 2: "Product 2"],
                            reason: [1: "Reason 1", 2: "Reason 2"],
                            reasonByProductAndPartner: ["1_1": "Reason 1 for Product 1 and Partner 1", "1_2": "Reason 1 for Product 1 and Partner 2"],
                            communicationChannel: [1: "Channel 1", 2: "Channel 2"],
                            type: [1: "Type 1", 2: "Type 2"],
                            status: [1: "Status 1", 2: "Status 2"]),
                            new ParametersConfiguration(
                            partners: [1: "Partner 1", 2: "Partner 2"])]
    }
}
