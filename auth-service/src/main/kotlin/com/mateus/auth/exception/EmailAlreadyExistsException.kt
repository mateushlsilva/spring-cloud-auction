package com.mateus.auth.exception

class EmailAlreadyExistsException (
    message: String = "Email já cadastrado"
) : RuntimeException(message)