package com.twoimpulse.challenge.campaign;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/library/{libraryId}/campaign")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns(@PathVariable long libraryId){
        Optional<List<Campaign>> allCampaigns = campaignService.getAllCampaigns(libraryId);
        if(allCampaigns.isPresent()){
            return ResponseEntity.ok(allCampaigns.get());
        }

        throw new EntityNotFoundException("No campaigns found");

    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable long libraryId, @PathVariable long campaignId){
        Optional<Campaign> campaign = campaignService.getCampaign(libraryId, campaignId);
        if(campaign.isPresent()){
            return ResponseEntity.ok(campaign.get());
        }
        log.error("No campaign found for id " + campaignId);
        throw new EntityNotFoundException("No campaign found for id " + campaignId);

    }

}
