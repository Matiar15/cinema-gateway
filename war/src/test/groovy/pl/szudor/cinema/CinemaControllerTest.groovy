package pl.szudor.cinema


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.exception.CinemaNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CinemaController.class)
class CinemaControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private CinemaService cinemaService

    private final String URL = "/cinema"

    def savedEntity = new Cinema().tap {
        it.id = 1
        it.name = "test"
        it.address = "test"
        it.email = "xdddd@wp.pl"
        it.phoneNumber = "+48-123-456-789"
        it.postalCode = "99-999"
        it.director = "test"
        it.nipCode = "1234567890"
        it.buildDate = LocalDate.of(2023, 3, 3)
        it.state = State.NO
    }

    def "create cinema all good"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then:
        1 * cinemaService.saveCinema(
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2023, 3, 3)
        ) >> savedEntity

        and:
        result.andExpect(status().is2xxSuccessful())

        and:
        0 * _
    }

    def "create cinema null name"() {
        given:
        def content = """
        |{
        |   "name":          null,
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null address"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":       null,
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null email"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":         null,
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null phone number"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":   null,
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null postal code"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":    null,
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null director"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":      null,
        |   "nipCode":      "1234567890",
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "create cinema null nip code"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":       null,
        |   "buildDate":    "2023-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }


    def "create cinema null created date"() {
        given:
        def content = """
        |{
        |   "name":         "test",
        |   "address":      "test",
        |   "email":        "xdddd@wp.pl",
        |   "phoneNumber":  "+48-123-456-789",
        |   "postalCode":   "99-999",
        |   "director":     "test",
        |   "nipCode":      "1234567890",
        |   "buildDate":     null
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())

        and:
        0 * _
    }

    def "get cinemas should validate all good"() {
        when:
        def result = mvc.perform(get("$URL?page=0&size=5"))
        then:
        1 * cinemaService.getCinemas(
                PageRequest.of(0, 5),
                new CinemaFilter(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        ) >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "patch cinema null state"() {
        given:
        def content = """
        |{
        |   "state": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())
    }

    def "patch cinema negative id"() {
        given:
        def content = """
        |{
        |   "state": "YES"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/-2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then:
        0 * cinemaService._
        result.andExpect(status().isBadRequest())
    }

    def "patch cinema"() {
        given:
        def content = """
        |{
        |   "state": "NO"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then:
        1 * cinemaService.updateState(22, State.NO) >> savedEntity
        result.andExpect(status().is2xxSuccessful())
    }

    def "patch cinema without found cinema"() {
        given:
        def content = """
        |{
        |   "state": "NO"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then:
        1 * cinemaService.updateState(22, State.NO) >> { throw new CinemaNotExistsException(22) }
        result.andExpect(status().isNotFound())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        CinemaService cinemaService() {
            return detachedMockFactory.Mock(CinemaService)
        }

    }
}