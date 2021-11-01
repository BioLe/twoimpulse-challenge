import LibraryCard from './libraryCard';

export default interface LibraryCardSubscription{
    libraryCardId: number;
    libraryCard: LibraryCard;
    startDate: Date;
    endDate: Date;
}