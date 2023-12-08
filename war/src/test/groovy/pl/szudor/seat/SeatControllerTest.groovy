package pl.szudor.seat


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.room.Room
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SeatController.class)
class SeatControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private SeatService seatService

    private final int correct_roomId = 1
    private final int wrong_roomId = -1
    private final String CORRECT_URL = "/room/$correct_roomId/seat"
    private final String WRONG_URL = "/room/$wrong_roomId/seat"

    def entityRoom = new Room().tap {
        it.id = 1
        it.number = 12
        it.cinema = null
    }
    def seat = new Seat().tap {
        it.id = 1
        it.number = 12
        it.occupied = Occupied.YES
        it.room = entityRoom
    }

    def "create seat validated all good"() {
        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"number\": 1 }")
        )

        then: "service call was made"
        1 * seatService.saveSeat(1, 1)
                >> seat

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }

    def "create seat should validate negative room id"() {
        when:
        def result = mvc.perform(post("$WRONG_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"number\": 1 }")
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "create seat should validate null number"() {
        given:
        def content = """
        |{
        |   "number": null
        |}""".stripMargin()


        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "create seat should validate negative number"() {
        given:
        def content = """
        |{
        |   "number": -1
        |}""".stripMargin()


        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "create seat should validate null body"() {
        when:
        def result = mvc.perform(post("$CORRECT_URL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "patch seat validated all good"() {
        given:
        def content = """
        |{
        |   "occupied": "YES"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$CORRECT_URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "service call was made"
        1 * seatService.patchSeat(1, Occupied.YES)
                >> seat

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }

    def "patch seat should validate negative room id"() {
        given:
        def content = """
        |{
        |   "occupied": "YES"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$WRONG_URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "patch seat should validate negative seat id"() {
        given:
        def content = """
        |{
        |   "occupied": "YES"
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$CORRECT_URL/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "patch seat validated null occupied"() {
        given:
        def content = """
        |{
        |   "occupied": null
        |}""".stripMargin()

        when:
        def result = mvc.perform(patch("$CORRECT_URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "patch seat validated without body"() {
        when:
        def result = mvc.perform(patch("$CORRECT_URL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "delete seating validated all good"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/1"))

        then: "service call was made"
        1 * seatService.deleteSeat(1)

        and: "result was 2xx"
        result.andExpect(status().is2xxSuccessful())
    }

    def "delete seating should validate negative room id"() {
        when:
        def result = mvc.perform(delete("$WRONG_URL/1"))

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    def "delete seating should validate negative seat id"() {
        when:
        def result = mvc.perform(delete("$CORRECT_URL/-1"))

        then: "no service calls were made"
        0 * seatService._

        and: "result was bad request"
        result.andExpect(status().isBadRequest())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        SeatService seatService() {
            return detachedMockFactory.Mock(SeatService.class)
        }
    }
}
