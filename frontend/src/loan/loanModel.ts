import Inventory from '../inventory/inventoryModel';
import LibraryCard from '../libraryCard/libraryCard';
import LoanState from './loanStateModel';

export default interface Loan{
    loanId: number;
    inventory: Inventory;
    libraryCard: LibraryCard;
    loanState: LoanState;
    date: Date;
}