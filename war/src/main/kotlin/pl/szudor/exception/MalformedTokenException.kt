package pl.szudor.exception

import org.springframework.security.core.AuthenticationException

class MalformedTokenException : AuthenticationException("Provided has been malformed, please create a new one.")