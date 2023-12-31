package pl.szudor.seat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/seat/populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SeatControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate testRestTemplate

    private static final ENDPOINT = "/room/1/seat"

    def "create seat"() {
        given:
        def payload = new SeatPayload(5)

        when:
        def response = testRestTemplate.postForEntity("$ENDPOINT", payload, SeatDto.class)

        then: "response status is created"
        response.statusCodeValue == 201

        and: "dto is returned"
        response.getBody() != null
    }

    def "delete seat"() {
        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, Void.class)

        then: "response is no content"
        response.statusCodeValue == 204

        and: "body is null"
        response.getBody() == null
    }
}
