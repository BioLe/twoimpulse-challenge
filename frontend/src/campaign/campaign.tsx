import React, { useContext, useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import {Typography, Grid, Button, Card, CardActionArea, CardMedia, CardContent, Chip} from '@mui/material';
import PaginatedResponse from "../common/models/PaginatedResponse";
import LoanTable from "../loan/components/loanTable";
import Loan from "../loan/loanModel";
import LoanService from "../loan/loanService";
import LoanIdTypeEnum from "../common/models/LoanIdTypeEnum";
import InventoryService from '../inventory/inventoryService';
import Inventory from "../inventory/inventoryModel";
import LoanStateEnum from "../loan/loanStateEnum";
import LibraryCardService from '../libraryCard/libraryCardService';
import LibraryCard from '../libraryCard/libraryCard';
import CampaignService from "./campaignService";
import Campaign from "./campaignModel";
import CampaignSubscriptionService from "./campaignSubscriptionService";
import CampaignSubscription from './campaignSubscriptionModel';
import FiberNewIcon from '@mui/icons-material/FiberNew';
import FaceIcon from '@mui/icons-material/Face';
import { user } from "../user/user";

interface Subs{
    libraryCardId: number;
    name: string;
    isUserNew: boolean;
}

const CampaignPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {
    
    const {state: {userDetails}} = useContext(user);
    const [libraryId, setLibraryId] = useState<number>();
    const [campaignId, setCampaignId] = useState<number>();
    const [campaign, setCampaign] = useState<Campaign>();
    const [subscribers, setSubscribers] = useState<Subs[]>();
    const [libraryCard, setLibraryCard] = useState<LibraryCard|null>();
    const [updateMembers, setUpdateMembers] = useState<boolean>(false);
    const [alreadySubbed, setAlreadySubbed] = useState<boolean>(false);

    useEffect( () => {

        let libId = props.match.params.libraryId;
        let campId = props.match.params.campaignId;
        let lCard:any = null;

        setLibraryId(libId);
        setCampaignId(campId);

        CampaignService.getCampaignById(libId, campId).then( camp => {
            setCampaign(camp);
        })

        if(userDetails != null && userDetails.libraryCards != null){
            if(userDetails.libraryCards.length > 0){
                let libCards = userDetails.libraryCards;
                let libCard = (libCards.filter(libCard => libCard.library.l_ID == libId));
                if(libCard.length > 0){
                    lCard = libCard[0];
                    setLibraryCard(lCard);
                }
            } else {
                setLibraryCard(null);
            }
            
        }

        CampaignSubscriptionService.getCampaignSubscriptionsByCampaignId(libId, campId).then( subs => {
            
            let subIds:number[] = [];

            let subChipsInfo:Subs[] = subs.map(sub => {
                let lcID = sub.libraryCard.libraryCardId;
                subIds.push(lcID);
                return ({libraryCardId: lcID, isUserNew: sub.isUserNew, name: ""} as Subs);
            });

            console.log("lCard: ", lCard);

            if(lCard != null) setAlreadySubbed(subIds.includes(lCard.libraryCardId));

            LibraryCardService.getLibraryCardsByIds(libId, subIds).then( libraryCards => {
                let lcMap:Map<number,string> = new Map();
                libraryCards.map( lCard => lcMap.set(lCard.libraryCardId, lCard.person.name!));
                subChipsInfo = subChipsInfo.map(sub => ({...sub, name: (lcMap.get(sub.libraryCardId)!)}));
                setSubscribers(subChipsInfo);
            })
        });

        console.log("AlreadySubbed? ", alreadySubbed);
        console.log("libraryCardL ", libraryCard);

    }, [updateMembers]);

    function handleJoinCampaign(event:any){
        console.log("join");
        if(libraryId && campaignId && libraryCard ){
            console.log("LibraryId: ", libraryId);
            console.log("CampaignId: ", campaignId);
            console.log("LibraryCard: ", libraryCard);
            CampaignSubscriptionService.subscribeToCampaign(libraryId, campaignId, libraryCard.libraryCardId).then(sub => {
                setUpdateMembers(!updateMembers);
            })
        }
        //PoCampaignSubscriptionService.subscribeToCampaign(libraryId)
    }

    return (
        <div>
            <Navigation />
            
                {campaign && 
                    <Grid container direction="column" alignItems="center" justifyContent="center">
                        <Grid item xs={12} my={2}>  
                            <CardMedia
                                component="img"
                                src={`data:image/jpg+png;base64,${campaign.banner}`}
                                alt="book_cover"
                            />
                        </Grid>
                        <Grid item xs={12} my={3}>
                            <Typography variant="h4">{campaign.title}</Typography>
                        </Grid>
                        <Grid item xs={12} my={2}>
                            <Typography variant="body1">{campaign.description}</Typography>
                        </Grid>
                        <Grid item xs={12} my={2}>
                            { libraryCard != null && !alreadySubbed &&
                                <Button value={campaign.campaignId} variant="contained" onClick={handleJoinCampaign}>Join</Button>
                            }
                            
                        </Grid>
                        <Grid item xs={12} my={2}>
                            <Typography variant="h4">Campaign members</Typography>
                        </Grid>
                        <Grid item xs={12} my={2}>
                            {subscribers ?
                                subscribers.map( (sub,i) => (
                                        <Chip key = {`chip_${i}`}
                                            icon  = {sub.isUserNew ? <FiberNewIcon /> : <FaceIcon/>} 
                                            label = {`${sub.name}`} 
                                            color = {sub.isUserNew ? "success" : "primary"}
                                            style = {{marginRight:10}}
                                        />
                                    
                                ))
                                :
                                <Typography variant="body1">No members.. but be the first to join!</Typography>
                            }
                        </Grid>
                    </Grid>
                }
                
            
        </div>
    );
}

export default CampaignPage;