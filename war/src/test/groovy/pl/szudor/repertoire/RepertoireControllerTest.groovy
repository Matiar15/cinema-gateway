package pl.szudor.repertoire

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaPayload
import pl.szudor.cinema.State
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RepertoireController.class)
class RepertoireControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private RepertoireService repertoireService

    private final String ENDPOINT = "/repertoire"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "save repertoire"() {
        given:
        def cinema = new CinemaPayload(1, "", "", "asd@wp.pl", "+48-123-123-123", "00-000", "", "1234567890", LocalDate.of(2023, 3, 3), State.YES, LocalDateTime.now())
        def cinemaEntity = new Cinema(1, "", "", "", "", "", "", "", LocalDate.of(2023, 3, 3), State.YES)
        def repertoire = new RepertoireDto(null, LocalDate.of(2023, 3, 3), cinema, null)
        def repertoireAsJson = objectMapper.writeValueAsString(repertoire)


        when:
        def result = mvc.perform(post("$ENDPOINT/cinema/1")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(repertoireAsJson))

        then:
        1 * repertoireService.saveRepertoire(repertoire, 1) >> new Repertoire(1, LocalDate.of(2023, 3, 3), cinemaEntity)
        result.andExpect(status().isCreated())

        0 * _
    }

    def "save repertoire with thrown exception"() {
        given:
        def cinema = new CinemaPayload(1, "", "", "asd@wp.pl", "+48-123-123-123", "00-000", "", "1234567890", LocalDate.of(2023, 3, 3), State.YES, LocalDateTime.now())
        def repertoire = new RepertoireDto(null, LocalDate.of(2023, 3, 3), cinema, null)
        def repertoireAsJson = objectMapper.writeValueAsString(repertoire)


        when:
        def result = mvc.perform(post("$ENDPOINT/cinema/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(repertoireAsJson))

        then:
        1 * repertoireService.saveRepertoire(repertoire, 1) >> { throw new CinemaNotExistsException(1) }
        result.andExpect(status().is4xxClientError())

        0 * _
    }

    def "get repertoires"() {
        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5"))
        def pageable = Mock(Pageable)

        then:
        1 * repertoireService.getAll(PageRequest.of(0, 5)) >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "delete repertoire"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * repertoireService.deleteRepertoire(1)
        result.andExpect(status().isNoContent())

        and:
        0 * _
    }

    def "delete repertoire with thrown exception"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * repertoireService.deleteRepertoire(1) >> { throw new RepertoireNotExistsException(1) }
        result.andExpect(status().is4xxClientError())

        and:
        0 * _
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
