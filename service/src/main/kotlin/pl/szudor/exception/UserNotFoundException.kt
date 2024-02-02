package pl.szudor.exception

import pl.szudor.exception.generic.NotExistsException

class UserNotFoundException(username: String) : NotExistsException("Account with username: $username does not exist!")