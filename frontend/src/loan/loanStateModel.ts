import Inventory from '../inventory/inventoryModel';
import LoanStateEnum from './loanStateEnum';

export default interface LoanState{
    loanStateId: number;
    state: LoanStateEnum;
}