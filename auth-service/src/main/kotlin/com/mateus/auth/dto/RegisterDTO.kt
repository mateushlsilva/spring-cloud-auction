package com.mateus.auth.dto

import com.mateus.auth.enum.UserTypeRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterDTO(

    @field:Email(message = "Email inválido")
    @field:NotBlank(message = "Email é obrigatório")
    val email: String,

    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    @field:NotBlank(message = "Nome é obrigatório")
    val name: String,

    @field:NotNull(message = "Idade é obrigatório")
    val age: Int,

    @field:Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
        message = "Senha deve conter letra maiúscula, minúscula e número"
    )
    @field:NotBlank(message = "Senha é obrigatória")
    val password: String,

    val type: UserTypeRole? = UserTypeRole.ROLE_COMMON
)
