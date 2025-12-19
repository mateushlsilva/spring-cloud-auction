package com.mateus.auth.services

import com.mateus.auth.dto.RegisterDTO
import com.mateus.auth.enum.UserTypeRole
import com.mateus.auth.exception.EmailAlreadyExistsException
import com.mateus.auth.exception.UnderageUserException
import com.mateus.auth.repository.UserRepository
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder


@DisplayName("Testes de cadastro de usuário")
class AuthorizationServiceTest {

    private val repository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()

    private val service = AuthorizationService(
        repository,
        passwordEncoder
    )

    @Test
    @DisplayName("Deve falhar quando o email já existir")
    fun `deve lancar erro quando o email ja estiver cadastrado`() {

        val dto = RegisterDTO(
            email = "test@test.com",
            name = "teste",
            age = 18,
            password = "123456Mm",
            type = UserTypeRole.ROLE_COMMON
        )

        every { repository.existsByEmail(dto.email) } returns true

        val exception = assertThrows<EmailAlreadyExistsException> {
            service.register(dto)
        }

        assertEquals("Email já cadastrado", exception.message)

        verify(exactly = 0) { repository.save(any()) }
    }

    @Test
    @DisplayName("Erro ao cadastrar usuário menor de idade")
    fun `deve lancar erro quando a idade for menor que a maioridade`() {
        val dto = RegisterDTO(
            email = "test@test.com",
            name = "teste",
            age = 17,
            password = "123456Mm",
            type = UserTypeRole.ROLE_COMMON
        )

        every { repository.existsByEmail(dto.email) } returns false

        val exception = assertThrows<UnderageUserException> {
            service.register(dto)
        }

        assertEquals("Usuário não possui idade mínima para criar conta", exception.message)

        verify(exactly = 0) { repository.save(any()) }
    }
}
