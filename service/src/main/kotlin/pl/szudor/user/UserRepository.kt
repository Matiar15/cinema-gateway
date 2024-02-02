package pl.szudor.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import pl.szudor.exception.UserNotFoundException
import pl.szudor.user.details.QUserAuthority

interface UserRepository : JpaRepository<User, Int>, UserCustomRepository

interface UserCustomRepository {
    fun findByUsername(username: String): User?
}

class UserRepositoryImpl : QuerydslRepositorySupport(User::class.java), UserCustomRepository {
    override fun findByUsername(username: String): User? {
        val root = QUser.user
        val authority = QUserAuthority.userAuthority

        return from(authority)
            .select(root)
            .join(authority.user, root)
            .where(root._username.eq(username))
            .fetchFirst()
    }
}

fun UserRepository.requireByUsername(username: String): User = findByUsername(username) ?: throw UserNotFoundException(username)