package pl.szudor.exception

class RoomNotExistsException:
    NotExistsException {
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(message: String): super(message)
}