package com.mateus.auth.interfaces

import com.mateus.auth.entity.User
import java.time.Instant

interface IJwtUtils {
    fun generateToken(user: User): String
    fun validateToken(token: String): String
    fun genExpirationDate(): Instant
}