package com.crewledger.backend.controller

import com.crewledger.backend.dto.AuthResponse
import com.crewledger.backend.dto.LoginRequest
import com.crewledger.backend.dto.RegisterRequest
import com.crewledger.backend.model.User
import com.crewledger.backend.model.UserRole
import com.crewledger.backend.repository.UserRepository
import com.crewledger.backend.security.JwtService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        if (userRepository.existsByUsername(request.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        if(userRepository.existsByEmail(request.email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        val role = try {
            UserRole.valueOf(request.role.uppercase())
        } catch (e: IllegalArgumentException) {
            UserRole.PLAYER
        }

        //Non-null assertion heere for the passwordHash due to Java interop error -- BCrypt will never return null
        val user = User(
            username = request.username,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password)!!,
            role = role
        )

        userRepository.save(user)

        val token = jwtService.generateToken(user.username, user.role.name)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            AuthResponse(token, user.username, user.role.name)
        )
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val user = userRepository.findByUsername(request.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val token = jwtService.generateToken(user.username, user.role.name)
        return ResponseEntity.ok(
        AuthResponse(token, user.username, user.role.name)
        )
    }
}