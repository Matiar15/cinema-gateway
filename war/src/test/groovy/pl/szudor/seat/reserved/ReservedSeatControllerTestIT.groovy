package pl.szudor.seat.reserved

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import pl.szudor.auth.JwtTokenManager
import pl.szudor.auth.details.UserAuthority
import pl.szudor.seat.SeatDto
import spock.lang.Specification

import static pl.szudor.seat.reserved.ReservedSeatControllerTestIT.Data.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/seat/reserved/populate_with_data.sql")
@Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ReservedSeatControllerTestIT extends Specification {
    @Autowired
    TestRestTemplate testRestTemplate

    @Autowired
    JwtTokenManager tokenManager

    def headers = new HttpHeaders()

    def "should reserve seat all good"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def payload = new ReservedSeatPayload(2)
        def entity = new HttpEntity(payload, headers)

        when: "posting for creating reserved seat"
        def response = testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, entity, SeatDto.class)

        then: "status is created 201"
        response.statusCodeValue == CREATED

        and: "response body contains correct ID and correct Number"
        response.body.id == 2
        response.body.number == 3
    }

    def "should return one seat all good"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when: "get list of reserved seat"
        def response = testRestTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, Map<?, ?>.class)

        then: "status is ok 200"
        response.statusCodeValue == OK

        and: "response body contains correct ID and correct Number"
        response.body["content"]["id"][0] == 1
        response.body["content"]["number"][0] == 2
    }

    def "should delete reserved seat"() {
        given:
        headers.add("Authorization", "Bearer " + tokenManager.generateToken("dummy", [] as Collection<UserAuthority>))
        def entity = new HttpEntity(headers)

        when: "delete all seats by event id 1"
        def response = testRestTemplate.exchange(ENDPOINT + "/1", HttpMethod.DELETE, entity, Void.class)

        then: "status is no content"
        response.statusCodeValue == NO_CONTENT
    }

    class Data {
        static ENDPOINT = "/event/1/seat"
        static NO_CONTENT = 204
        static CREATED = 201
        static OK = 200
    }
}
