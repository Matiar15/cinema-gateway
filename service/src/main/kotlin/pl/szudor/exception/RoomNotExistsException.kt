package pl.szudor.exception

class RoomNotExistsException(id: Int):
    NotExistsException("Room under id: $id does not exist!")