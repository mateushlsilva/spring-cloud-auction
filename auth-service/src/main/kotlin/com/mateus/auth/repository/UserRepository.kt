package com.mateus.auth.repository

import com.mateus.auth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository: JpaRepository<User, String> {
    fun findByEmail(email: String): UserDetails
    fun existsByEmail(email: String): Boolean
}