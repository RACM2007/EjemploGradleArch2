package cl.bci.ecodig.customersupport.cases.service

import cl.bci.ecodig.customersupport.cases.configuration.FilesConfiguration
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.AttachedFilesDto
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData
import cl.bci.ecodig.customersupport.cases.helper.ConvertPdfToImagesHelper
import cl.bci.ecodig.customersupport.cases.helper.UploadFilesHelper
import cl.bci.ecodig.customersupport.cases.repository.ComputerVisionClient
import cl.bci.ecodig.customersupport.cases.repository.ZendeskAPIClient
import cl.bci.ecodig.customersupport.cases.repository.blobstorage.BlobStorageCustomerSupportImpl
import cl.bci.ecodig.customersupport.cases.repository.blobstorage.BlobStorageQuarantineImpl
import com.azure.storage.blob.BlobContainerClient
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class AttachedFilesServiceSpec extends Specification{

    UploadFilesHelper uploadFilesHelper = Stub()

    BlobStorageQuarantineImpl blobStorageAttachmentsRepository = Stub()

    BlobStorageCustomerSupportImpl blobStorageCustomerSupportRepository = Stub()

    BlobContainerClient blobServiceClientQuarantineSA = GroovyMock(BlobContainerClient)

    ZendeskAPIClient zendeskAPIClient = Stub()

    ComputerVisionClient computerVisionClient = Stub()

    FilesConfiguration filesConfiguration = Stub()

    ConvertPdfToImagesHelper convertPdfToImagesHelper = Stub()

    AttachedFilesService service


    def setup() {
        service = new AttachedFilesService(uploadFilesHelper, blobStorageAttachmentsRepository, blobStorageCustomerSupportRepository, blobServiceClientQuarantineSA, zendeskAPIClient, computerVisionClient, filesConfiguration, convertPdfToImagesHelper)
    }

    def "upload files to quarantine"(){
        given:
        def idCase = "1234-asdf-0"
        def multipart = Mock(MultipartFile)
        def inputStream = new ByteArrayInputStream("asdfg".getBytes())
        def fileType = "application/jpg"
        multipart.getInputStream() >> inputStream
        def files = Arrays.asList(multipart)
        when:
        def response = service.uploadFilesToQuarantineStorageAccount(idCase, files)
        then:
        notThrown()

    }

    def "exceded files to upload"(){
        given:
        def idCase = "1234-asdf-0"
        def multipart = Mock(MultipartFile)
        def files = Arrays.asList(multipart, multipart, multipart)
        when:
        when:
        def response = service.uploadFilesToQuarantineStorageAccount(idCase, files)
        then:
        notThrown()

    }

    def "checkForGenerateSAS"(){
        when:
        def response = service.checkForGenerateSAS(request)
        then:
        notThrown()
        where:
        request << [CasesFakeData.getAttachmentsDTOStandard(),
                    CasesFakeData.getAttachmentsWithThreat()]

    }

    def "test validateFileSize"() {
        setup:
        byte[] data = new byte[1024] // 1 KB
        InputStream inputStream = new ByteArrayInputStream(data)

        when:
        service.validateFileSize(inputStream)

        then:
        thrown(Exception)
    }

    def "test transformAndUploadFileToQuarantineStorageAccount"() {
        setup:
        AttachedFilesDto file = new AttachedFilesDto()
        file.setLink("link")
        String idCase = "idCase"
        byte[] data = new byte[1024] // 1 KB
        zendeskAPIClient.getFile(file.getLink()) >> data

        when:
        service.transformAndUploadFileToQuarantineStorageAccount(file, idCase)

        then:
        notThrown()
    }

    def "test transformAndUploadFileToQuarantineStorageAccount no link"() {
        setup:
        AttachedFilesDto file = new AttachedFilesDto()
        String idCase = "idCase"
        when:
        service.transformAndUploadFileToQuarantineStorageAccount(file, idCase)

        then:
        notThrown()
    }

    def "test transformAndUploadFileToQuarantineStorageAccount exception"() {
        setup:
        AttachedFilesDto file = new AttachedFilesDto()
        file.setLink("link")
        String idCase = "idCase"
        byte[] data = new byte[1024] // 1 KB
        zendeskAPIClient.getFile(file.getLink()) >> { throw new Exception() }

        when:
        service.transformAndUploadFileToQuarantineStorageAccount(file, idCase)

        then:
        notThrown()
    }

    def "test invalidUploadFiles"(){
        when:
        service.checkUploadedFiles(request, "idCase")
        then:
         notThrown()
        where:
        request << [ null, new ArrayList<>()]
    }

    def "test checkUploadFiles"(){
        given:
        def request = List.of(CasesFakeData.getAttachedFilesDTOStandard(), CasesFakeData.getAttachedFilesDTOStandardBase64())
        when:
        service.checkUploadedFiles(request, "idCase")
        then:
        notThrown()
    }

    def "test dlpAnalysis false result"(){
        given:
        computerVisionClient.getImageAnalysis(_) >> cvResponses
        filesConfiguration.getRegexForDlpAnalysis() >> List.of(".*\\..*")
        when:
        def response = service.dlpAnalysis( "anyFileName.extension")
        then:
        response == false
        where:
        cvResponses << [ CasesFakeData.getComputerVisionImageAnalysisResponseDtoStandard(),
                         CasesFakeData.getComputerVisionImageAnalysisResponseDtoPartial()]
    }

    def "test dlpAnalysis false result match pdf extension"(){
        given:
        convertPdfToImagesHelper.pdfFileAsInputStream(_) >> List.of(new ByteArrayInputStream("asdfg".getBytes()))
        computerVisionClient.getImageAnalysis(_) >> CasesFakeData.getComputerVisionImageAnalysisResponseDtoStandard()
        filesConfiguration.getRegexForDlpAnalysis() >> List.of(".*-.*")
        when:
        def response = service.dlpAnalysis( "any-FileName.pdf")
        then:
        response == false
    }

    def "test dlpAnalysis exception"(){
        given:
        convertPdfToImagesHelper.pdfFileAsInputStream(_) >> { throw new Exception() }
        when:
        def response = service.dlpAnalysis( "any-FileName.pdf")
        then:
        response == false
    }

    def "test dlpAnalysis true result pdf"(){
        given:
        convertPdfToImagesHelper.pdfFileAsInputStream(_) >> List.of(new ByteArrayInputStream("asdfg".getBytes()))
        computerVisionClient.getImageAnalysis(_) >> CasesFakeData.getComputerVisionImageAnalysisResponseDtoStandard()
        filesConfiguration.getRegexForDlpAnalysis() >> List.of(".*_.*")
        when:
        def response = service.dlpAnalysis( "any-FileName.pdf")
        then:
        response == true
    }

}
