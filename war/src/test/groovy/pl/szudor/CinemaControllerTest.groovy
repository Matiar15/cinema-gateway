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
import pl.szudor.cinema.CinemaController
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaService
import pl.szudor.cinema.CinemaState
import pl.szudor.exception.RepertoireNotExistsException
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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

    def "test save cinema"() {
        given:
        def cinema = new CinemaDto(
                1,
                "test",
                "test",
                "test",
                "test",
                "test",
                "test",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF,
                null
        )
        def entity = new Cinema(1,
                "test",
                "test",
                "test",
                "test",
                "test",
                "test",
                LocalDate.of(2019, 3, 30),
                CinemaState.OFF)
        def cinemaAsJson = objectMapper.writeValueAsString(cinema)

        when:
        def result = mvc.perform(post("/cinemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cinemaAsJson))

        then:
        1 * cinemaService.saveCinema(cinema) >> entity
        result.andExpect(status().is2xxSuccessful())
        and:
        0 * _
    }

    def "test get cinema"() {
        when:
        def result = mvc.perform(get("/cinemas"))

        then:
        1 * cinemaService.getCinemas() >> _
        result.andExpect(status().isOk())

        and:
        0 * _
    }

    def "test delete cinema"() {
        when:
        def result = mvc.perform(delete("/cinemas/2"))

        then:
        1 * cinemaService.deleteCinema(2)
        result.andExpect(status().is2xxSuccessful())
    }

    def "test delete cinema with wrong cinema id"() {
        when:
        def result = mvc.perform(delete("/cinemas/22"))

        then:
        1 * cinemaService.deleteCinema(22) >> { throw new RepertoireNotExistsException("REPERTOIRE NOT FOUND UNDER ID: 22") }
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