package cl.bci.ecodig.customersupport.cases.util

import cl.bci.ecodig.customersupport.cases.util.CasesUtil
import spock.lang.Specification
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import cl.bci.ecodig.core.spring.exception.ValidationException

class CasesUtilSpec extends Specification {

    def "should throw ValidationException when comment is empty and status requires mandatory comment"() {
        given:
        def status = 1
        def listStatusWithMandatoryComment = "1,2,3"
        def commentValue = ""

        when:
        CasesUtil.isMandatoryComment(status, listStatusWithMandatoryComment, commentValue)

        then:
        thrown(ValidationException)
    }

    def "should not throw ValidationException when comment is not empty and status requires mandatory comment"() {
        given:
        def status = 1
        def listStatusWithMandatoryComment = "1,2,3"
        def commentValue = "This is a comment"

        when:
        CasesUtil.isMandatoryComment(status, listStatusWithMandatoryComment, commentValue)

        then:
        notThrown(ValidationException)
    }

    def "should not throw ValidationException when comment is empty and status does not require mandatory comment"() {
        given:
        def status = 4
        def listStatusWithMandatoryComment = "1,2,3"
        def commentValue = ""

        when:
        CasesUtil.isMandatoryComment(status, listStatusWithMandatoryComment, commentValue)

        then:
        notThrown(ValidationException)
    }

    def "should not throw ValidationException when comment is not empty and status does not require mandatory comment"() {
        given:
        def status = 4
        def listStatusWithMandatoryComment = "1,2,3"
        def commentValue = "This is a comment"

        when:
        CasesUtil.isMandatoryComment(status, listStatusWithMandatoryComment, commentValue)

        then:
        notThrown(ValidationException)
    }

    def "should return current date in ISO 8601 format"() {
        given:
        def now = LocalDateTime.now(ZoneOffset.UTC)
        when:
        def actual = CasesUtil.currentDateInISO8601()
        then:
        actual.startsWith(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }

    def "checkDatesFilter OK when no dates values are retrieved"() {
        given:
        def defaultSearchDays = 90

        when:
        def result = CasesUtil.checkDatesFilter(startDates, endDates, defaultSearchDays)

        then:
        result

        where:
        startDates << [null, "2022-01-01", null]
        endDates << [null, null, "2022-01-01"]
    }

    def "checkDatesFilter OK with dateRanges"() {
        given:
        def dateStart = "2022-01-01"
        def dateEnd = "2022-02-01"
        def defaultSearchDays = 7

        when:
        def result = CasesUtil.checkDatesFilter(dateStart, dateEnd, defaultSearchDays)

        then:
        result
    }

    def "should throw ValidationException when start date is after end date"() {
        given:
        def dateStart = "2022-01-31"
        def dateEnd = "2022-01-01"
        def defaultSearchDays = 7

        when:
        CasesUtil.checkDatesFilter(dateStart, dateEnd, defaultSearchDays)

        then:
        thrown(ValidationException)
    }

    def "should throw ValidationException when end date is after today"() {
        given:
        def dateStart = "2022-01-01"
        def dateEnd = LocalDate.now().plusDays(1).toString()
        def defaultSearchDays = 7

        when:
        CasesUtil.checkDatesFilter(dateStart, dateEnd, defaultSearchDays)

        then:
        thrown(ValidationException)
    }

    def "should return empty date if value is null"() {
        when:
        CasesUtil.dateInISO8601(null)

        then:
        notThrown()
    }
}