package pl.szudor.exception


open class NotExistsException : RuntimeException {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}
