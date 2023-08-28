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
import pl.szudor.cinema.CinemaState
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.Film
import pl.szudor.film.FilmController
import pl.szudor.film.FilmDto
import pl.szudor.film.FilmService
import pl.szudor.repertoire.Repertoire
import pl.szudor.repertoire.RepertoireDto
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(FilmController.class)
class FilmControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private FilmService filmService

    private final String ENDPOINT = "/films"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "test save film"() {
        given:
        def repertoire = new RepertoireDto(1, null, null, null)
        def film = new FilmDto(1, LocalTime.of(15, 15), 10, repertoire, null)
        def filmAsJson = objectMapper.writeValueAsString(film)
        def repertoireEntity = new Repertoire(1, LocalDate.of(2023, 3, 3), new Cinema(1, "", "", "", "", "", "", LocalDate.of(2023, 3, 3), CinemaState.ON))
        def filmEntity = new Film(1, LocalTime.of(15, 15), 10, repertoireEntity)

        when:
        def result = mvc.perform(post("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmAsJson))

        then:
        1 * filmService.saveFilm(film, 1) >> filmEntity
        result.andExpect(status().isCreated())

        and:
        0 * _
    }

    def "test save film with thrown exception"() {
        given:
        def film = new FilmDto(1, LocalTime.of(15, 15), 10, null, null)
        def filmAsJson = objectMapper.writeValueAsString(film)

        when:
        def result = mvc.perform(post("$ENDPOINT/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmAsJson))

        then:
        1 * filmService.saveFilm(film, 1) >> { throw new RepertoireNotExistsException("Repertoire under id: 1 does not exist.") }
        result.andExpect(status().is4xxClientError())

        and:
        0 * _
    }

    def "test get films"() {
        when:
        def result = mvc.perform(get("$ENDPOINT"))

        then:
        1 * filmService.getFilms() >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "test delete film"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * filmService.deleteFilm(1)
        result.andExpect(status().isNoContent())

        and:
        0 * _
    }

    def "test delete film with thrown exception"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * filmService.deleteFilm(1) >> { throw new FilmNotExistsException("Film under id: 1 does not exist.") }
        result.andExpect(status().is4xxClientError())

        and:
        0 * _
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        FilmService filmService() {
            return detachedMockFactory.Mock(FilmService.class)
        }
    }
}
