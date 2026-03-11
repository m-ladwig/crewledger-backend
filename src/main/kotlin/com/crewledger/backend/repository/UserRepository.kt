package com.crewledger.backend.repository

import com.crewledger.backend.model.User
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}