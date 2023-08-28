package pl.szudor.exception

class RepertoireNotExistsException :
    NotExistsException {
        constructor(message: String, cause: Throwable): super(message, cause)
        constructor(message: String): super(message)
    }