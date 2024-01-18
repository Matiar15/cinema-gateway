package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class EventNotExistsException(id: Int) :
    NotExistsException("Could not find any event by id $id")
