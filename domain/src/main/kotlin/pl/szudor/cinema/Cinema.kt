package pl.szudor.cinema

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cinema")
class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int? = 0

    @Column
    var name: String? = null

    @Column
    var address: String? = null

    @Column
    var email: String? = null

    @Column
    var phoneNumber: String? = null

    @Column
    var postalCode: String? = null

    @Column
    var director: String? = null

    @Column
    var nipCode: String? = null

    @Column
    var buildDate: LocalDate? = null

    @Column(name = "is_active")
    var active: Boolean? = null

    @Column
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