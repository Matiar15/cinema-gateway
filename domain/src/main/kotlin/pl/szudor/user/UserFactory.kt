package pl.szudor.user

import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface UserFactory {
    fun createUser(username: String, password: String, email: String?): User
}

@Service
class UserFactoryImpl(
    @Lazy private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserFactory {
    override fun createUser(username: String, password: String, email: String?): User =
        User().apply {
            _username = username
            _password = bCryptPasswordEncoder.encode(password)
            email?.let { this.email = email }
            this.locked = User.Enum.NO
        }

}