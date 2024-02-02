package pl.szudor.auth

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.exception.UserNotFoundException
import pl.szudor.user.details.UserAuthority
import pl.szudor.utils.JwtTokenManager
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val credentialAuthorizationService: CredentialAuthorizationService,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtTokenManager: JwtTokenManager,
) {
    @PostMapping("/token")
    fun createToken(@RequestBody @Valid payload: AuthPayload): AuthDto =
        credentialAuthorizationService.authenticate(payload.username!!, payload.password!!).run {
            val authorities = this.authorities.filterIsInstance<UserAuthority>()
                .takeIf { it.size == this.authorities.size } ?: throw UserNotFoundException(payload.username)
            val token = jwtTokenManager.generateToken(this.name, authorities)
            return AuthDto(this.name, token)
        }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid payload: UserPayload) {
        customUserDetailsService.createUser(payload.username!!, payload.password!!, payload.email)
    }

    @PatchMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun patch(@PathVariable username: String, @RequestBody @Valid payload: UserPatchPayload) {
        customUserDetailsService.addUserEmail(username, payload.email!!)
    }
}