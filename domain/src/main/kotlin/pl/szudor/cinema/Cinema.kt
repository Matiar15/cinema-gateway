package pl.szudor.cinema

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cinema")
class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int? = 0

    @Column(name = "name")
    var name: String? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "phone_number")
    var phoneNumber: String? = null

    @Column(name = "postal_code")
    var postalCode: String? = null

    @Column(name = "director")
    var director: String? = null

    @Column(name = "nip_code")
    var nipCode: String? = null

    @Column(name = "build_date")
    var buildDate: LocalDate? = null

    @Column(name = "is_active")
    @Enumerated(EnumType.STRING)
    var state: State? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cinema

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

}

enum class State {
    YES,
    NO
}
