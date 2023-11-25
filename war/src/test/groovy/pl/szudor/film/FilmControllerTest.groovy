/*
package pl.szudor.film

import com.fasterxml.jackson.databind.ObjectMapper
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
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.State
import pl.szudor.exception.FilmNotExistsException
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(FilmController.class)
class FilmControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private FilmService filmService

    private final String ENDPOINT = "/film"

    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "save film"() {
        given:
        def film = new FilmDto(1, LocalTime.of(15, 15), null, "", Pegi.SEVEN, 1, LocalDate.of(2023, 3, 3), "PL", null,  LocalDateTime.now())
        def filmAsJson = objectMapper.writeValueAsString(film)
        def repertoireEntity = new Repertoire(1, LocalDate.of(2023, 3, 3), new Cinema(1, "", "", "", "", "", "", "", LocalDate.of(2023, 3, 3), State.YES))
        def roomEntity = new Room(12, null)
        def filmEntity = new Film(1, LocalTime.of(15, 15), repertoireEntity, "", Pegi.SEVEN, 1, LocalDate.of(2023, 3, 3), "PL", roomEntity)
        when:
        def result = mvc.perform(post("$ENDPOINT/repertoire/1/room/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmAsJson))

        then:
        1 * filmService.saveFilm(film, 1, 1) >> filmEntity
        result.andExpect(status().isCreated())

        and:
        0 * _
    }

    def "save film with thrown repertoire exception"() {
        given:
        def film = new FilmDto(1, LocalTime.of(15, 15), null, "", Pegi.SEVEN, 1, LocalDate.of(2023, 3, 3), "PL", null, LocalDateTime.now())
        def filmAsJson = objectMapper.writeValueAsString(film)

        when:
        def result = mvc.perform(post("$ENDPOINT/repertoire/1/room/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmAsJson))

        then:
        1 * filmService.saveFilm(film, 1, 1) >> { throw new RepertoireNotExistsException(1) }
        result.andExpect(status().is4xxClientError())

        and:
        0 * _
    }

    def "save film with thrown room exception"() {
        given:
        def film = new FilmDto(1, LocalTime.of(15, 15), null, "", Pegi.SEVEN, 1, LocalDate.of(2023, 3, 3), "PL", null,  LocalDateTime.now())
        def filmAsJson = objectMapper.writeValueAsString(film)

        when:
        def result = mvc.perform(post("$ENDPOINT/repertoire/1/room/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmAsJson))

        then:
        1 * filmService.saveFilm(film, 1, 1) >> { throw new RoomNotExistsException(1) }
        result.andExpect(status().is4xxClientError())

        and:
        0 * _
    }

    def "get films"() {
        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5"))

        then:
        1 * filmService.getAll(PageRequest.of(0, 5)) >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "delete film"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * filmService.deleteFilm(1)
        result.andExpect(status().isNoContent())

        and:
        0 * _
    }

    def "delete film with thrown exception"() {
        when:
        def result = mvc.perform(delete("$ENDPOINT/1"))

        then:
        1 * filmService.deleteFilm(1) >> { throw new FilmNotExistsException(1) }
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
*/
