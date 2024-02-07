package cl.bci.ecodig.customersupport.cases.service

import cl.bci.ecodig.channel.ApplicationIdentifier
import cl.bci.ecodig.core.spring.exception.NotFoundException
import cl.bci.ecodig.customersupport.cases.configuration.ParametersConfiguration
import cl.bci.ecodig.customersupport.cases.controller.dto.parameters.DerivativeValuesDto
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData
import cl.bci.ecodig.customersupport.cases.repository.CasesMessageSender
import cl.bci.ecodig.customersupport.cases.repository.CasesRepository
import cl.bci.ecodig.customersupport.cases.repository.ErrorsMessageSender
import cl.bci.ecodig.customersupport.cases.repository.cosmosdb.model.CasesModel
import cl.bci.ecodig.customersupport.customerprofile.service.CustomersProfileServiceImpl
import spock.lang.Specification
import spock.lang.Subject

@Subject(CustomersProfileServiceImpl)
class CasesServiceImplSpec extends Specification{

    CasesRepository casesRepository = Stub()

    AttachedFilesService attachedFilesService = Stub()

    CasesMessageSender casesMessageSender = Stub()

    ErrorsMessageSender errorsMessageSender = Stub()

    ParametersConfiguration parametersConfiguration = Stub()

    ApplicationIdentifier applicationIdentifier = Stub()

    CasesService service

    def setup() {
        service = new CasesServiceImpl(casesRepository, attachedFilesService, casesMessageSender, errorsMessageSender, parametersConfiguration, applicationIdentifier)
    }

    def "create Case OK with attachments"(){
        given:
        def completeCase = CasesFakeData.hiddenCaseEntityTest()
        def completeCaseUpdate = CasesFakeData.hiddenUpdateCaseEntityTest()
        applicationIdentifier.allAttributes() >> Map.of("id_partner","1")
        parametersConfiguration.getPartners() >> Map.of(1,"Bci",2,"MACH",3,"Lider Bci")
        parametersConfiguration.getValidDerivativeValues() >> Map.of(1,CasesFakeData.getDerivativedValuesDTOStandard(),2,CasesFakeData.getDerivativedValuesDTOStandard(),3,CasesFakeData.getDerivativedValuesDTOStandard())
        casesRepository.save(_) >> CasesModel.builder().build()
        when:
        def response = service.createCase(modelRequests)
        then:
        notThrown()
        where:
        modelRequests << [ CasesFakeData.getCreateCaseRequestDTOStandard(),
                           CasesFakeData.getCreateCaseRequestDTOStandardWithoutPartnerTarget(),
                           CasesFakeData.getCreateCaseRequestDTOStandardWithEmptyAttachments(),
                           CasesFakeData.getCreateCaseRequestDTOStandardWithAttachments()]

    }

    def "create Case OK without attachments"(){
        given:
        def completeCase = CasesFakeData.hiddenCaseEntityTest()
        def completeCaseUpdate = CasesFakeData.hiddenUpdateCaseEntityTest()
        casesRepository.save(_) >> CasesFakeData.getCasesModelStandardWithAttachments()
        parametersConfiguration.getPartners() >> Map.of(1,"Bci",2,"MACH",3,"Lider Bci")
        casesMessageSender.sendCreateCaseMessage(_) >> null
        when:
        def response = service.createCase(CasesFakeData.getCreateCaseRequestDTOStandardWithoutAttachments())
        then:
        notThrown()

    }

    def "create Case NO OK"(){
        when:
        def response = service.createCase(CasesFakeData.getCreateCaseRequestDTOStandard())
        then:
        thrown(Exception)

    }

    def "get Case OK"(){
        given:
        def idCase = "1234-asdf-0"
        when:
        def response = service.getCase(idCase)
        then:
        thrown(Exception)

    }

    def "get Case NO OK"(){
        given:
        def idCase = "1234-asdf-0"
        casesRepository.findById(_) >> Optional.of(modelResponses)
        when:
        def response = service.getCase(idCase)
        then:
        response
        where:
        modelResponses << [ CasesFakeData.getCasesModelWithUpdates() ,
                            CasesFakeData.getCasesModelNoAttachmentsStatus(),
                            CasesFakeData.getCasesModelPendingAttachmentsStatus()]

    }

    def "update Case OK"(){
        given:
        def idCase = "1234-asdf-0"
        casesRepository.findById(_) >> Optional.of(CasesFakeData.getCasesModelWithUpdates())
        casesRepository.save(_) >> CasesFakeData.getCasesModelStandardWithAttachments()
        when:
        def response = service.updateCase(idCase, modelRequests)
        then:
        notThrown()
        where:
        modelRequests << [ CasesFakeData.getUpdatesDTOStandard(),
                           CasesFakeData.getUpdatesDTOStandardWithEmptyAttachments(),
                           CasesFakeData.getUpdatesDTOStandardWithAttachments()]
    }

    def "update Case NO OK"(){
        given:
        def idCase = "1234-asdf-0"
        casesRepository.findById(_) >> Optional.of(CasesFakeData.getCasesModelWithUpdates())
        when:
        def response = service.updateCase(idCase, CasesFakeData.getUpdatesDTOStandard())
        then:
        thrown(Exception)
    }

    def "historical Case OK"(){
        given:
        def documentNumber = "1-9"
        def dateStart = "2021-01-01"
        def dateEnd = "2021-01-02"
        casesRepository.findByDocumentNumberAndDates(_,_,_) >> Arrays.asList(CasesFakeData.getCasesModelWithUpdates(), CasesFakeData.getCasesModelNoAttachmentsStatus(),CasesFakeData.getCasesModelPendingAttachmentsStatus())
        when:
        def response = service.getHistoricalCase(documentNumber, dateStart, dateEnd)
        then:
        response
    }

    def "historical Case no OK by documentNumber"(){
        given:
        def documentNumber = "16778231-0"
        def dateStart = "2021-01-01"
        def dateEnd = "2021-01-02"
        casesRepository.findByDocumentNumberAndDates(_,_,_) >> Arrays.asList()
        when:
        def response = service.getHistoricalCase(documentNumber, dateStart, dateEnd)
        then:
        thrown(NotFoundException)
    }

    def "attach file to Case OK"(){
        given:
        def idCase = "1234-asdf-0"
        casesRepository.findById(_) >> Optional.of(modelResponses)
        when:
        def response = service.attachFilesToCase(idCase, _)
        then:
        notThrown()
        where:
        modelResponses << [ CasesFakeData.getCasesModelWithUpdates() ,
                            CasesFakeData.getCasesModelPendingAttachmentsStatus()]
    }

    def "delete file to Case OK"(){
        given:
        def idCase = "1234-asdf-0"
        def fileName = "bart.jpg"
        casesRepository.findById(_) >> Optional.of(CasesFakeData.getCasesModelWithUpdatesAndAttachments())
        casesRepository.save(_) >> CasesModel.builder().build()
        when:
        def response = service.deleteFileToCase(idCase, fileName)
        then:
        notThrown()
    }

}
