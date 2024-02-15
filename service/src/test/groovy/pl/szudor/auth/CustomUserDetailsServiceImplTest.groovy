package pl.szudor.auth

import org.springframework.dao.DataIntegrityViolationException
import pl.szudor.auth.authority.UserAuthorityRepository
import pl.szudor.auth.details.UserAuthority
import pl.szudor.exception.EmailAlreadyExistsException
import pl.szudor.exception.UserExistsException
import pl.szudor.exception.UserNotFoundException
import spock.lang.Specification

import static pl.szudor.auth.CustomUserDetailsServiceImplTest.TestData.*

class CustomUserDetailsServiceImplTest extends Specification {
    UserAuthorityRepository userAuthorityRepository = Mock()
    UserFactory userFactory = Mock()
    UserRepository userRepository = Mock()

    def underTest = new CustomUserDetailsServiceImpl(
            userAuthorityRepository,
            userFactory,
            userRepository
    )

    def "should create user all good"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
        }

        def authority = new UserAuthority().tap {
            it.users = [user]
            it.role = "USER"
        }

        when:
        underTest.createUser(username, password, null)

        then:
        1 * userFactory.createUser(username, password, null) >> user

        and:
        1 * userAuthorityRepository.findByRole("USER") >> authority

        and:
        1 * userRepository.save({ User u -> u.userAuthorities == [authority].toSet() }) >> user

        and:
        0 * _
    }

    def "create user with provided email address"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
            it.email = email
        }

        def authority = new UserAuthority().tap {
            it.users = [user]
            it.role = "USER"
        }

        def creatorAuthority = new UserAuthority().tap {
            it.users = [user]
            it.role = "CREATOR"
        }

        when:
        underTest.createUser(username, password, email)

        then:
        1 * userFactory.createUser(username, password, email) >> user

        and:
        1 * userAuthorityRepository.findByRole("USER") >> authority

        and:
        1 * userAuthorityRepository.findByRole("CREATOR") >> creatorAuthority

        and:
        1 * userRepository.save({ User u -> u.userAuthorities == [authority, creatorAuthority].toSet() })
                >> user

        and:
        0 * _
    }

    def "create user should throw data integrity violation exception"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
            it.email = email
        }

        def authority = new UserAuthority().tap {
            it.users = [user]
            it.role = "USER"
        }

        def creatorAuthority = new UserAuthority().tap {
            it.users = [user]
            it.role = "CREATOR"
        }

        when:
        underTest.createUser(username, password, email)

        then:
        1 * userFactory.createUser(username, password, email) >> user

        and:
        1 * userAuthorityRepository.findByRole("USER") >> authority

        and:
        1 * userAuthorityRepository.findByRole("CREATOR") >> creatorAuthority

        and:
        1 * userRepository.save({ User u -> u.userAuthorities == [authority, creatorAuthority].toSet() })
                >> { throw new DataIntegrityViolationException("") }

        and:
        thrown UserExistsException
        0 * _
    }

    def "add user email all good"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
            it.email = null
        }

        def creatorAuthority = new UserAuthority().tap {
            it.users = [user]
            it.role = "CREATOR"
        }

        when:
        underTest.addUserEmail(username, email)

        then:
        1 * userRepository.findByUsername(username) >> user

        and:
        1 * userAuthorityRepository.findByRole("CREATOR") >> creatorAuthority

        and:
        1 * userRepository.save({ User u -> u.userAuthorities == [creatorAuthority].toSet() })

        and:
        0 * _
    }

    def "add user email all good"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
            it.email = email
        }

        when:
        underTest.addUserEmail(username, email)

        then:
        1 * userRepository.findByUsername(username) >> user

        and:
        thrown EmailAlreadyExistsException
        0 * _
    }

    def "add user email should validate null user"() {
        when:
        underTest.addUserEmail(username, email)

        then:
        1 * userRepository.findByUsername(username) >> null

        and:
        thrown UserNotFoundException
        0 * _
    }

    def "load by username all good"() {
        given:
        def user = new User().tap {
            it.userName = username
            it.userName = password
            it.locked = false
            it.email = email
        }

        when:
        underTest.loadUserByUsername(username) >> user

        then:
        0 * _
    }

    class TestData {
        static username = "Matiar"
        static password = "Pword"
        static email = "mail@mail.com"
    }
}
