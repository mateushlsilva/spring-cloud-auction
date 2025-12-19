package com.mateus.auth.enum

import org.springframework.security.core.GrantedAuthority

enum class UserTypeRole(private val role: String) : GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_COMMON("ROLE_COMMON");
    override fun getAuthority(): String = role
}