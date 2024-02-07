package pl.szudor.utils

import io.jsonwebtoken.Claims
import pl.szudor.auth.JwtData
import pl.szudor.auth.JwtTokenManager
import pl.szudor.auth.details.UserAuthority
import spock.lang.Specification

import java.time.Instant

class JwtTokenManagerTest extends Specification {
    def underTest = new JwtTokenManager(new JwtData())

    def "should validate claims all good"() {
        given:
        def claims = Mock(Claims) {
            it.getExpiration() >> Date.from(Instant.now().plusSeconds(3600))
        }

        when:
        def result = underTest.validateClaims(claims)

        then:
        result
    }

    def "should validate claims with expired token"() {
        given:
        def claims = Mock(Claims) {
            it.getExpiration() >> Date.from(Instant.now().minusMillis(100))
        }

        when:
        def result = underTest.validateClaims(claims)

        then:
        !result
    }

    def "should generate token all good"() {
        given:
        def username = "Matiar"
        def authorities = [] as Collection<UserAuthority>

        when:
        def result = underTest.generateToken(username, authorities)

        then:
        !result.isEmpty()
    }
}
