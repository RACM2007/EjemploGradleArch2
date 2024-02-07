package cl.bci.ecodig.customersupport.cases.helper

import cl.bci.ecodig.customersupport.cases.constant.Constants
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.CreateCaseRequestDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.AttachedFilesDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.AttachmentsDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.BranchOfficeDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.BusinessExecutiveDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.CaseClassificationDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.CaseDataRequestDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.CustomerDataDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.FilesDto
import cl.bci.ecodig.customersupport.cases.controller.dto.cases.common.UpdatesDto
import cl.bci.ecodig.customersupport.cases.controller.dto.parameters.DerivativeValuesDto
import cl.bci.ecodig.customersupport.cases.repository.cosmosdb.model.CasesModel
import cl.bci.ecodig.customersupport.cases.controller.dto.event.CaseInfoDTO
import cl.bci.ecodig.customersupport.cases.service.entity.Case
import cl.bci.ecodig.customersupport.cases.service.entity.CaseData
import cl.bci.ecodig.customersupport.cases.service.entity.UpdateCase
import cl.bci.ecodig.customersupport.cases.repository.webclient.dto.*

class CasesFakeData {


    static CustomerDataDto getCustomerDataDTOStandard(){
        return CustomerDataDto.builder()
                .documentNumber("1-9")
                .email("valid@email.com")
                .phone("+56966991377").build()
    }

    static CaseClassificationDto getCaseClasificationDTOStandard(){
        return CaseClassificationDto.builder()
                .communicationChannel(5)
                .communicationChannelOther("")
                .type(4)
                .idProduct(2)
                .reason(9)
                .other("nothing").build()
    }

    static BusinessExecutiveDto getBusinessExecutiveDTOStandard(){
        return BusinessExecutiveDto.builder()
                .name("Arale")
                .email("arale@drslump.com")
                .documentNumber("1-9").build()
    }

    static BranchOfficeDto getBranchOfficeDTOStandard(){
        return BranchOfficeDto.builder()
                .name("Vespucio")
                .code("11").build()
    }

    static CaseDataRequestDto getCaseDataDTOStandard(){
        return CaseDataRequestDto.builder()
                .idOriginalTicket("123456")
                .description("I need help")
                .creationDate("2020-01-01")
                .partnerTarget(3).build()
    }

    static CaseDataRequestDto getCaseDataDTOStandardWithoutPartnerTarget(){
        return CaseDataRequestDto.builder()
                .idOriginalTicket("123456")
                .description("I need help")
                .creationDate("2020-01-01")
                .build()
    }

    static CaseDataRequestDto getCaseDataStandard(){
        return CaseDataRequestDto.builder()
                .idOriginalTicket("123456")
                .description("I need help")
                .creationDate("2020-01-01")
                .partnerTarget(3).build()
    }

    static CaseData getModelCaseDataStandard(){
        return CaseData.builder()
                .idOriginalTicket("123456")
                .description("I need help")
                .creationDate("2020-01-01")
                .partnerTarget(3).build()
    }

    static CaseDataRequestDto getCaseDataDTONoPartnerInfo(){
        return CaseDataRequestDto.builder()
                .idOriginalTicket("123456")
                .description("I need help")
                .creationDate("2020-01-01").build()
    }

