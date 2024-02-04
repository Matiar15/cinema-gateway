package pl.szudor.auth

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.szudor.exception.UserExistsException
import pl.szudor.auth.authority.UserAuthorityRepository
import pl.szudor.auth.details.UserAuthorityFactory

interface CustomUserDetailsService : UserDetailsService {
    fun createUser(username: String, password: String, email: String?): User
    fun addUserEmail(username: String, email: String)
}

@Service
class CustomUserDetailsServiceImpl(
    private val userAuthorityRepository: UserAuthorityRepository,
    private val userAuthorityFactory: UserAuthorityFactory,
    private val userFactory: UserFactory,
    private val userRepository: UserRepository
) : CustomUserDetailsService {
    companion object {
        const val USER_ROLE = "USER"
    }

    override fun createUser(username: String, password: String, email: String?): User =
        try {
            userRepository.saveAndFlush(
                userFactory.createUser(
                    username,
                    password, email
                )
            ).run {
                userAuthorityRepository.save(userAuthorityFactory.createUserAuthority(this, USER_ROLE))
                this
            }
        } catch (_: DataIntegrityViolationException) {
            throw UserExistsException(username)
        }

    override fun addUserEmail(username: String, email: String) {
        userRepository.requireByUsername(username).also { it.email = email }
    }

    override fun loadUserByUsername(username: String): UserDetails = userRepository.requireByUsername(username)
}