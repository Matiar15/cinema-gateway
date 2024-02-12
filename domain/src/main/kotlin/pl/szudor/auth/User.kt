package pl.szudor.auth

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.szudor.auth.details.UserAuthority
import javax.persistence.*

@Entity
@Table(name = "user")
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "user_name")
    var userName: String = ""

    @Column(name = "password")
    var pword: String = ""

    @Column
    var email: String? = null

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    var userAuthorities: Set<UserAuthority> = setOf()

    @Column
    @Enumerated(EnumType.STRING)
    var locked: Enum = Enum.NO

    @Column
    @Enumerated(EnumType.STRING)
    var nonExpired: Enum = Enum.YES

    @Column
    @Enumerated(EnumType.STRING)
    var accountNonLocked: Enum = Enum.YES

    @Column
    @Enumerated(EnumType.STRING)
    var nonCredentialsExpired: Enum = Enum.YES

    @Column
    @Enumerated(EnumType.STRING)
    var enabled: Enum = Enum.NO

    enum class Enum {
        YES,
        NO;

        fun enumToBoolean() =
           when (this) {
               YES -> true
               NO -> false
           }

    }

    override fun getAuthorities(): Set<GrantedAuthority> = userAuthorities
    override fun getPassword(): String = pword
    override fun getUsername(): String = userName
    override fun isAccountNonExpired(): Boolean = nonExpired.enumToBoolean()
    override fun isAccountNonLocked(): Boolean = accountNonLocked.enumToBoolean()
    override fun isCredentialsNonExpired(): Boolean = nonCredentialsExpired.enumToBoolean()
    override fun isEnabled(): Boolean = enabled.enumToBoolean()
}