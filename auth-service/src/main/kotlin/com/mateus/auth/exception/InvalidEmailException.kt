package com.mateus.auth.exception

class InvalidEmailException(
    message: String = "Email inválido"
) : RuntimeException(message)
