package pl.szudor.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import pl.szudor.exception.UserNotFoundException

interface CredentialAuthorizationService {
    fun authenticate(username: String, password: String): Authentication
}

@Service
class CredentialAuthorizationServiceImpl(
    private val authorizationManager: AuthenticationManager,
) : CredentialAuthorizationService {
    override fun authenticate(username: String, password: String): Authentication =
        try {
            authorizationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (_: BadCredentialsException) {
            throw UserNotFoundException(username)
        }
}