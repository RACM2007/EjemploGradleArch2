package cl.bci.ecodig.customersupport.cases.producer

import cl.bci.ecodig.customersupport.cases.configuration.ParametersConfiguration
import cl.bci.ecodig.customersupport.cases.repository.producer.CasesMessageSenderImpl
import cl.bci.ecodig.customersupport.cases.helper.CasesFakeData

import cl.bci.ecodig.servicebus.base.service.MessageSender
import spock.lang.Specification

class CasesMessageSenderSpec extends Specification {

    MessageSender serviceBusSender = Stub()

    ParametersConfiguration parametersConfiguration = Stub()

    CasesMessageSenderImpl messageSender

    def setup() {
        messageSender = new CasesMessageSenderImpl(serviceBusSender, parametersConfiguration)
    }

    def "sendCreateCaseMessage OK"(){

        when:
        messageSender.sendCreateCaseMessage(requestDtos)
        then:
        notThrown()
        where:
        requestDtos << [CasesFakeData.getCaseInfoDTOStandard(), CasesFakeData.getCaseInfoDTOWithoutPartnerInfo()]

    }

    def "sendUpdateCaseMessage OK"(){

        when:
        messageSender.sendUpdateCaseMessage(requestDtos)
        then:
        notThrown()
        where:
        requestDtos << [CasesFakeData.getCaseInfoDTOStandard(), CasesFakeData.getCaseInfoDTOWithUpdates()]

    }
}
