import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'

import Campaign from "./campaignModel";
import CampaignSubscription from './campaignSubscriptionModel';

const getCampaignSubscriptionsByCampaignId = async(libraryId:number, campaignId:number): Promise<CampaignSubscription[]> => {
    try{

        const url = `${config.server.url}/api/v1/library/${libraryId}/campaign/${campaignId}/subscription`;
        const response: AxiosResponse<CampaignSubscription[]> = await axios.get(url);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error getting subscriptions from campaign ", campaignId);
    }

    return Promise.reject("no elements found");
}

const subscribeToCampaign = async(libraryId:number, campaignId:number, libraryCardId:number): Promise<any> => {
    try{

        const params = { params: { libraryCardId: libraryCardId}};

        const url = `${config.server.url}/api/v1/library/${libraryId}/campaign/${campaignId}/subscription/${libraryCardId}`;
        const response: AxiosResponse<any> = await axios.post(url, params);
        
        if(response.status === 201){
            return response.data;
        }
    }
    catch (error){
        console.log("error subscribing to campaign ", campaignId);
    }

    return Promise.reject("error subscribingToCampaign");
}

const CampaignSubscriptionService = {
    getCampaignSubscriptionsByCampaignId,
    subscribeToCampaign
};

export default CampaignSubscriptionService;