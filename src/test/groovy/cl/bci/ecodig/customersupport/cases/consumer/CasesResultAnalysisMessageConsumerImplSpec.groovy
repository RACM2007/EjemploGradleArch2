package cl.bci.ecodig.customersupport.cases.consumer

import cl.bci.ecodig.customersupport.cases.configuration.FilesConfiguration
import cl.bci.ecodig.customersupport.cases.repository.CasesRepository
import cl.bci.ecodig.customersupport.cases.service.AttachedFilesService
import cl.bci.ecodig.customersupport.cases.service.CasesServiceImpl
import cl.bci.ecodig.customersupport.cases.helper.ConsumerFakeData
import com.azure.core.util.BinaryData
import com.azure.storage.blob.BlobClient
import com.azure.storage.blob.BlobContainerClient
import spock.lang.Specification
import spock.lang.Subject

@Subject(CasesResultAnalysisMessageConsumerImpl)
class CasesResultAnalysisMessageConsumerImplSpec extends Specification{

    CasesRepository casesRepository = Stub()

    BlobContainerClient blobServiceClientQuarantineSA = GroovyMock(BlobContainerClient)

    BlobContainerClient blobServiceClientCustomerSupportSA = GroovyMock(BlobContainerClient)

    CasesServiceImpl casesService = Stub()

    AttachedFilesService attachedFilesService = Stub()

    FilesConfiguration filesConfiguration = Stub()

    CasesResultAnalysisMessageConsumerImpl service

    def setup() {
        service = new CasesResultAnalysisMessageConsumerImpl(casesRepository, blobServiceClientQuarantineSA, blobServiceClientCustomerSupportSA, casesService,attachedFilesService,filesConfiguration)
    }

    def "process file ok"(){
        given:
        filesConfiguration.getThreatNotFoundValue() >> "No threat Found ziii"
        when:
        service.process(ConsumerFakeData.getResultAnalysisFilesResponseEventDTO(), null)
        then:
        notThrown()

    }

    def "process file DLP Threat Found"(){
        given:
        filesConfiguration.getThreatNotFoundValue() >> "No threat Found"
        filesConfiguration.isDlpAnalysisEnabled() >> true
        attachedFilesService.dlpAnalysis(_) >> true
        when:
        service.process(ConsumerFakeData.getResultAnalysisFilesResponseEventDTO(), null)
        then:
        notThrown()

    }

    def "process file blob exception"(){
        given:
        filesConfiguration.getThreatNotFoundValue() >> "No threat Found"
        filesConfiguration.isDlpAnalysisEnabled() >> true
        attachedFilesService.dlpAnalysis(_) >> false
        blobServiceClientQuarantineSA.getBlobClient(_) >> Mock(BlobClient)
        when:
        service.process(ConsumerFakeData.getResultAnalysisFilesResponseEventDTO(), null)
        then:
        thrown(Exception)

    }


    def "process file exception2 "(){
        when:
        service.process(inputData, null)
        then:
        thrown(Exception)
        where:
        inputData << [ConsumerFakeData.getResultAnalysisFilesResponseEventDTOWithoutData()
                    , ConsumerFakeData.getResultAnalysisFilesResponseEventDTOWithoutBlobUri()
                    , ConsumerFakeData.getResultAnalysisFilesResponseEventDTOWithoutScanResultType()    ]

    }

}
