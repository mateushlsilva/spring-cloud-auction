package com.mateus.auth.services

import com.mateus.auth.dto.AuthenticationDTO
import com.mateus.auth.dto.RegisterDTO
import com.mateus.auth.entity.User
import com.mateus.auth.enum.UserTypeRole
import com.mateus.auth.exception.EmailAlreadyExistsException
import com.mateus.auth.exception.UnderageUserException
import com.mateus.auth.interfaces.IAuthorization
import com.mateus.auth.repository.UserRepository
import com.mateus.auth.security.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthorizationService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
): UserDetailsService, IAuthorization {

    private val MINIMUM_AGE_TO_CREATE_ACCOUNT = 18
    
    override fun loadUserByUsername(username: String): UserDetails {
        return repository.findByEmail(username)
    }

    override fun register(createUser: RegisterDTO): User {
        if (repository.existsByEmail(createUser.email)) {
            throw EmailAlreadyExistsException()
        }
        
        if (createUser.age < MINIMUM_AGE_TO_CREATE_ACCOUNT) {
            throw UnderageUserException()
        }

        val encryptedPassword: String = passwordEncoder
            .encode(createUser.password).toString()

        val newUser = User(
            email = createUser.email, 
            password = encryptedPassword, 
            type = createUser.type?: UserTypeRole.ROLE_COMMON,
            name = createUser.name, 
            age = createUser.age
        )

        return repository.save<User>(newUser)
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }
}