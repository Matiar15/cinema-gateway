package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException

class EmailAlreadyExistsException(username: String) :
    BadRequestException("User: $username already has assigned email address")