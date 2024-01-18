package pl.szudor.seat.reserved

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
import pl.szudor.event.Event
import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import pl.szudor.seat.Seat
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ReservedSeatController.class)
class ReservedSeatControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private ReservedSeatService reservedSeatService

    private final String ENDPOINT = "/event/1/seat"

    def room_ = new Room().tap{ id = 4}

    def event_ = new Event().tap {
        id = 1
        film = new Film().tap {id = 1}
        playedAt = LocalTime.of(22, 35)
        repertoire = new Repertoire().tap {id = 3}
        room = room_
    }

    def seat_ = new Seat().tap {
        id = 1
        room = room_
        number = 3
    }

    def "should validate negative id"() {
        when:
        def result = mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"id\": -1 }")
        )

        then:
        0 * reservedSeatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must be greater than 0")
    }

    def "should validate null id"() {
        when:
        def result = mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"id\": null }")
        )

        then:
        0 * reservedSeatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("must not be null")
    }

    def "should validate no body"() {
        when:
        def result = mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        0 * reservedSeatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())

        and:
        result.andReturn().resolvedException.asString().contains("request body is missing")
    }

    def "should create reserved seat"() {
        given:
        def reservedSeat = new ReservedSeat().tap {
            id = seat_.id
            event = event_
            seat = seat_
        }

        when:
        def result = mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"id\": 1 }")
        )

        then: "service call was made"
        1 * reservedSeatService.create(1, 1)
            >> reservedSeat

        and: "result was created"
        result.andExpect(status().isCreated())
    }

    def "should delete reserved seat"() {
        when:
        def result = mvc.perform(delete(ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * reservedSeatService.delete(1)

        and: "result was no content"
        result.andExpect(status().isNoContent())
    }

    def "should fetch all reserved seats"() {
        when:
        def result = mvc.perform(get("$ENDPOINT?page=0&size=5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "service call was made"
        1 * reservedSeatService.fetch(1, PageRequest.of(0, 5)) >> _

        and: "result was ok"
        result.andExpect(status().isOk())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        ReservedSeatService reservedSeatService() {
            return detachedMockFactory.Mock(ReservedSeatService.class)
        }
    }
}
