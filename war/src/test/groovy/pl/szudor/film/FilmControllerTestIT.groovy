package pl.szudor.film

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/film/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FilmControllerTestIT extends Specification {
    private final String ENDPOINT = "/film"

    @Autowired
    TestRestTemplate restTemplate

    def "create film"() {
        given:
        def payload = new FilmPayload("xD", Pegi.SEVEN, 12, LocalDate.of(2023, 3, 3), "PL_pl")

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", payload, FilmDto.class)

        then: "response status is no content"
        response.statusCodeValue == 201

        and: "there was dto returned"
        response.hasBody()
    }

    def "get all films"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", Map<?, ?>.class)

        then: "get returned film"
        response.hasBody()

        and: "look if film's id was the same as in db record"
        response.getBody()['content']['id'][0] == 1
    }

    def "delete film"() {
        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, String.class)

        then: "response status is no content"
        response.statusCodeValue == 204
    }
}
