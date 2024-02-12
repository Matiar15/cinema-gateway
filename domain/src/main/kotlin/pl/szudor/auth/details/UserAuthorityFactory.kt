package pl.szudor.auth.details

import org.springframework.stereotype.Service
import pl.szudor.auth.User

interface UserAuthorityFactory {
    fun createUserAuthority(user: User, role: String): UserAuthority
}

@Service
class UserAuthorityFactoryImpl : UserAuthorityFactory {
    override fun createUserAuthority(user: User, role: String): UserAuthority =
        UserAuthority().apply {
            users?.let { it += user }
            this.role = role
        }
}