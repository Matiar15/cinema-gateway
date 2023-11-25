/*
package pl.szudor.room

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:populate_with_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RoomControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate testRestTemplate

    public static final ENDPOINT = "/room"

    def "create room"() {
        when:
        def response = testRestTemplate.postForEntity("$ENDPOINT/cinema/1", new RoomDto(null, 12, null, null), RoomDto.class)

        then:
        response.statusCodeValue == 201
        response.hasBody()
    }

    def "update room"() {
        given:
        def httpEntity = new HttpEntity(new RoomPayload(13))

        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.PUT,  httpEntity, RoomDto.class)

        then:
        response.statusCodeValue == 200
        response.hasBody()
    }

    def "delete room"() {
        when:
        def response = testRestTemplate.exchange("$ENDPOINT/1", HttpMethod.DELETE, null, Void.class)

        then:
        response.statusCodeValue == 204
        !response.hasBody()
    }
}
*/
