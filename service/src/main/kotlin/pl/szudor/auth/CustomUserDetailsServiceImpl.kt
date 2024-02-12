package pl.szudor.auth

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.szudor.auth.authority.UserAuthorityRepository
import pl.szudor.auth.details.UserAuthorityFactory
import pl.szudor.exception.EmailAlreadyExistsException
import pl.szudor.exception.UserExistsException

interface CustomUserDetailsService : UserDetailsService {
    fun createUser(username: String, password: String, email: String?): User
    fun addUserEmail(username: String, email: String)
}

@Service
class CustomUserDetailsServiceImpl(
    private val userAuthorityRepository: UserAuthorityRepository,
    private val userAuthorityFactory: UserAuthorityFactory,
    private val userFactory: UserFactory,
    private val userRepository: UserRepository,
) : CustomUserDetailsService {
    companion object {
        const val USER_ROLE = "USER"
        const val CREATOR_ROLE = "CREATOR"
    }

    override fun createUser(username: String, password: String, email: String?): User =
        try {
            val user = userFactory.createUser(
                username,
                password,
                email
            )
            buildSet {
                add(userAuthorityFactory.createUserAuthority(user, USER_ROLE))
                email?.let {
                    add(userAuthorityFactory.createUserAuthority(user, CREATOR_ROLE))
                }
            }.forEach { userAuthorityRepository.save(it) }
            user
        } catch (_: DataIntegrityViolationException) {
            throw UserExistsException(username)
        }

    override fun addUserEmail(username: String, email: String) {
        userRepository.requireByUsername(username).also {
            if (it.email != null) throw EmailAlreadyExistsException(username)
            it.email = email
            userAuthorityRepository.save(userAuthorityFactory.createUserAuthority(it, CREATOR_ROLE))
        }
    }

    override fun loadUserByUsername(username: String): UserDetails = userRepository.requireByUsername(username)
}