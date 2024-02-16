package pl.szudor.auth

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
    var passWord: String = ""

    @Column
    var email: String? = null

    @ManyToMany
    @JoinTable(
        name = "user_authority_user",
        joinColumns = [JoinColumn(name = "id_user", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "id_authority", referencedColumnName = "id")]
    )
    var userAuthorities: MutableSet<UserAuthority>? = mutableSetOf()

    @Column
    var locked: Boolean = false

    @Column
    var nonExpired: Boolean = true

    @Column
    var accountNonLocked: Boolean = true

    @Column
    var nonCredentialsExpired: Boolean = true

    @Column
    var enabled: Boolean = false

    override fun getAuthorities(): Set<GrantedAuthority> = userAuthorities!!
    override fun getPassword(): String = passWord
    override fun getUsername(): String = userName
    override fun isAccountNonExpired(): Boolean = nonExpired
    override fun isAccountNonLocked(): Boolean = accountNonLocked
    override fun isCredentialsNonExpired(): Boolean = nonCredentialsExpired
    override fun isEnabled(): Boolean = enabled
}