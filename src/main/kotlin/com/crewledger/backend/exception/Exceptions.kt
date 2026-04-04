package com.crewledger.backend.exception

class Exceptions {
    class NotFoundException(message: String) : RuntimeException(message)
    class AccessDeniedException(message: String) : RuntimeException(message)
}