package pl.szudor.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import java.time.LocalTime

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/event/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EventControllerTestIT extends Specification {

    @Autowired
    TestRestTemplate restTemplate
    def "post event"() {
        given:
        def payload = new EventPayload(LocalTime.of(23, 23))

        when:
        def response = restTemplate.postForEntity("/repertoire/1/film/1/room/1/event", payload, EventDto.class)

        then: "dto was returned"
        response.hasBody()

        and: "status is created"
        response.getStatusCodeValue() == 201
    }

    def "get all events"() {
        when:
        def response = restTemplate.getForEntity("/repertoire/1/film/1/room/1/event", Map<?, ?>.class)

        then: "get returned event"
        response.hasBody()
    }

    def "patch event"() {
        given:
        def httpEntity = new HttpEntity(new EventPatchPayload(LocalTime.of(9, 12)))

        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event/10:10", HttpMethod.PATCH, httpEntity, ParameterizedTypeReference.forType(EventDto.class))

        then: "response status is no content"
        response.statusCodeValue == 200
    }

    def "delete event"() {
        when:
        def response = restTemplate.exchange("/repertoire/1/film/1/room/1/event/10:10", HttpMethod.DELETE, null, String.class)

        then: "response status is no content"
        response.statusCodeValue == 204
    }
}