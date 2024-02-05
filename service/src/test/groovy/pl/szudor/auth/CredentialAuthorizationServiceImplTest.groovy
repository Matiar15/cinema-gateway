package pl.szudor.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import pl.szudor.auth.details.UserAuthority
import pl.szudor.exception.UserNotFoundException
import spock.lang.Specification

import static pl.szudor.auth.CredentialAuthorizationServiceImplTest.TestData.getUsername
import static pl.szudor.auth.CredentialAuthorizationServiceImplTest.TestData.password

class CredentialAuthorizationServiceImplTest extends Specification {
    AuthenticationManager authorizationManager = Mock()
    JwtTokenManager jwtTokenManager = Mock()

    def underTest = new CredentialAuthorizationServiceImpl(authorizationManager, jwtTokenManager)

    def "should authenticate all good" () {
        given:
        def token = new UsernamePasswordAuthenticationToken(username, password)

        when:
        def result  = underTest.authenticate(username, password)

        then:
        1 * authorizationManager.authenticate(token) >> token

        and:
        result == token

        and:
        0 * _
    }

    def "authenticate should validate bad credentials exception" () {
        given:
        def token = new UsernamePasswordAuthenticationToken(username, password)

        when:
        underTest.authenticate(username, password)

        then:
        1 * authorizationManager.authenticate(token) >> { throw new BadCredentialsException("") }

        and:
        thrown UserNotFoundException
        0 * _
    }

    def "should generate token all good"() {
        given:
        def authority = new UserAuthority().tap {
            it.user = user
            it.role = "USER"
        }
        def token = new UsernamePasswordAuthenticationToken(username, password)
        def databaseToken = new UsernamePasswordAuthenticationToken(username, password, [authority])

        when:
        underTest.generateToken(username, password)

        then:
        1 * authorizationManager.authenticate(token) >> databaseToken

        and:
        1 * jwtTokenManager.generateToken(username, [authority])

        and:
        0 * _
    }

    class TestData {
        static username = "Matiar"
        static password = "Pword"
    }
}
