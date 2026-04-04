package com.crewledger.backend.service

import com.crewledger.backend.dto.CampaignRequest
import com.crewledger.backend.dto.CampaignResponse
import com.crewledger.backend.exception.Exceptions
import com.crewledger.backend.model.Campaign
import com.crewledger.backend.repository.CampaignRepository
import com.crewledger.backend.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CampaignService(
    private val campaignRepository: CampaignRepository,
    private val userRepository: UserRepository
) {

    fun getCurrentUser() = userRepository.findByUsername(
        SecurityContextHolder.getContext().authentication!!.name
    ) ?: throw RuntimeException("Authenticated user not found in database")

    @Transactional(readOnly = true)
    fun getAllCampaigns(): List<CampaignResponse> {
        val user = getCurrentUser()
        return campaignRepository.findByOwner(user).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getCampaignById(id: Long): CampaignResponse {
        val user = getCurrentUser()
        val campaign = campaignRepository.findById(id)
            .orElseThrow { Exceptions.NotFoundException("Campaign not found") }

        if (campaign.owner.id != user.id) {
            throw Exceptions.AccessDeniedException("Access denied")
        }

        return campaign.toResponse()
    }

    @Transactional
    fun createCampaign(request: CampaignRequest): CampaignResponse {
        val user = getCurrentUser()

        val campaign = Campaign(
            name = request.name,
            description = request.description,
            owner = user
        )

        return campaignRepository.save(campaign).toResponse()
    }

    @Transactional
    fun updateCampaign(id: Long, request: CampaignRequest): CampaignResponse {
        val user = getCurrentUser()
        val campaign = campaignRepository.findById(id)
            .orElseThrow { Exceptions.NotFoundException("Campaign not found") }

        if (campaign.owner.id != user.id) {
            throw Exceptions.AccessDeniedException("Access denied")
        }

        campaign.name = request.name
        campaign.description = request.description

        return campaignRepository.save(campaign).toResponse()
    }

    @Transactional
    fun deleteCampaign(id: Long) {
        val user = getCurrentUser()
        val campaign = campaignRepository.findById(id)
            .orElseThrow { Exceptions.NotFoundException("Campaign not found") }

        if (campaign.owner.id != user.id) {
            throw Exceptions.AccessDeniedException("Access denied")
        }

        campaignRepository.delete(campaign)
    }


    //Helper function
    private fun Campaign.toResponse() = CampaignResponse(
        id = id,
        name = name,
        description = description,
        ownerUsername = owner.username
    )
}