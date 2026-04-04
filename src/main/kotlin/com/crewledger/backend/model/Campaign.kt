package com.crewledger.backend.model

import jakarta.persistence.*
import javax.xml.stream.events.Characters

@Entity
@Table(name = "campaigns")
class Campaign(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: User,

    @OneToOne(mappedBy = "campaign", cascade = [CascadeType.ALL])
    var crew: Crew? = null,

    @OneToMany(mappedBy = "campaign", cascade = [CascadeType.ALL])
    val characters: MutableList<Character> = mutableListOf()
)