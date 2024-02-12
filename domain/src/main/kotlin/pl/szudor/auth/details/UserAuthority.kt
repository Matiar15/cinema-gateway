package pl.szudor.auth.details

import org.springframework.security.core.GrantedAuthority
import pl.szudor.auth.User
import javax.persistence.*

@Entity
@Table(name = "user_authority")
class UserAuthority : GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "authority")
    var role: String? = null

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "user_authority_user",
        joinColumns = [JoinColumn(name = "id_authority", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "id_user", referencedColumnName = "id")]
    )
    var users: MutableSet<User>? = mutableSetOf()
    override fun getAuthority(): String = ROLE + role!!

    companion object {
        const val ROLE = "ROLE_"
    }
}