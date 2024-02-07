package cl.bci.ecodig.customersupport.cases.mapper


import cl.bci.ecodig.customersupport.cases.repository.cosmosdb.mapper.ModelMapper
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData

import spock.lang.Specification

class ModelMapperSpec extends Specification{

    ModelMapper mapper

    def setup() {
        mapper = new ModelMapper()
    }


    def "mapper mapperUpdateCaseToModel"(){
        given:
        def request1 = CasesFakeData.getUpdatesDTOStandard()
        when:
        def response = mapper.mapperUpdateCaseToModel(request1,modelResponses)
        then:
        response
        where:
        modelResponses << [ CasesFakeData.getCaseEntityStandard() ,
                            CasesFakeData.getCaseEntityWithUpdates() ]
    }

}
