package cl.bci.ecodig.customersupport.cases.helper

import cl.bci.ecodig.customersupport.cases.controller.dto.event.ResultAnalysisFilesResponseEventDTO

class ConsumerFakeData {

    static ResultAnalysisFilesResponseEventDTO.EventData getData() {
        return ResultAnalysisFilesResponseEventDTO.EventData.builder()
                .blobUri("https://testaccountblob.blob.core.windows.net/ms-customersupportSA/completoMasInfoParaLLegar/asdf-1234/qwerty.png")
                .scanResultType("No threat Found")
                .build()
    }

    static ResultAnalysisFilesResponseEventDTO.EventData getDataWithoutBlobUri() {
        return ResultAnalysisFilesResponseEventDTO.EventData.builder()
                .scanResultType("sin amenazas")
                .build()
    }

    static ResultAnalysisFilesResponseEventDTO.EventData getDataWithoutScanResult() {
        return ResultAnalysisFilesResponseEventDTO.EventData.builder()
                .blobUri("https://testaccountblob.blob.core.windows.net/ms-customersupportSA/completoMasInfoParaLLegar/asdf-1234-qwerty.txt")
                .build()
    }
    static ResultAnalysisFilesResponseEventDTO getResultAnalysisFilesResponseEventDTO() {
        return ResultAnalysisFilesResponseEventDTO.builder()
                .id("1")
                .data(getData())
                .build()
    }

    static ResultAnalysisFilesResponseEventDTO getResultAnalysisFilesResponseEventDTOWithoutData() {
        return ResultAnalysisFilesResponseEventDTO.builder()
                .id("1")
                .build()
    }

    static ResultAnalysisFilesResponseEventDTO getResultAnalysisFilesResponseEventDTOWithoutBlobUri() {
        return ResultAnalysisFilesResponseEventDTO.builder()
                .id("1")
                .data(getDataWithoutBlobUri())
                .build()
    }

    static ResultAnalysisFilesResponseEventDTO getResultAnalysisFilesResponseEventDTOWithoutScanResultType() {
        return ResultAnalysisFilesResponseEventDTO.builder()
                .id("1")
                .data(getDataWithoutScanResult())
                .build()
    }
}
