package pl.szudor.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import pl.szudor.auth.details.UserAuthority
import pl.szudor.exception.UserNotFoundException

interface CredentialAuthorizationService {
    fun authenticate(username: String, password: String): Authentication
    fun generateToken(username: String, password: String): String
}

@Service
class CredentialAuthorizationServiceImpl(
    private val authorizationManager: AuthenticationManager,
    private val jwtTokenManager: JwtTokenManager
) : CredentialAuthorizationService {
    override fun authenticate(username: String, password: String): Authentication =
        try {
            authorizationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (ex: BadCredentialsException) {
            throw pl.szudor.exception.BadCredentialsException()
        }

    override fun generateToken(username: String, password: String): String {
        authenticate(username, password).run {
            val authorities = this.authorities.filterIsInstance<UserAuthority>()
                .takeIf { it.size == this.authorities.size } ?: throw UserNotFoundException(username)
            return jwtTokenManager.generateToken(this.name, authorities)
        }

    }
}