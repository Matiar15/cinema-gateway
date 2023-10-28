package pl.szudor.cinema

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
import pl.szudor.exception.CinemaNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CinemaController.class)
class CinemaControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Autowired
    private CinemaService cinemaService


    private ObjectMapper objectMapper = new ObjectMapper()


    def setup() {
        objectMapper.findAndRegisterModules()
    }

    def "save cinema"() {
        given:
        def cinema = new CinemaDto(
                1,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF,
                null
        )
        def entity = new Cinema(1,
                "test",
                "test",
                "xdddd@wp.pl",
                "123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF)
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinema")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        1 * cinemaService.saveCinema(cinema) >> entity
        result.andExpect(status().is2xxSuccessful())

        and:
        0 * _
    }

    def "get cinema"() {
        when:
        def result = mvc.perform(get("/cinema?page=0&size=5"))

        then:
        1 * cinemaService.getCinemas(PageRequest.of(0, 5)) >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "update status cinema"() {
        given:
        def payload = new CinemaPayload(CinemaState.OFF)
        def updateContent = objectMapper.writeValueAsString(payload)
        def entity = new Cinema(1,
                "test",
                "test",
                "xdddd@wp.pl",
                "123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF
        )

        when:
        def result = mvc.perform(put("/cinema/state/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        1 * cinemaService.updateState(22, payload) >> entity
        result.andExpect(status().is2xxSuccessful())
    }

    def "update cinema status without found cinema"() {
        given:
        def payload = new CinemaPayload(CinemaState.OFF)
        def updateContent = objectMapper.writeValueAsString(payload)


        when:
        def result = mvc.perform(put("/cinema/state/22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent)
        )

        then:
        1 * cinemaService.updateState(22, payload) >> { throw new CinemaNotExistsException(22) }
        result.andExpect(status().is4xxClientError())
    }

    @TestConfiguration
    @Import([SpringDataWebAutoConfiguration, ValidationAutoConfiguration])
    static class CinemaControllerTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        CinemaService cinemaService() {
            return detachedMockFactory.Mock(CinemaService.class)
        }

    }
}