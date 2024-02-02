package pl.szudor.user.details

import org.springframework.stereotype.Service
import pl.szudor.user.User

interface UserAuthorityFactory {
    fun createUserAuthority(user: User, role: String): UserAuthority
}

@Service
class UserAuthorityFactoryImpl : UserAuthorityFactory {
    override fun createUserAuthority(user: User, role: String): UserAuthority =
        UserAuthority().apply { this.user = user; this.role = role }
}