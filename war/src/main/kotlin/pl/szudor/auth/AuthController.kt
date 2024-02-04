package pl.szudor.auth

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val credentialAuthorizationService: CredentialAuthorizationService,
    private val customUserDetailsService: CustomUserDetailsService,
) {
    @PostMapping("/token")
    fun createToken(@RequestBody @Valid payload: AuthPayload): AuthDto =
        AuthDto(payload.username!!, credentialAuthorizationService.generateToken(payload.username, payload.password!!))

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