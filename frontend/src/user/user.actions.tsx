import LibraryCard from "../libraryCard/libraryCard";
import { IUserDetails } from "./user.types";


export enum ActionType {
    SetUserDetails         = 'SET_USER',
    // ResetUser              = 'RESET_USER',
    // GetUser                = 'GET_USER',
    UpdateUserLibraryCards = 'UPDATE_USER_LIBRARY_CARDS'
}

interface ISetUserDetails {type: ActionType.SetUserDetails, payload: IUserDetails};
// interface IResetUser {type: ActionType.ResetUser, payload: {}};
interface IUpdateUserLibraryCards {type: ActionType.UpdateUserLibraryCards, payload: LibraryCard[]}

export type Actions = ISetUserDetails | IUpdateUserLibraryCards;

export const SetUserDetails = (response: IUserDetails): ISetUserDetails => ({
    type: ActionType.SetUserDetails,
    payload: response
});

// export const ResetUser = (): IResetUser => ({
//     type: ActionType.ResetUser,
//     payload: {}
// });

export const UpdateUserLibraryCards = (value: LibraryCard[]): IUpdateUserLibraryCards => ({
    type: ActionType.UpdateUserLibraryCards,
    payload: value
});