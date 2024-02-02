package pl.szudor.user.authority

import org.springframework.data.jpa.repository.JpaRepository
import pl.szudor.user.details.UserAuthority

interface UserAuthorityRepository : JpaRepository<UserAuthority, Int>