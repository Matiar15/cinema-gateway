package pl.szudor.repertoire

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
import pl.szudor.cinema.Active
import pl.szudor.cinema.Cinema
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RepertoireController.class)
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
        it.active = Active.NO
    }

    def rep = new Repertoire().tap {
        it.id = 1
        it.playedAt = played
        it.cinema = cin
        it.createdAt = LocalDateTime.now()
    }

    def "save repertoire"() {
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

        then:
        1 * repertoireService.createRepertoire(1, played) >> rep

        and:
        result.andExpect(status().isCreated())
    }

    def "save repertoire with not found cinema"() {
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

        then:
        1 * repertoireService.createRepertoire(1, played) >> { throw new CinemaNotExistsException(1) }

        and:
        result.andExpect(status().isNotFound())
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

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "save repertoire no body"() {
        when:
        def result = mvc.perform(post("$ENDPOINT")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
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

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
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

        then:
        1 * repertoireService.patchRepertoire(1, played) >> rep

        and:
        result.andExpect(status().isOk())
    }

    def "patch repertoire with not found repertoire"() {
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

        then:
        1 * repertoireService.patchRepertoire(1, played) >> { throw new RepertoireNotExistsException(1) }

        and:
        result.andExpect(status().isNotFound())
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

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "patch repertoire no body"() {
        when:
        def result = mvc.perform(patch("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
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

        then:
        0 * repertoireService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "get repertoires"() {
        given:
        def filter = new RepertoireFilter(null)

        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5"))

        then:
        1 * repertoireService.fetchByFilter(1, filter, PageRequest.of(0, 5)) >> _

        and:
        result.andExpect(status().isOk())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class RepertoireControllerTestConfig {
        private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        RepertoireService repertoireService() {
            return detachedMockFactory.Mock(RepertoireService.class)
        }
    }
}
