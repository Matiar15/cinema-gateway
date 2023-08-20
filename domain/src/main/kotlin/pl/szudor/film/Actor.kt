package pl.szudor.film

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "person")
class Actor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @Column(name = "date_of_birth")
    val dateOfBirth: LocalDate,

    @Column(name = "occupation")
    val occupation: String
)