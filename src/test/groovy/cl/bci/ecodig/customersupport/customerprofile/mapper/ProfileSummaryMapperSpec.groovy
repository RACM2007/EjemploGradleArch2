package cl.bci.ecodig.customersupport.customerprofile.mapper

import cl.bci.ecodig.customersupport.customerprofile.controller.mapper.ProfileSummaryMapper
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.products.CustomersProductsResponseDTO
import cl.bci.ecodig.customersupport.customerprofile.helper.ProfileFakeData
import spock.lang.Specification

class ProfileSummaryMapperSpec extends Specification{

    ProfileSummaryMapper mapper

    def setup() {
        mapper = new ProfileSummaryMapper()
    }

    def "mapper mapperJoinCustomersSummary"(){
        when:
        def response = mapper.mapperJoinCustomersSummary(profileResponses,productsResponses)
        then:
        response
        where:
        profileResponses << [
                ProfileFakeData.getProfileResponse(),
                ProfileFakeData.getProfileResponseJustBci(),
                ProfileFakeData.getProfileResponse()]
        productsResponses << [
                CustomersProductsResponseDTO.builder().build(),
                ProfileFakeData.getProductsResponse(),
                ProfileFakeData.getProductsEmptyResponse()]

    }
}
