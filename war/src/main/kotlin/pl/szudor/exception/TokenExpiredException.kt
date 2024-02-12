package pl.szudor.exception

import org.springframework.security.core.AuthenticationException

class TokenExpiredException : AuthenticationException("Provided token has expired")