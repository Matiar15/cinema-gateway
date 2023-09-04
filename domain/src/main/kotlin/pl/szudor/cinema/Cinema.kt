package pl.szudor.cinema

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cinema")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class Cinema(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", insertable = false, updatable = false)
        var id: Int?,
        @Column(name = "name")
        val name: String,
        @Column(name = "address")
        val address: String,
        @Column(name = "email")
        val email: String,
        @Column(name = "phone_number")
        val phoneNumber: String,
        @Column(name = "postal_code")
        val postalCode: String,
        @Column(name = "director")
        val director: String,
        @Column(name = "nip_code")
        val nipCode: String,
        @Column(name = "build_date")
        val buildDate: LocalDate,
        @Column(name = "current_state")
        @Enumerated(EnumType.STRING)
        val currentState: CinemaState
) {
        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now()

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Cinema

                return id == other.id
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }

}
