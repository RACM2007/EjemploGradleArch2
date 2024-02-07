package cl.bci.ecodig.customersupport.cases.helper

import cl.bci.ecodig.core.spring.exception.TechnicalException
import cl.bci.ecodig.customersupport.cases.configuration.FilesConfiguration
import org.apache.tika.Tika
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.multipart.MultipartFile
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.AttachedFilesDto
import spock.lang.Specification

class UploadFilesHelperSpec extends Specification {
    Tika tika = Mock()
    FilesConfiguration filesConfiguration = Mock()

    UploadFilesHelper attachmentsHelper = new UploadFilesHelper(tika, filesConfiguration)

    def setup() {
        ReflectionTestUtils.setField(attachmentsHelper, "validMediaTypes", List.of("jpg", "png"))
    }

    def "should return expected result of given fileType"() {
        expect:
        attachmentsHelper.isValidMediaType(fileType) == result
        where:
        fileType | result
        "jpg"       | true
        "png"       | true
        "bmp"       | false
        "gif"       | false
    }

    def "should return type of given file"() {
        given:
        def multipart = Mock(MultipartFile)
        def inputStream = new ByteArrayInputStream("".getBytes())
        def fileType = "application/jpg"
        multipart.getInputStream() >> inputStream
        tika.detect(inputStream) >> fileType
        expect:
        attachmentsHelper.getMediaType(multipart) == fileType
    }

    def "should throw TechnicalException when wrong typeFile is passed"() {
        given:
        def multipart = Mock(MultipartFile)
        def inputStream = new ByteArrayInputStream("".getBytes())
        def fileType = "application/jpg"
        multipart.getInputStream() >> inputStream
        tika.detect(inputStream) >> { throw new IOException() }
        when:
        attachmentsHelper.getValidMediaTypes()
        attachmentsHelper.getMediaType(multipart) == fileType
        then:
        thrown(TechnicalException)
    }

    def "validateFiles should throw ValidationException when the total size of files exceeds the maximum allowed"() {
        given:
        filesConfiguration.getMaximumFiles() >> 3
        filesConfiguration.getMaxSizeInBytes() >> 9000000
        tika.detect(_ as InputStream) >> "text/plain"

        when:
        attachmentsHelper.validateFiles(request)

        then:
        thrown(Exception)
        where:
        request << [ List.of(new MockMultipartFile('file1', 'file1.txt', 'text/plain', new byte[1000000]),
                new MockMultipartFile('file2', 'file2.jpg', 'jpg', new byte[1000000])),
                List.of(new MockMultipartFile('file1', 'file1.txt', 'text/plain', new byte[1000000]),
                        new MockMultipartFile('file2', 'file2.txt', 'text/plain', new byte[1000000]),
                        new MockMultipartFile('file3', 'file3.txt', 'text/plain', new byte[1000000]),
                        new MockMultipartFile('file4', 'file4.txt', 'text/plain', new byte[1000000]))]
    }

    def "validateFiles should throw ValidationException when the max size of files exceeds the maximum allowed"() {
        given:
        filesConfiguration.getMaximumFiles() >> 3
        filesConfiguration.getMaxSizeInBytes() >> 9

        when:
        attachmentsHelper.validateFiles( List.of(new MockMultipartFile('file1', 'file1.txt', 'text/plain', new byte[1000000])))

        then:
        thrown(Exception)
    }

    def "validateFiles ok"() {
        given:
        filesConfiguration.getMaximumFiles() >> 3
        filesConfiguration.getMaxSizeInBytes() >> 90000
        tika.detect(_ as InputStream) >> "jpg"

        when:
        attachmentsHelper.validateFiles( List.of(new MockMultipartFile('file1', 'file1.jpg', 'jpg', new byte[1000])))

        then:
        notThrown()
    }

    def "test getFilename"(){
        given:
        filesConfiguration.getPrefixForNameFiles() >> "name="
        filesConfiguration.getNumberOfDigitsForPrefixNameFiles() >> 5
        when:
        attachmentsHelper.getFileName(request)
        then:
        notThrown()
        where:
        request << [ AttachedFilesDto.builder().name("Name").build(), AttachedFilesDto.builder().link("link/name=asdf.jpeg").build()]
    }

    def "test no getFilename provided"(){
        when:
        attachmentsHelper.getFileName(AttachedFilesDto.builder().build())
        then:
        thrown(Exception)
    }

    def "test invalidCheckValidFilesIfExists"(){
        when:
        attachmentsHelper.checkValidFilesIfExists(request)
        then:
        notThrown()
        where:
        request << [ null, new ArrayList<>()]
    }

    def "test mazSize checkValidFilesIfExists"(){
        given:
        filesConfiguration.getMaximumFiles() >> 3
        def file1 = AttachedFilesDto.builder().name("link.png").build()
        def file2 = AttachedFilesDto.builder().name("base64.png").build()
        List<AttachedFilesDto> request = Arrays.asList(file1, file2, new ArrayList<>())
        tika.detect(_ as InputStream) >> "png"
        when:
        attachmentsHelper.checkValidFilesIfExists(request)
        then:
        thrown(Exception)
    }

    def "test validateFileSize with valid size"() {
        given: "An input stream with valid size"
        filesConfiguration.getMaxSizeInBytes() >> 9000000
        String str = "valid size"
        InputStream inputStream = new ByteArrayInputStream(str.getBytes())

        when: "validateFileSize is called"
        attachmentsHelper.validateFileSize(inputStream)

        then:
        notThrown()
    }

    def "test validateFileSize with exceeded size"() {
        given: "An input stream with valid size"
        filesConfiguration.getMaxSizeInBytes() >> 3
        String str = "valid size"
        InputStream inputStream = new ByteArrayInputStream(str.getBytes())

        when: "validateFileSize is called"
        attachmentsHelper.validateFileSize(inputStream)

        then:
        thrown(Exception)
    }

    def "test validateFileSize with exceeded size exception"() {
        given: "An input stream with valid size"
        filesConfiguration.getMaxSizeInBytes() >> { throw new IOException()}
        String str = "valid size"
        InputStream inputStream = new ByteArrayInputStream(str.getBytes())

        when: "validateFileSize is called"
        attachmentsHelper.validateFileSize(inputStream)

        then:
        notThrown()
    }

}