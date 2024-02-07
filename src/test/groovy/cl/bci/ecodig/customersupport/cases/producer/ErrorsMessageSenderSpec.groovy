package cl.bci.ecodig.customersupport.cases.producer

import cl.bci.ecodig.customersupport.cases.controller.dto.event.ErrorInfoDTO
import cl.bci.ecodig.customersupport.cases.repository.producer.ErrorsMessageSenderImpl
import cl.bci.ecodig.servicebus.base.service.MessageSender
import spock.lang.Specification

class ErrorsMessageSenderSpec extends Specification {

    MessageSender serviceBusSender2 = Stub()

    ErrorsMessageSenderImpl errorsMessageSender

    def setup() {
        errorsMessageSender = new ErrorsMessageSenderImpl(serviceBusSender2)
    }

    def "sendCreateCaseMessage OK"() {

        when:
        errorsMessageSender.sendErrorMessage(ErrorInfoDTO.builder().build())
        then:
        notThrown()

    }

}