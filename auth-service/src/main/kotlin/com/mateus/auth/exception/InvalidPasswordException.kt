package com.mateus.auth.exception

class InvalidPasswordException(
    message: String = "Senha não atende aos requisitos mínimos"
) : RuntimeException(message)