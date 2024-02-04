package pl.szudor.cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.ControllerTestConfig
import pl.szudor.NoSecWebMvcTest
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(CinemaControllerTestConfig)
@NoSecWebMvcTest(CinemaController)
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
        it.active = Active.NO
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

        then: "service call was made"
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

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "get cinemas should validate all good"() {
        when:
        def result = mvc.perform(get("$URL?page=0&size=5"))

        then: "service call was made"
        1 * cinemaService.fetchByFilter(
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

        and: "result was 2xx"
        result.andExpect(status().isOk())
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * cinemaService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "patch cinema should validate all good"() {
        given:
        def content = """
        |{
        |   "active": "NO"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "service call was made"
        1 * cinemaService.updateState(22, Active.NO) >> savedEntity

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }
}

@Import([ControllerTestConfig])
class CinemaControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    CinemaService cinemaService() {
        return detachedMockFactory.Mock(CinemaService.class)
    }
}