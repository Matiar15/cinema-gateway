package pl.szudor.repertoirefilmroom

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Key(
    @Column(name = "repertoire_id")
    var repertoireId: Int? = null,
    @Column(name = "film_id")
    var filmId: Int? = null,
    @Column(name = "room_id")
    var roomId: Int? = null,
): Serializable