package pl.szudor.cinema

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cinema")
class Cinema (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cinema_id")
        val id: Int = 0,
        @Column(name = "name")
        val name: String,
        @Column(name = "street")
        val street: String,
        @Column(name = "director")
        val director: String,
        @Column(name = "phone_number")
        val phoneNumber: String,
        @Column(name = "postal_code")
        val postalCode: String,
) {
        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now()
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
fun Cinema.toDto(): CinemaDto {
        return CinemaDto(
                id,
                name,
                street,
                phoneNumber,
                postalCode,
                director
        )
}