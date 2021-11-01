import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'
import LibraryCard from "./libraryCard";
import LibraryCardSubscription from './libraryCardSubscriptionModel';

const getLibraryCardsByPerson = async(personId:number): Promise<LibraryCard[]> => {
    try{

        let url = `${config.server.url}/api/v1/person/${personId}/library-cards`;

        const response: AxiosResponse<LibraryCard[]> = await axios.get(url );
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getLibraryCardsByPerson", error);
    }

    return Promise.reject("no elements found");
}

const getLibraryCardSubscriptionByLibraryIdAndLibraryCardId = async(libraryId:number, libraryCardId:number): Promise<LibraryCardSubscription[]> => {
    try{

        
        let url = `${config.server.url}/api/v1/library/${libraryId}/library-card/${libraryCardId}/subscription/history`;

        const response: AxiosResponse<LibraryCardSubscription[]> = await axios.get(url );
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getLibraryCardsByPerson", error);
    }

    return Promise.reject("no elements found");
}

const renewLibraryCardSubscription = async(libraryId:number, libraryCardId:number): Promise<any> => {

    try{

        let url = `${config.server.url}/api/v1/library/${libraryId}/library-card/${libraryCardId}/subscription`;

        const response: AxiosResponse<any> = await axios.post(url);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error renewing subscription", error);
    }

    return Promise.reject("no elements found");

}

const getLibraryCardsByIds = async(libraryId:number, libraryCardIds:number[]): Promise<LibraryCard[]> => {

    try{

        const params = { params: { ids: libraryCardIds.join(',')}};


        let url = `${config.server.url}/api/v1/library/${libraryId}/library-card`;

        const response: AxiosResponse<LibraryCard[]> = await axios.get(url, params);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error getting library cards", error);
    }

    return Promise.reject("no elements found");

}

const isSubscriptionActive = async(libraryId:number, libraryCardId:number): Promise<boolean> => {
    try{

        let url = `${config.server.url}/api/v1/library/${libraryId}/library-card/${libraryCardId}/subscription/status`;

        const response: AxiosResponse<boolean> = await axios.get(url);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error verifying subscription", error);
    }

    return Promise.reject("no elements found");
}

const createLibraryCard = async(libraryId:number, personId: number): Promise<any> => {
    try{

        let url = `${config.server.url}/api/v1/library/${libraryId}/library-card?personId=${personId}`;

        const response: AxiosResponse<boolean> = await axios.post(url);
        
        if(response.status === 201){
            console.log("SUCESSO");
            return response;
        }
    }
    catch (error){
        console.log("error verifying subscription", error);
    }

    return Promise.reject("no elements found");
}

const LibraryCardService = {
    getLibraryCardsByPerson,
    getLibraryCardSubscriptionByLibraryIdAndLibraryCardId,
    renewLibraryCardSubscription,
    isSubscriptionActive,
    getLibraryCardsByIds,
    createLibraryCard
};

export default LibraryCardService;