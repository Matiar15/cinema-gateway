package pl.szudor.event


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.ControllerTestConfig
import pl.szudor.NoSecWebMvcTest
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@NoSecWebMvcTest(EventController.class)
@Import(EventControllerTestConfig)
class EventControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    EventService eventService

    private final String URL = "/repertoire/1/film/2/room/3/event"
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
        it.id = 1
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

        then: "service call was made"
        1 * eventService.create(1, 2, 3, played_at)
            >> event

        and: "result was 2xx"
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

        then: "no service calls were made"
        0 * eventService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
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

        then: "no service calls were made"
        0 * eventService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "create event null body"() {
        when:
        def result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * eventService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("request body is missing")
    }

    def "should patch event all good"() {
        given:
        def content = """
        |{
        |   "playedAt": "23:35"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * eventService.patch(1, 1, 3, LocalTime.of(23, 35))

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }

    def "patch event null old played at"() {
        given:
        def content = """
        |{
        |   "playedAt": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * eventService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and: "resolved exception"
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "should delete event all good"() {
        when:
        def result = mvc.perform(delete("$URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * eventService.delete(1)

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }
}

@Import([ControllerTestConfig])
class EventControllerTestConfig {
    def detachedMockFactory = new DetachedMockFactory()


    @Bean
    EventService eventService() {
        return detachedMockFactory.Mock(EventService.class)
    }
}