import Library from "../library/libraryModel";

export default interface Campaign{
    campaignId: number;
    library: Library;
    title: string;
    description: string;
    startDate: Date;
    endDate: Date;
    banner: string;
}