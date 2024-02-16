package pl.szudor.repertoire

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.ControllerTestConfig
import pl.szudor.NoSecWebMvcTest
import pl.szudor.cinema.Cinema
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(RepertoireControllerTestConfig)
@NoSecWebMvcTest(RepertoireController)
class RepertoireControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private RepertoireService repertoireService

    private final String ENDPOINT = "/cinema/1/repertoire"

    def played = LocalDate.of(2099, 3, 3)

    def cin = new Cinema().tap {
        it.id = 1
        it.name = "test"
        it.address = "test"
        it.email = "xdddd@wp.pl"
        it.phoneNumber = "+48-123-456-789"
        it.postalCode = "99-999"
        it.director = "test"
        it.nipCode = "1234567890"
        it.buildDate = LocalDate.of(2023, 3, 3)
        it.active = false
    }

    def rep = new Repertoire().tap {
        it.id = 1
        it.playedAt = played
        it.cinema = cin
        it.createdAt = LocalDateTime.now()
    }

    def "save repertoire all good"() {
        given:
        def content = """
        |{
        |   "playedAt": "2099-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * repertoireService.createRepertoire(1, played) >> rep

        and: "result was 2xx"
        result.andExpect(status().isCreated())
    }

    def "save repertoire with date after current date"() {
        given:
        def content = """
        |{
        |   "playedAt": "1000-12-31"
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("Passed date cannot be older than current date")
    }

    def "save repertoire no body"() {
        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("request body is missing")
    }

    def "save repertoire null played at"() {
        given:
        def content = """
        |{
        |   "playedAt": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "patch repertoire all good"() {
        given:
        def content = """
        |{
        |   "playedAt": "2099-03-03"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * repertoireService.patchRepertoire(1, played) >> rep

        and: "result was 2xx"
        result.andExpect(status().isOk())
    }

    def "patch repertoire with date after current date"() {
        given:
        def content = """
        |{
        |   "playedAt": "1000-12-31"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("Passed date cannot be older than current date")
    }

    def "patch repertoire no body"() {
        when:
        def result = mvc.perform(patch("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("request body is missing")
    }

    def "patch repertoire null played at"() {
        given:
        def content = """
        |{
        |   "playedAt": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * repertoireService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "get repertoires"() {
        given:
        def filter = new RepertoireFilter(null)

        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5"))

        then: "service call was made"
        1 * repertoireService.fetchByFilter(1, filter, PageRequest.of(0, 5)) >> _

        and: "result was 2xx"
        result.andExpect(status().isOk())
    }
}

@Import([ControllerTestConfig])
class RepertoireControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    RepertoireService repertoireService() {
        return detachedMockFactory.Mock(RepertoireService.class)
    }
}