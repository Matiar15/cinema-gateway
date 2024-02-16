package pl.szudor.auth

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AuthPayload(
    @field:Size(min = 5, max = 20)
    @field:NotNull
    val username: String?,
    @field:Size(min = 5, max = 20)
    @field:NotNull
    val password: String?
)

data class UserPayload(
    @field:Size(min = 5, max = 20)
    @field:NotNull
    val username: String?,
    @field:Size(min = 5, max = 20)
    @field:NotNull
    val password: String?,
    @field:Email
    val email: String?
)

data class UserPatchPayload(
    @field:Email
    @field:NotNull
    val email: String?
)
