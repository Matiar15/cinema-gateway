package pl.szudor.exception

import org.springframework.security.core.AuthenticationException

class SignatureException : AuthenticationException("Signature does not match locally computed signature.")