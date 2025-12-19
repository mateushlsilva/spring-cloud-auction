package com.mateus.auth.controllers

import com.mateus.auth.dto.AuthenticationDTO
import com.mateus.auth.dto.LoginResponseDTO
import com.mateus.auth.dto.RegisterDTO
import com.mateus.auth.entity.User
import com.mateus.auth.security.JwtUtils
import com.mateus.auth.services.AuthorizationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController /* @Component */
@RequestMapping("auth")
class AuthenticationController(
    private val authorizationService: AuthorizationService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {
    /*@RabbitListener(queues = ["auth-login-queue"])*/
    @PostMapping("/login")
    fun login(@RequestBody @Valid data: AuthenticationDTO): ResponseEntity<LoginResponseDTO> {
        val userEmailPassword = UsernamePasswordAuthenticationToken(
            data.email,
            data.password
        )
        val auth = authenticationManager.authenticate(userEmailPassword)
        val token = jwtUtils.generateToken(auth.principal as User)
        return ResponseEntity.ok(LoginResponseDTO(token));
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid data: RegisterDTO): ResponseEntity<User> {
        val user = authorizationService.register(data)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }
}