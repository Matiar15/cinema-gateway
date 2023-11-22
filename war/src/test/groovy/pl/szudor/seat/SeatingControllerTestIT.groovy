package pl.szudor.seat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SeatingControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate testRestTemplate

    private static final ENDPOINT = "/seat"

    def "create seating"() {
        when:
        def response = testRestTemplate.postForEntity("$ENDPOINT/room/1", new SeatingDto(null, 5, null, null), SeatingDto.class)

        then:
        response.statusCodeValue == 201
        response.getBody() != null
    }

    def "update seating"() {
        given:
        def httpEntity = new HttpEntity(new SeatingDto(null, 5, null, null))
        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.PUT, httpEntity, SeatingDto.class)

        then:
        response.statusCodeValue == 200
        response.getBody() != null
    }

    def "delete seating"() {
        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, Void.class)

        then:
        response.statusCodeValue == 204
        response.getBody() == null
    }
}
