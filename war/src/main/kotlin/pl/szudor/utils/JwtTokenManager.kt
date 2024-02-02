package pl.szudor.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.SignatureException
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import pl.szudor.exception.TokenExpiredException
import pl.szudor.user.details.UserAuthority
import java.io.Serializable
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenManager(
    private val jwtData: JwtData,
) : Serializable {
    companion object {
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        val KEY: SecretKey = Jwts.SIG.HS512.key().build()
    }

    fun generateToken(username: String, authorities: Collection<UserAuthority>): String {
        val currentTime = Instant.now()

        return Jwts.builder()
            .subject(username)
            .claims(mapOf("roles" to setOf(authorities.map { it.authority })))
            .issuedAt(Date.from(currentTime))
            .expiration(Date.from(currentTime.plus(jwtData.tokenValidity, ChronoUnit.SECONDS)))
            .signWith(KEY)
            .compact()
    }
    fun validateClaims(claims: Claims): Boolean {
        try {
            return claims.expiration.after(Date())
        } catch (e: Exception) {
            throw e
        }
    }

    fun resolveClaims(req: HttpServletRequest): Claims? =
        resolveToken(req)?.let {
            try {
                Jwts.parser().verifyWith(KEY).build().parseSignedClaims(it).payload
            } catch (ex: ExpiredJwtException) {
                throw TokenExpiredException()
            } catch (ex: SignatureException) {
                throw pl.szudor.exception.SignatureException()
            }
        }


    fun resolveToken(request: HttpServletRequest): String? = request.getHeader(TOKEN_HEADER)?.let {
        return when {
            (it.startsWith(TOKEN_PREFIX)) -> it.substring(TOKEN_PREFIX.length)
            else -> null
        }
    }
}

@ConfigurationProperties(prefix = "jwt")
class JwtData {
    var tokenValidity: Long = 0
}