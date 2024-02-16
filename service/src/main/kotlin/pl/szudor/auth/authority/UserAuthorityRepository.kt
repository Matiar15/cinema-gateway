package pl.szudor.auth.authority

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import pl.szudor.auth.details.QUserAuthority
import pl.szudor.auth.details.UserAuthority
import pl.szudor.exception.UserAuthorityNotExistsException

interface UserAuthorityRepository : JpaRepository<UserAuthority, Int>, CustomUserAuthorityRepository

interface CustomUserAuthorityRepository {
    fun findByRole(role: String): UserAuthority?
}

class UserAuthorityRepositoryImpl : QuerydslRepositorySupport(UserAuthority::class.java),
    CustomUserAuthorityRepository {
    override fun findByRole(role: String): UserAuthority? {
        val root = QUserAuthority.userAuthority

        return from(root)
            .where(root.role.eq(role))
            .fetchFirst()
    }
}

fun UserAuthorityRepository.requireByRole(role: String): UserAuthority =
    findByRole(role) ?: throw UserAuthorityNotExistsException(role)