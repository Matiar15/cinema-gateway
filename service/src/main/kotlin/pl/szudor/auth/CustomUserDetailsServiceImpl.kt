package pl.szudor.auth

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.auth.authority.UserAuthorityRepository
import pl.szudor.auth.authority.requireByRole
import pl.szudor.auth.details.UserAuthority
import pl.szudor.exception.EmailAlreadyExistsException
import pl.szudor.exception.UserExistsException

interface CustomUserDetailsService : UserDetailsService {
    fun createUser(username: String, password: String, email: String?): User
    fun addUserEmail(username: String, email: String)
}

@Service
@Transactional
class CustomUserDetailsServiceImpl(
    private val userAuthorityRepository: UserAuthorityRepository,
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
            userRepository.saveAndFlush(user.apply { userAuthorities!! += assignAuthorities(email) })
        } catch (_: DataIntegrityViolationException) {
            throw UserExistsException(username)
        }

    override fun addUserEmail(username: String, email: String) {
        userRepository.requireByUsername(username).also {
            if (it.email != null) throw EmailAlreadyExistsException(username)
            it.email = email
            userRepository.save(it.apply { userAuthorities!! += userAuthorityRepository.requireByRole(CREATOR_ROLE) })
        }
    }

    override fun loadUserByUsername(username: String): UserDetails = userRepository.requireByUsername(username)

    private fun assignAuthorities(email: String?): Set<UserAuthority> =
        buildSet {
            add(userAuthorityRepository.requireByRole(USER_ROLE))
            email?.let {
                add(userAuthorityRepository.requireByRole(CREATOR_ROLE))
            }
        }
}