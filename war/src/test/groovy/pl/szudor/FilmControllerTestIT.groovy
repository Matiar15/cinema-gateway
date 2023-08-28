package pl.szudor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.film.FilmDto
import spock.lang.Specification

import java.time.LocalTime

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FilmControllerTestIT extends Specification {
    private final String ENDPOINT = "/films"

    @Autowired
    TestRestTemplate restTemplate

    def "test post film"() {
        given:
        def filmDto = new FilmDto(null, LocalTime.of(13,15, 10), 5, null, null)

        when:
        def response = restTemplate.postForEntity("$ENDPOINT/1", filmDto, FilmDto.class)

        then:
        response.statusCodeValue == 201
        response.hasBody()

    }

    def "test get all films"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", FilmDto[].class)

        then:
        response.statusCodeValue == 200
        !response.getBody().findAll().empty
    }

    def "test delete film"() {
        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, String.class)

        then:
        response.statusCodeValue == 204
        response.getBody() == null
    }
}
