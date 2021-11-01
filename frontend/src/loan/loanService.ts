import { AutoFixOffSharp } from "@mui/icons-material";
import PaginatedResponse from '../common/models/PaginatedResponse';
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'
import LoanStateEnum from "./loanStateEnum";
import Loan from "./loanModel";
import LoanIdTypeEnum from "../common/models/LoanIdTypeEnum";

interface LoanParameters {
    libraryId: number, 
    id:string, 
    idType?: LoanIdTypeEnum,
    loanState?:LoanStateEnum, 
    pageNo?:number, 
    pageSize?:number, 
    sortBy?:string
}

const getLoans = async(loanParams:LoanParameters): Promise<PaginatedResponse<Loan>> => {
    try{
        const params = { params: 
            {
            ...(loanParams.idType    ? {idType    : loanParams.idType}    : {}),
            ...(loanParams.loanState ? {loanState : loanParams.loanState} : {}),
            ...(loanParams.pageNo    ? {pageNo    : loanParams.pageNo}    : {}),
            ...(loanParams.pageSize  ? {pageSize  : loanParams.pageSize}  : {}),
            ...(loanParams.sortBy    ? {sortBy    : loanParams.sortBy}    : {})
            }
        }
        
        const response: AxiosResponse<PaginatedResponse<Loan>> = 
            await axios.get(`${config.server.url}/api/v1/library/${loanParams.libraryId}/loans/${loanParams.id}`, params);
        
        if(response.status === 200){
            console.log("Response: ", response);
            return response.data;
        }
    }
    catch (error){
        console.log("error in getLoans");
    }

    return Promise.reject("no elements found");
}

const getLoansByPersonId = async(personId: number): Promise<Loan[]> => {
    try{
        
        const response: AxiosResponse<Loan[]> = 
            await axios.get(`${config.server.url}/api/v1/person/${personId}/loans`);
        
        if(response.status === 200){
            console.log("Response: ", response);
            return response.data;
        }
    }
    catch (error){
        console.log("error getting loans per person");
    }

    return Promise.reject("no elements found");
}

const borrowBook = async(libraryId: number, libraryCardId: number, inventoryId: number): Promise<any> => {
    try{
        
        const response: AxiosResponse<any> = 
            await axios.post(`${config.server.url}/api/v1/library/${libraryId}/loans/borrow/${inventoryId}?libraryCardId=${libraryCardId}`);
        
        if(response.status === 200){
            console.log("Response: ", response);
            return response.data;
        }
    }
    catch (error){
        console.log("error borrowing book");
    }

    return Promise.reject("Something went wrong");
}

const returnBook = async(libraryId: number, libraryCardId: number, inventoryId: number): Promise<any> => {
    try{
        
        const response: AxiosResponse<any> = 
            await axios.post(`${config.server.url}/api/v1/library/${libraryId}/loans/return/${inventoryId}?libraryCardId=${libraryCardId}`);
        
        if(response.status === 200){
            console.log("Response: ", response);
            return response.data;
        }
    }
    catch (error){
        console.log("error returning book");
    }

    return Promise.reject("Something went wrong");
}



const LoanService = {
    getLoans,
    borrowBook,
    returnBook
};

export default LoanService;