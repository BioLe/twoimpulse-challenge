import Library from "../library/libraryModel";
import LibraryCard from "../libraryCard/libraryCard";
import Campaign from "./campaignModel";


export default interface CampaignSubscription{
    campaignId: {campaignId:number, libraryCardId:number};
    campaign: Campaign;
    libraryCard: LibraryCard;
    isUserNew: boolean;
}