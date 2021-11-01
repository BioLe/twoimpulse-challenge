import { createContext, useReducer, Dispatch, useEffect } from "react";
import { Actions, ActionType } from "./user.actions";
import { IUserDetails } from "./user.types";

interface IUserState {
    loading: boolean;
    userDetails: IUserDetails | null;
  }

interface IUserContext {
    state: IUserState;
    dispatch: Dispatch<Actions>;
}

const initialState: IUserState = {
    loading: false,
    userDetails: null
}

let localStorageUserState = localStorage.getItem("userState");
const localState: any = (localStorageUserState) ? JSON.parse(localStorageUserState) : null;

const user = createContext<IUserContext>({
    state: initialState,
    dispatch: () => null
});

const { Provider } = user;

const reducer = (state:IUserState, action: Actions) => {

    console.log("Reducer", state);

    switch (action.type){

        case ActionType.SetUserDetails:
            console.log("State:", state);
            console.log("userDetailsPayload", action.payload);
            return {
                ...state,
                userDetails: action.payload
            };

        case ActionType.UpdateUserLibraryCards:
            return ({
                ...state,
                userDetails: {
                    ...state.userDetails,
                    libraryCards: [...state.userDetails!.libraryCards!, action.payload]
                } 
            }) as IUserState;

        default:
            return state;
    }

}

const UserProvider = ({children}: {children: JSX.Element }) => {

    const [state, dispatch] = useReducer(reducer, localState || initialState);

    console.log("EstadoProvider", state);
    useEffect(() => {
        localStorage.setItem("userState", JSON.stringify(state));
      }, [state]);


    return <Provider value={{state,dispatch}}>{children}</Provider>;
}

export {user, UserProvider};