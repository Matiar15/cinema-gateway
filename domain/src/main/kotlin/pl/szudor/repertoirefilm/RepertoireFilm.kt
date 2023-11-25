package pl.szudor.repertoirefilm

import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "repertoire_film")
class RepertoireFilm(
    @Id
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "id_repertoire")
    var repertoire: Repertoire? = null,

    @ManyToOne
    @JoinColumn(name = "id_film")
    var film: Film? = null
)
