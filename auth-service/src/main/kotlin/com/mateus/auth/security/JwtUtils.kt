package com.mateus.auth.security

import com.auth0.jwt.JWT
import com.mateus.auth.entity.User
import com.mateus.auth.interfaces.IJwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException

@Component
class JwtUtils: IJwtUtils {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    override fun generateToken(user: User): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val token = JWT.create()
                .withIssuer("auction")
                .withSubject(user.email)
                .withExpiresAt(genExpirationDate())
                .sign(algorithm)
            return token
        }catch (exception: JWTCreationException) {
            throw RuntimeException("Error while generating token", exception)
        }
    }

    override fun validateToken(token: String): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                .withIssuer("auction")
                .build()
                .verify(token)
                .subject
        }catch (exception: JWTVerificationException) {
            return ""
        }
    }

    override fun genExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}