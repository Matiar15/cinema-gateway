package pl.szudor.cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/cinema/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CinemaControllerTestIT extends Specification {
    private final String ENDPOINT = "/cinema"

    @Autowired
    TestRestTemplate restTemplate

    def "post cinema"() {
        given:
        def payload = new CinemaPayload(
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30)
        )

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", payload, CinemaPayload.class)

        then: "there was returned dto"
        response.hasBody()

        and: "status code is CREATED"
        response.statusCodeValue == 201
    }

    def "get cinemas"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", Map<?, ?>.class)

        then: "get returned cinema"
        response.hasBody()

        and: "look if cinema's id was the same as in db record"
        response.getBody()['content']['id'][0] == 1
    }

    def "update state of the cinema"() {
        given:
        def httpEntity = new HttpEntity(new CinemaPatchPayload(Active.NO))

        when:
        def response = restTemplate.exchange("$ENDPOINT/1", HttpMethod.PATCH, httpEntity, ParameterizedTypeReference.forType(CinemaPatchPayload.class))

        then: "response status is 2xx"
        response.statusCodeValue == 200

        and: "body is not null"
        response.getBody() != null
    }
}
