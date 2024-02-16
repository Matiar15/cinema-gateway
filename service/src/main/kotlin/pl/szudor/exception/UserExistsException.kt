package pl.szudor.exception

import pl.szudor.exception.generic.BadRequestException

class UserExistsException(username: String) : BadRequestException("Account with username: $username already exists!")