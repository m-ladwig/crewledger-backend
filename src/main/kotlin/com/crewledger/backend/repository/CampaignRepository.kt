package com.crewledger.backend.repository

import com.crewledger.backend.model.Campaign
import com.crewledger.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CampaignRepository : JpaRepository<Campaign, Long> {
    fun findByOwner(owner: User): List<Campaign>
}
