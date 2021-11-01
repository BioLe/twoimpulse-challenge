package com.twoimpulse.challenge.campaign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public Optional<List<Campaign>> getAllCampaigns(long libraryId) {
        return campaignRepository.findAllByLibraryId(libraryId);
    }

    public Optional<Campaign> getCampaign(long libraryId, long campaignId) {
        return campaignRepository.findByIdAndLibraryId(libraryId, campaignId);
    }
}
