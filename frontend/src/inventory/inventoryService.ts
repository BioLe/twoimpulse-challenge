import { AutoFixOffSharp } from "@mui/icons-material";
import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'

import Inventory from "./inventoryModel";
import Library from '../library/libraryModel';

const getInventoryByLibraryId = async(libraryId: number, status?:string, pageNo?:number, pageSize?:number): Promise<PaginatedResponse<Inventory>> => {
    try{
        const params = { params: 
            {
            ...(status ? {status: status} : {}),
            ...(pageNo ? {pageNo: pageNo} : {}),
            ...(pageSize ? {pageSize: pageSize} : {}),
            }
        }
        
        const response: AxiosResponse<PaginatedResponse<Inventory>> = 
            await axios.get(`${config.server.url}/api/v1/library/${libraryId}/inventory`, params);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getInventory");
    }

    return Promise.reject("no elements found");
}

const getInventoryByLibraryIdAndInventoryId = async(libraryId: number, inventoryId:number): Promise<Inventory> => {
    try{       
        const response: AxiosResponse<Inventory> = 
            await axios.get(`${config.server.url}/api/v1/library/${libraryId}/inventory/${inventoryId}`);
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getInventoryById");
    }

    return Promise.reject("no elements found");
}


const InventoryService = {
    getInventoryByLibraryId,
    getInventoryByLibraryIdAndInventoryId
};

export default InventoryService;