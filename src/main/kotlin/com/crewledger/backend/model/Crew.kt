package com.crewledger.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "crews")
class Crew(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var crewType: String,

    @Column(nullable = false)
    var tier: Int = 0,

    @Column(nullable = false)
    var heat: Int = 0,

    @Column(nullable = false)
    var wantedLevel: Int = 0,

    @Column(nullable = false)
    var reputation: Int = 0,

    @Column(nullable = false)
    var coin: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    val campaign: Campaign
)