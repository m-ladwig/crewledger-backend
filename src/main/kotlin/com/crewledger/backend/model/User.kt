package com.crewledger.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.PLAYER,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    val campaigns: MutableList<Campaign> = mutableListOf()
)

enum class UserRole {
    GM, PLAYER
}