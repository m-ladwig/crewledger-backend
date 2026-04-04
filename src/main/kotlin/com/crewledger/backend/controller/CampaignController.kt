package com.crewledger.backend.controller

import com.crewledger.backend.dto.CampaignRequest
import com.crewledger.backend.dto.CampaignResponse
import com.crewledger.backend.service.CampaignService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.spi.ResolveResult

@RestController
@RequestMapping("api/campaigns")
class CampaignController(
    private val campaignService: CampaignService
) {

    @GetMapping
    fun getAllCampaigns(): ResponseEntity<List<CampaignResponse>> {
        return ResponseEntity.ok(campaignService.getAllCampaigns())
    }

    @GetMapping("/{id}")
    fun getCampaignById(@PathVariable id: Long): ResponseEntity<CampaignResponse> {
        return ResponseEntity.ok(campaignService.getCampaignById(id))
    }

    @PostMapping()
    fun createCampaign(
        @Valid @RequestBody request: CampaignRequest
    ): ResponseEntity<CampaignResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(campaignService.createCampaign(request))
    }

    @PutMapping("/{id}")
    fun updateCampaign(
        @PathVariable id: Long,
        @Valid @RequestBody request: CampaignRequest
    ): ResponseEntity<CampaignResponse> {
        return ResponseEntity.ok(campaignService.updateCampaign(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteCampaign(@PathVariable id: Long): ResponseEntity<Unit> {
        campaignService.deleteCampaign(id)
        return ResponseEntity.noContent().build()
    }
}