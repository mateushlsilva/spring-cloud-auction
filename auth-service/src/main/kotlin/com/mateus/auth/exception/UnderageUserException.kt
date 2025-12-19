package com.mateus.auth.exception

class UnderageUserException (
    message: String = "Usuário não possui idade mínima para criar conta"
) : RuntimeException(message)