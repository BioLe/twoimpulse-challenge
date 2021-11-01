import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'

import Campaign from "./campaignModel";

const getCampaigns = async(libraryId:number): Promise<Campaign[]> => {
    try{

        const url = `${config.server.url}/api/v1/library/${libraryId}/campaign`;
        const response: AxiosResponse<Campaign[]> = await axios.get(url);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getCampaigns");
    }

    return Promise.reject("no elements found");
}

const getCampaignById = async(libraryId:number, campaignId:number): Promise<Campaign> => {
    try{

        const url = `${config.server.url}/api/v1/library/${libraryId}/campaign/${campaignId}`;
        const response: AxiosResponse<Campaign> = await axios.get(url);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getCampaign");
    }

    return Promise.reject("no elements found");
}

const CampaignService = {
    getCampaigns,
    getCampaignById
};

export default CampaignService;