    static CreateCaseRequestDto getCreateCaseRequestDTOStandard(){
        return CreateCaseRequestDto.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTOStandard()).build()
    }

    static CreateCaseRequestDto getCreateCaseRequestDTOStandardWithoutPartnerTarget(){
        return CreateCaseRequestDto.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTOStandardWithoutPartnerTarget()).build()
    }

    static CreateCaseRequestDto getCreateCaseRequestDTOStandardWithEmptyAttachments(){
        return CreateCaseRequestDto.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachedFiles(new ArrayList<>())
                .caseData(getCaseDataDTOStandard()).build()
    }

    static AttachedFilesDto getAttachedFilesDTOStandard(){
        return AttachedFilesDto.builder()
                .name("file.png")
                .link("asdf/link/file.png")
                .build()
    }

    static AttachedFilesDto getAttachedFilesDTOStandardBase64(){
        return AttachedFilesDto.builder()
                .name("file.png")
                .base64("base64")
                .build()
    }

    static CreateCaseRequestDto getCreateCaseRequestDTOStandardWithAttachments(){
        return CreateCaseRequestDto.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachedFiles(List.of(getAttachedFilesDTOStandard()))
                .caseData(getCaseDataDTOStandard()).build()
    }

    static CreateCaseRequestDto getCreateCaseRequestDTOStandardWithoutAttachments(){
        return CreateCaseRequestDto.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTOStandard()).build()
    }

    static CasesModel getCasesModelStandard() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getModelCaseDataStandard()).build()
    }

    static CasesModel getCasesModelStandardWithAttachments() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachments(getAttachmentsDTOStandard())
                .caseData(getModelCaseDataStandard()).build()
    }

    static Case getCaseEntityStandard() {
        return Case.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getModelCaseDataStandard()).build()
    }

    static Case getCaseEntityWithAttachments() {
        return Case.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachments(getAttachmentsDTOStandard())
                .caseData(getModelCaseDataStandard()).build()
    }

    static Case getCaseEntityWithUpdatesAndAttachments() {
        return Case.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachments(AttachmentsDto.builder().build())
                .caseData(getModelCaseDataStandard()).build()
    }

    static CasesModel getCasesModelWithUpdates() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getModelCaseDataStandard())
                .updates(new ArrayList<>()).build()
    }

    static CasesModel getCasesModelNoAttachmentsStatus() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachments(AttachmentsDto.builder().status(Constants.WITHOUT_ATTACHMENTS).build())
                .caseData(getModelCaseDataStandard())
                .updates(new ArrayList<>()).build()
    }

    static CasesModel getCasesModelPendingAttachmentsStatus() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .attachments(AttachmentsDto.builder().status(Constants.WITHOUT_ATTACHMENTS).build())
                .caseData(getModelCaseDataStandard())
                .updates(new ArrayList<>()).build()
    }

    static FilesDto getFilesDTOStandard(){
        return FilesDto.builder()
                .result("No threat found")
                .link("1234-asdf-0/foto.jpg")
                .build()
    }

    static FilesDto getFilesDTOStandard2(){
        return FilesDto.builder()
                .result("No threat found")
                .link("1234-asdf-0/bart.jpg")
                .build()
    }

    static FilesDto getFilesDTOWithThreat(){
        return FilesDto.builder()
                .result("Threat found: DLP Analysis")
                .link("1234-asdf-0/bart.jpg")
                .build()
    }

    static AttachmentsDto getAttachmentsDTOStandard(){
        return AttachmentsDto.builder()
                .status("OK")
                .files(Arrays.asList(getFilesDTOStandard(), getFilesDTOStandard2()))
                .build()
    }

    static AttachmentsDto getAttachmentsWithThreat(){
        return AttachmentsDto.builder()
                .status("OK")
                .files(Arrays.asList(getFilesDTOWithThreat()))
                .build()
    }

    static CasesModel getCasesModelWithUpdatesAndAttachments() {
        return CasesModel.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getModelCaseDataStandard())
                .updates(new ArrayList<>())
                .attachments(getAttachmentsDTOStandard()).build()
    }

    static Case getCaseEntityWithUpdates() {
        return Case.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getModelCaseDataStandard())
                .updates(new ArrayList<>()).build()
    }

    static UpdatesDto getUpdatesDTOStandard(){
        return UpdatesDto.builder()
                .partner(1)
                .status(2)
                .comments("nothing")
                .businessExecutive("pepito")
                .messageToCustomer("cuenteme su problema")
                .messageFromCustomer("nezezito aiuda").build()
    }

    static UpdatesDto getUpdatesDTOStandardWithEmptyAttachments(){
        return UpdatesDto.builder()
                .partner(1)
                .status(2)
                .comments("nothing")
                .businessExecutive("pepito")
                .messageToCustomer("cuenteme su problema")
                .messageFromCustomer("nezezito aiuda")
                .attachedFiles(new ArrayList<>()).build()
    }

    static UpdatesDto getUpdatesDTOStandardWithAttachments(){
        return UpdatesDto.builder()
                .partner(1)
                .status(2)
                .comments("nothing")
                .businessExecutive("pepito")
                .messageToCustomer("cuenteme su problema")
                .messageFromCustomer("nezezito aiuda")
                .attachedFiles(List.of(getAttachedFilesDTOStandard())).build()
    }

    static CaseInfoDTO getCaseInfoDTOStandard(){
        return CaseInfoDTO.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTOStandard())
                .updates(new ArrayList<>()).build()
    }

    static CaseInfoDTO getCaseInfoDTOWithoutPartnerInfo(){
        return CaseInfoDTO.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTONoPartnerInfo())
                .updates(new ArrayList<>()).build()
    }

    static UpdateCase getUpdateCaseDTOStandard(){
        return UpdateCase.builder()
                .partner(1)
                .status(2)
                .comments("nothing")
                .businessExecutive("pepito")
                .messageFromCustomer("oli")
                .messageToCustomer("ola ke ase").build()
    }

    static CaseInfoDTO getCaseInfoDTOWithUpdates(){
        return CaseInfoDTO.builder()
                .customerData(getCustomerDataDTOStandard())
                .caseClasification(getCaseClasificationDTOStandard())
                .businessExecutive(getBusinessExecutiveDTOStandard())
                .branchOffice(getBranchOfficeDTOStandard())
                .caseData(getCaseDataDTONoPartnerInfo())
                .updates(Arrays.asList(getUpdateCaseDTOStandard())).build()
    }

    static Case hiddenCaseEntityTest(){
        Case caseFull = new Case()
        caseFull.setId("asdf")
        caseFull.setCustomerData(getCustomerDataDTOStandard())
        caseFull.setCaseClasification(getCaseClasificationDTOStandard())
        caseFull.setBusinessExecutive(getBusinessExecutiveDTOStandard())
        caseFull.setBranchOffice(getBranchOfficeDTOStandard())
        caseFull.setCaseData(getModelCaseDataStandard())
        caseFull.setUpdates(Arrays.asList(getUpdateCaseDTOStandard()))
        caseFull.toString()
        caseFull.hashCode()
        caseFull.equals(getUpdatesDTOStandard())
        Case.builder().toString()
        return caseFull
    }

    static UpdateCase hiddenUpdateCaseEntityTest(){
        UpdateCase caseUpdateFull = new UpdateCase()
        caseUpdateFull.setPartner(1)
        caseUpdateFull.setStatus(2)
        caseUpdateFull.setComments("nothing")
        caseUpdateFull.setBusinessExecutive("pepito")
        caseUpdateFull.setMessageFromCustomer("I need help")
        caseUpdateFull.setMessageToCustomer("I will help you")
        caseUpdateFull.setDateTime("2020-01-01")
        caseUpdateFull.toString()
        caseUpdateFull.hashCode()
        caseUpdateFull.equals(Object)
        caseUpdateFull.getDateTime()
        caseUpdateFull.getStatus()
        caseUpdateFull.getComments()
        caseUpdateFull.getBusinessExecutive()
        caseUpdateFull.getMessageFromCustomer()
        caseUpdateFull.getMessageToCustomer()
        UpdateCase.builder().toString()
        return caseUpdateFull
    }

    static DerivativeValuesDto getDerivativedValuesDTOStandard(){
        return new DerivativeValuesDto("id",1)
    }

    static ImageAnalysisReadResultDto getImageAnalysisReadResultDtoStandard(){
        return new ImageAnalysisReadResultDto("id","1_1")
    }

    static ImageAnalysisReadResultDto getImageAnalysisReadResultDtoNoIndex(){
        return new ImageAnalysisReadResultDto("id",null)
    }

    static ComputerVisionImageAnalysisResponseDto getComputerVisionImageAnalysisResponseDtoStandard(){
        return ComputerVisionImageAnalysisResponseDto.builder().readResult(getImageAnalysisReadResultDtoStandard()).build()
    }

    static ComputerVisionImageAnalysisResponseDto getComputerVisionImageAnalysisResponseDtoPartial(){
        return ComputerVisionImageAnalysisResponseDto.builder().readResult(getImageAnalysisReadResultDtoNoIndex()).build()
    }

}
