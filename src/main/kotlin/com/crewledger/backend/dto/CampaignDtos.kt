package com.crewledger.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CampaignRequest(
    @field:NotBlank(message = "Campaign name is required")
    @field:Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    val name: String,

    val description: String? = null
)

data class CampaignResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val ownerUsername: String
)
