import { AutoFixOffSharp } from "@mui/icons-material";
import config from '../config/config';
import axios from 'axios';
import { AxiosResponse } from 'axios'
import Person from "../person/personModel";

const BASE_URL = `${config.server.url}/api/v1/person/auth`


const signIn = async(person:Person): Promise<AxiosResponse> => {
    try{        
        const response: AxiosResponse<AxiosResponse> = await axios.post(`${BASE_URL}/sign-in`, person);
        
        if(response.status === 200){
            return response;
        }
        
        
    }
    catch (error){
        console.log("error during sign in");
        return error.response;
    }

    return Promise.reject("no elements found");
}

const signUp = async(person:Person): Promise<AxiosResponse> => {
    try{        

        console.log("PersonFormData ", person);
        const response: AxiosResponse = await axios.post(`${BASE_URL}/sign-up`, person);
        
        if(response.status === 201){
            return response;
        }
    }
    catch (error){
        console.log("error during sign in", error.response.data);
        return error.response;
    }

    return Promise.reject("no elements found");
}

const AuthService = {
    signIn,
    signUp
    //logout,
    
};

export default AuthService;