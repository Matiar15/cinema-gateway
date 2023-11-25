package pl.szudor.cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import pl.szudor.data.domain.PageImplDto
import spock.lang.Specification

import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CinemaControllerTestIT extends Specification {
    private final String ENDPOINT = "/cinema"

    @Autowired
    TestRestTemplate restTemplate

    def "post cinema"() {
        given: "test dto"
        def cinemaDto = new CinemaPayload(
                null,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                null
        )

        when:
        def response = restTemplate.postForEntity("$ENDPOINT", cinemaDto, CinemaPayload.class)

        then:
        response.hasBody()
        response.getBody() == new CinemaPayload(
                2,
                "test",
                "test",
                "xdddd@wp.pl",
                "+48-123-456-789",
                "99-999",
                "test",
                "1234567890",
                LocalDate.of(2019, 3, 30),
                State.NO,
                response.getBody().createdAt
        )
        response.statusCodeValue == 201
    }

    def "get cinemas"() {
        when:
        def response = restTemplate.getForEntity("$ENDPOINT", PageImplDto<CinemaPayload>.class)

        then:
        response.hasBody()
        !response.getBody().findAll().empty
    }

    def "update state of the cinema"() {
        given:
        def httpEntity = new HttpEntity(new CinemaPatchPayload(State.NO))

        when:
        def response = restTemplate.exchange("$ENDPOINT/state/1", HttpMethod.PUT, httpEntity, ParameterizedTypeReference.forType(CinemaPayload.class) )

        then:
        response.statusCodeValue == 200
        response.getBody() != null

    }
}
