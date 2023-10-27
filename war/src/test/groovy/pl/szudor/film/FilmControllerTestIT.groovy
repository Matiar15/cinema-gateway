package pl.szudor.film

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.film.FilmDto
import pl.szudor.film.Pegi
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FilmControllerTestIT extends Specification {
    private final String ENDPOINT = "/film"

    @Autowired
    TestRestTemplate restTemplate

    def "create film"() {
        given:
        def filmDto = new FilmDto(null, LocalTime.of(13,15, 10), null, "", Pegi.EIGHTEEN, 10, LocalDate.of(2023, 3, 3), "PL", null, LocalDateTime.now())

        when:
        def response = restTemplate.postForEntity("$ENDPOINT/repertoire/1/room/1", filmDto, FilmDto.class)

        then:
        response.statusCodeValue == 201
        response.hasBody()

    }

    def "get all films"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", FilmDto[].class)

        then:
        response.statusCodeValue == 200
        response.getBody() != null
    }

    def "delete film"() {
        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, String.class)

        then:
        response.statusCodeValue == 204
        response.getBody() == null
    }
}
