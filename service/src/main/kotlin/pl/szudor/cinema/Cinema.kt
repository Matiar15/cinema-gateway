package pl.szudor.cinema

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "CINEMA")
class Cinema (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        var id: Long? = null,
        @Column(name = "NAME")
        val name: String,
        @Column(name = "STREET")
        val street: String,
        @Column(name = "DIRECTOR")
        val director: String,
        @Column(name = "PHONE_NUMBER")
        val phoneNumber: String,
        @Column(name = "POSTAL_CODE")
        val postalCode: String,
) {
        @Column(name = "CREATED_AT")
        var createdAt: LocalDateTime? = LocalDateTime.now()
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Cinema

                return id == other.id
        }

        override fun hashCode(): Int {
                return id?.hashCode() ?: 0
        }

}
fun Cinema.toCinemaDto(): CinemaDto {
        return CinemaDto(
                id = id!!,
                name = name,
                street = street,
                director = director,
                phoneNumber = phoneNumber,
                postalCode = postalCode
        )
}