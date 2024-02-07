package cl.bci.ecodig.customersupport.cases.mapper

import cl.bci.ecodig.customersupport.cases.controller.mapper.CasesMapper
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData
import spock.lang.Specification

class CasesMapperSpec extends Specification{

    CasesMapper mapper

    def setup() {
        mapper = new CasesMapper()
    }

    def "mapper mapperCreateCaseToModel"(){
        given:
        def request = CasesFakeData.getCreateCaseRequestDTOStandard()
        when:
        def response = mapper.mapperCreateCaseToEntity(request)
        then:
        response
    }

    def "mapper mapperCreateCaseToEvent"(){
        given:
        def request = CasesFakeData.getCasesModelStandard()
        when:
        def response = mapper.mapperCreateCaseToEvent(request)
        then:
        response
    }

    def "mapper mapperUpdateCaseToEvent"(){
        given:
        def request = CasesFakeData.getCasesModelStandard()
        when:
        def response = mapper.mapperUpdateCaseToEvent(request)
        then:
        response
    }

    def "mapper mapperCreateCaseToErrorEvent"(){
        given:
        def request = CasesFakeData.getCreateCaseRequestDTOStandard()
        when:
        def response = mapper.mapperCreateCaseToErrorEvent(request,"wrong")
        then:
        response
    }

    def "mapper mapperUpdateCaseToErrorEvent"(){
        given:
        def request = CasesFakeData.getUpdatesDTOStandard()
        when:
        def response = mapper.mapperUpdateCaseToErrorEvent(request,"wrong")
        then:
        response
    }

    def "mapper mapperModelToGetCaseResponseDTO"(){
        when:
        def response = mapper.mapperModelToGetCaseResponseDTO(modelResponses)
        then:
        response
        where:
        modelResponses << [ CasesFakeData.getCaseEntityStandard() ,
                            CasesFakeData.getCaseEntityWithUpdates(),
                            CasesFakeData.getCaseEntityWithUpdatesAndAttachments() ]
    }

    def "mapper mapperModelToGetHistoricalCaseResponseDTO"(){
        given:
        def request = Arrays.asList(CasesFakeData.getCasesModelStandard(),CasesFakeData.getCasesModelWithUpdates(),CasesFakeData.getCasesModelStandardWithAttachments())
        when:
        def response = mapper.mapperModelToGetHistoricalCaseResponseDTO(request)
        then:
        response
    }

    def "mapper mapperUpdateCaseAttachmentsToModel"(){
        given:
        def requestAttachment = CasesFakeData.getFilesDTOStandard()
        when:
        def response = mapper.mapperUpdateCaseAttachmentsToModel(requestAttachment, responsesEntity)
        then:
        response
        where:
        responsesEntity << [ CasesFakeData.getCaseEntityStandard(), CasesFakeData.getCaseEntityWithAttachments() ]
    }
}
