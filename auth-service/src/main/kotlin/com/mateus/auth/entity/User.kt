package com.mateus.auth.entity

import com.mateus.auth.enum.UserTypeRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    val id: Long? = null,

    @Column(name = "usr_email", unique = true, nullable = false)
    val email: String,

    @Column(name = "usr_name", nullable = false)
    val name: String,

    @Column(name = "usr_age", nullable = false)
    val age: Int,

    @Column(name = "usr_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: UserTypeRole = UserTypeRole.ROLE_COMMON,

    @Column(name = "usr_password", nullable = false)
    private val password: String,
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(type)

    override fun getPassword(): String = password

    override fun getUsername(): String = email


}