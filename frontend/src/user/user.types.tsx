import LibraryCard from "../libraryCard/libraryCard";

export interface IUserDetails {
    userId: number;
    name: string;
    libraryCards: LibraryCard[] | null;
}