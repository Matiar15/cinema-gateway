package pl.szudor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaState
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.repertoire.RepertoireService
import spock.lang.Specification
import pl.szudor.repertoire.RepertoireController
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

    private final String ENDPOINT = "/repertoires"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "test save repertoire"() {
        given:
        def cinema = new CinemaDto(1, "", "", "asd@wp.pl", "+48-123-123-123", "00-000", "", "1234567890", LocalDate.of(2023, 3, 3), CinemaState.ON, LocalDateTime.now())
        def cinemaEntity = new Cinema(1, "", "", "", "", "", "", "", LocalDate.of(2023, 3, 3), CinemaState.ON)
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

    def "test save repertoire with thrown exception"() {
        given:
        def cinema = new CinemaDto(1, "", "", "asd@wp.pl", "+48-123-123-123", "00-000", "", "1234567890", LocalDate.of(2023, 3, 3), CinemaState.ON, LocalDateTime.now())
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

    def "test get repertoires"() {
        when:
        def result = mvc.perform(get("$ENDPOINT"))

        then:
        1 * repertoireService.getRepertoires() >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "test delete repertoire"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * repertoireService.deleteRepertoire(1)
        result.andExpect(status().isNoContent())

        and:
        0 * _
    }

    def "test delete repertoire with thrown exception"() {
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
