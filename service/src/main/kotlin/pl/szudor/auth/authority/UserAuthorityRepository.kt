package pl.szudor.auth.authority

import org.springframework.data.jpa.repository.JpaRepository
import pl.szudor.auth.details.UserAuthority

interface UserAuthorityRepository : JpaRepository<UserAuthority, Int>