import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'

import Library from "./libraryModel";

const getLibraries = async(pageNo?:number, pageSize?:number): Promise<PaginatedResponse<Library>> => {
    try{
        // const response = await axios({
        //     method: 'GET',
        //     url: 
        // });

        // axios.get<ILibrary[]>(`${config.server.url}/api/v1/library`)
        // .then( response => {
        //     if(response.status === 200){
        //         return response.data;
        //     }
        // })

        let url = `${config.server.url}/api/v1/library`;
        if(pageNo && !pageSize){
            url += `?pageNo=${pageNo}`;
        }
        else if(pageSize && !pageNo){
            url += `?pageSize=${pageSize}`;
        }
        else if(pageNo && pageSize){
            url += `?pageSize=${pageSize}&pageNo=${pageNo}`
        }
        
        const response: AxiosResponse<PaginatedResponse<Library>> = await axios.get(url, );
        
        if(response.status === 200){
            return response.data;
        }
    }
    catch (error){
        console.log("error in getLibraries");
    }

    return Promise.reject("no elements found");
}

const LibraryService = {
    getLibraries
};

export default LibraryService;