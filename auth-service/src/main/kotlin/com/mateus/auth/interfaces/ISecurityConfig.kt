package com.mateus.auth.interfaces

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

interface ISecurityConfig {
    fun passwordEncoderBean(): PasswordEncoder
    fun filterChain(http: HttpSecurity): SecurityFilterChain
    fun authenticationManager(authenticationProviders: List<AuthenticationProvider>): AuthenticationManager
    fun authenticationProvider(userDetailsService: UserDetailsService, encoder: PasswordEncoder): DaoAuthenticationProvider
}