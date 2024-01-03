package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class EventNotExistsException(id: Int) :
    NotExistsException("Did not find any events with ID: $id")
