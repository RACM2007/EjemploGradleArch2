package cl.bci.ecodig.customersupport.cases.controller

import cl.bci.ecodig.customersupport.cases.service.CasesService
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData
import org.springframework.http.HttpStatus
import spock.lang.Specification

class CasesControllerSpec extends Specification{

    CasesService casesService = Stub()

    CasesController controller

    def setup(){
        controller = new CasesController(casesService)
    }

    def "test createCase"(){
        when:
         def response = controller.createCase(CasesFakeData.getCreateCaseRequestDTOStandard())
        then:
            response.status == HttpStatus.CREATED
    }

    def "test updateCase"(){
        when:
        def response = controller.updateCase("1234",CasesFakeData.getUpdatesDTOStandard())
        then:
        response.status == HttpStatus.OK
    }

    def "test getCase"(){
        when:
        def response = controller.getCase("1234")
        then:
        response.status == HttpStatus.OK
    }

    def "test getHistoricalCases"(){
        when:
        def response = controller.getHistoricalCases("1-9","2023-01-01","2023-01-02")
        then:
        response.status == HttpStatus.OK
    }

    def "test attachFileToCase"(){
        when:
        def response = controller.attachFileToCase("1234",_)
        then:
        response.status == HttpStatus.OK
    }

    def "test deleteFileToCase"(){
        when:
        def response = controller.deleteFileToCase("1234","file.png")
        then:
        response.status == HttpStatus.NO_CONTENT
    }
}
