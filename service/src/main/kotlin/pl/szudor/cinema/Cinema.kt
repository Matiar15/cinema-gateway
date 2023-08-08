package pl.szudor.cinema

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cinema")
class Cinema (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cinema_id")
        val id: Int?,
        @Column(name = "cinema_name")
        val name: String,
        @Column(name = "cinema_street")
        val street: String,
        @Column(name = "cinema_phone_number")
        val phoneNumber: String,
        @Column(name = "cinema_postal_code")
        val postalCode: String,
        @Column(name = "cinema_director")
        val director: String
) {
        @Column(name = "cinema_created_at")
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
