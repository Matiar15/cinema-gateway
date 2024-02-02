package pl.szudor.user.details

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.security.core.GrantedAuthority
import pl.szudor.user.User
import javax.persistence.*

@Entity
@Table(name = "user_authority")
class UserAuthority : GrantedAuthority {
    companion object {
        const val ROLE = "ROLE_"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "authority")
    var role: String? = null

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    var user: User? = null
    override fun getAuthority(): String = ROLE + role!!
}