package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class EventNotExistsException(repertoireId: Int, filmId: Int, roomId: Int) :
    NotExistsException("Could not find event matching repertoire ID: $repertoireId, film ID: $filmId, and room ID: $roomId")
