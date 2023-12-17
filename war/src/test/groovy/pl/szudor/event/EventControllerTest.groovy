package pl.szudor.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.cinema.Active
import pl.szudor.cinema.Cinema
import pl.szudor.film.Film
import pl.szudor.film.Pegi
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(EventController.class)
class EventControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    EventService eventService

    private final String URL = "/repertoire/1/film/2/room/3/event"
    private final String PATCH_DELETE_URL = URL + "/22:35"
    def played_at = LocalTime.of(22, 35)

    def cinema_ = new Cinema().tap {
        it.id = 1
        it.name = ""
        it.address = ""
        it.nipCode = ""
        it.email = ""
        it.phoneNumber = ""
        it.postalCode = ""
        it.director = ""
        it.buildDate = LocalDate.of(2019, 3, 22)
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
        it.active = Active.NO
    }

    def played = LocalDate.of(2023, 3, 3)

    def repertoire_ = new Repertoire().tap {
        it.id = 1
        it.playedAt = played
        it.cinema = cinema_
        it.createdAt = LocalDateTime.of(2023, 3, 3, 3, 3)
    }

    def time = LocalTime.of(23, 3)
    def peg = Pegi.SEVEN
    def date = LocalDate.of(2023, 3, 3)
    def dateTime = LocalDateTime.of(date, time)

    def film_ = new Film().tap {
        it.id = 2
        it.createdAt = dateTime
        it.duration = 12
        it.originalLanguage = ""
        it.pegi = peg
        it.title = ""
        it.releaseDate = date
    }

    def room_ = new Room().tap {
        it.id = 3
        it.number = 3
        it.cinema = cinema_
    }

    def event = new Event().tap {
        it.playedAt = played_at
        it.room = room_
        it.film = film_
        it.repertoire = repertoire_
        it.id = new EventKey().tap {
            it.filmId = film_.id
            it.repertoireId = repertoire_.id
            it.roomId = room_.id
        }
    }

    def "should create event all good"() {
        given:
        def content = """
        |{
        |   "playedAt": "22:35"
        |}""".stripMargin()
        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        1 * eventService.create(1, 2, 3, played_at)
            >> event

        and:
        result.andExpect(status().isCreated())
    }

    def "create event null playedAt"() {
        given:
        def content = """
        |{
        |   "playedAt": null
        |}""".stripMargin()
        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * eventService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create event missing playedAt"() {
        given:
        def content = """
        |{
        |}""".stripMargin()
        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * eventService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "create event null body"() {
        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * eventService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "should patch event all good"() {
        given:
        def content = """
        |{
        |   "playedAt": "23:35"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(PATCH_DELETE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        1 * eventService.patch(1, 2, 3, played_at,  played_at.plusHours(1))

        and:
        result.andExpect(status().is2xxSuccessful())
    }

    def "patch event null old played at"() {
        given:
        def content = """
        |{
        |   "playedAt": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(PATCH_DELETE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * eventService._

        and:
        result.andExpect(status().isBadRequest())
    }

    def "patch event missing played at"() {
        given:
        def content = """
        |{
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch(PATCH_DELETE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        0 * eventService._

        and:
        result.andExpect(status().isBadRequest())
    }


    def "should delete event all good"() {
        when:
        def result = mvc.perform(delete(PATCH_DELETE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then:
        1 * eventService.delete(1, 2, 3, played_at)

        and:
        result.andExpect(status().is2xxSuccessful())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        EventService eventService() {
            return detachedMockFactory.Mock(EventService)
        }

    }
}
