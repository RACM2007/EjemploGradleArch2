package cl.bci.ecodig.customersupport.cases.helper

import com.azure.core.util.BinaryData
import com.azure.storage.blob.BlobClient
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import spock.lang.Specification

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class ConvertPdfToImagesHelperSpec  extends Specification{

    ConvertPdfToImagesHelper convertPdfToImagesHelper

    def setup() {
        convertPdfToImagesHelper = new ConvertPdfToImagesHelper()
    }

    def "test pdfFileAsInputStream"() {
        given: "A blob client and a binary data"
        BlobClient blobClient = Mock(BlobClient)
        BinaryData binaryData = BinaryData.fromBytes("test".getBytes())
        blobClient.downloadContent() >> binaryData

        and: "A PDDocument and a PDFRenderer"
        PDDocument document = Mock(PDDocument)
        PDDocument.load(_) >> document
        document.getNumberOfPages() >> 1
        PDFRenderer renderer = new PDFRenderer(document)
        BufferedImage image = ImageIO.read(new ByteArrayInputStream("test".getBytes()))
        renderer.renderImageWithDPI(_, _) >> image

        when: "pdfFileAsInputStream is called"
        def result = convertPdfToImagesHelper.pdfFileAsInputStream(blobClient)

        then:
        notThrown()
    }

}
