package pl.szudor.exception

class UserAuthorityNotExistsException(role: String) : NotExistsException("No role with name ROLE_${role} exists!")