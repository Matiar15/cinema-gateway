package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class RoomNotExistsException(id: Int):
    NotExistsException("Room under id: $id does not exist!")