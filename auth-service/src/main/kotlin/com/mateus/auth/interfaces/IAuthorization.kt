package com.mateus.auth.interfaces

import com.mateus.auth.dto.AuthenticationDTO
import com.mateus.auth.dto.RegisterDTO
import com.mateus.auth.entity.User

interface IAuthorization {
    fun register(createUser: RegisterDTO ): User
    fun refresh()
}