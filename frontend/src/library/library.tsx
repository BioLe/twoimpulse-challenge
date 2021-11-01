import React, { useContext, useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import Library from "../library/libraryModel";
import LibraryService from '../library/libraryService';
import {Typography, Grid, Paper, Button, Card, CardMedia, CardActionArea, CardContent} from '@mui/material';
import { Link } from 'react-router-dom';
import config from '../config/config';
import PaginatedResponse from "../common/models/PaginatedResponse";
import LibraryList from "../library/components/libraryList";
import Inventory from "../inventory/inventoryModel";
import InventoryService from "../inventory/inventoryService";
import InventoryStatus from '../inventory/inventoryStatus';
import InventoryList from "../inventory/components/InventoryList";
import LoanTable from "../loan/components/loanTable";
import LoanService from "../loan/loanService";
import Loan from "../loan/loanModel";
import LoanIdTypeEnum from "../common/models/LoanIdTypeEnum";
import CampaignService from "../campaign/campaignService";
import Campaign from '../campaign/campaignModel';

import Carousel from 'react-material-ui-carousel';
import LibraryCardService from "../libraryCard/libraryCardService";
import { user } from "../user/user";
import { UpdateUserLibraryCards } from "../user/user.actions";

function Item(props:any)
{
    return (
        <Card style={{height: '100%', width: '100%'}}>
            <CardActionArea>
                <CardMedia
                    component="img"
                    height="200"
                    src={`data:image/jpeg;base64, ${props.item.banner}`}
                    width="100"
                    alt="banner"
                />
                <CardContent style={{ display:'flex', justifyContent:'center' }} >
                    <Grid container direction="column" alignItems="center" justifyContent="center">
                        <Grid item xs={3}>
                            <Typography gutterBottom variant="h5" component="div">
                                {props.item.title} 
                            </Typography>
                        </Grid>
                        <Grid item xs={3}>
                            <Typography gutterBottom variant="caption" component="div">
                                Click to know more
                            </Typography>
                        </Grid>
                    </Grid>
                </CardContent>
            </CardActionArea>
        </Card>
    )
}

const LibraryPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    const {state: {userDetails}, dispatch} = useContext(user);
    const MAX_NUM_OF_BOOKS = 3;
    const PAGE_INIT = 0;
    const [libraryId, setLibraryId] = useState<number>();
    const [inventory, setInventory] = useState<PaginatedResponse<Inventory>>();
    const [loans, setLoans] = useState<PaginatedResponse<Loan>>();
    const [campaigns, setCampaigns] = useState<Campaign[]>();
    const [doesUserHaveLibraryCard, setDoesUserHaveLibraryCard] = useState<boolean>();
    const [updatePage, setUpdatePage] = useState<boolean>(false);

    useEffect( () => {
        let libId = props.match.params.libraryId;
        setLibraryId(libId);

        CampaignService.getCampaigns(libId).then( campaigns => {
            console.log("Campaigns: ", campaigns);
            setCampaigns(campaigns);
        });      

        InventoryService.getInventoryByLibraryId(libId, InventoryStatus.ALL, PAGE_INIT, MAX_NUM_OF_BOOKS).then( inventory => {
            setInventory(inventory);
        });

        LoanService.getLoans({libraryId: libId, id: libId, idType:LoanIdTypeEnum.LIBRARY_ID}).then( loans => {
            setLoans(loans);
        });

    }, []);

    useEffect( () => {
        if(userDetails && userDetails != null && userDetails.libraryCards != null){
            if(userDetails!.libraryCards!.length > 0){
                let libCards = userDetails.libraryCards;
                let libCard = (libCards.filter(libCard => libCard.library.l_ID == libraryId));
                if(libCard.length > 0){
                    setDoesUserHaveLibraryCard(true);
                }
            } else {
                setDoesUserHaveLibraryCard(false);
            }
        }
    })

    useEffect( () => {
        
    }, [campaigns, loans, inventory])

    const handleJoinLibrary = () => {
        console.log("join library");
        if(libraryId && userDetails != null){
            console.log("PersonId", userDetails.userId);
            LibraryCardService.createLibraryCard(libraryId, userDetails.userId).then( resp => {
                console.log("LibraryCardCreated: ", resp.data);
                if(resp.status === 201){
                    dispatch(UpdateUserLibraryCards(resp.data));
                    setDoesUserHaveLibraryCard(true);
                }
            });
        }
    };

    return (
        <div>
            
            <Navigation />
            <Grid container direction="column" alignItems="center" justifyContent="center">
            
                
                    <Typography variant="h4" my={3}>Campaigns</Typography>
                    {campaigns && campaigns.length > 0 
                        ?
                            <Grid container direction="row" alignItems="center" justifyContent="center">
                                <Grid item xs={10} mx={3}>
                                    
                                        <Carousel 
                                            navButtonsProps={{          // Change the colors and radius of the actual buttons. THIS STYLES BOTH BUTTONS
                                                style: {
                                                    backgroundColor: 'lightblue',
                                                    borderRadius: 100
                                                }
                                            }} 
                                            navButtonsAlwaysVisible={true}>
                                            {campaigns.map( (camp, i) => {
                                                return (
                                                    <Link  key={`link_${i}`} to={`/library/${libraryId}/campaign/${camp.campaignId}`} style={{ textDecoration: 'none' }}>
                                                        <Item key={`carrousel_${i}`} item={camp} />
                                                    </Link>
                                                    );
                                            })}
                                        </Carousel>
                                    
                                </Grid>
                            </Grid>
                        :
                        <Typography variant="body1">No active campaigns at the moment</Typography>
                    }
               
                
                <Grid item xs={10} my={3}>
                    <Typography variant="h4">Inventory</Typography>
                </Grid>
                <Grid item xs={12}>
                    <Grid item xs={12} mx={2}>
                        {(libraryId && inventory?.totalElements != 0)  
                            ?
                            <InventoryList inventory={inventory} libraryId={libraryId}/>
                            :
                            <Typography variant="h6">No inventory available right now..</Typography>
                            }
                        {inventory && inventory.totalElements > MAX_NUM_OF_BOOKS &&
                            <Grid container direction="column" alignItems="center" justifyContent="center">
                                <Grid item xs={12}> 
                                    <Button component={Link} to={`/library/${libraryId}/inventory`} variant="contained">Show All</Button>
                                </Grid>
                            </Grid>
                        }
                    </Grid>
                </Grid>
                <Grid item xs={12} my={3}>
                    <Typography variant="h4">Loan History</Typography>
                </Grid>
                <LoanTable loans={loans} includeBookDetails={true} includeLibraryDetails={true}/>

                {/* Create Library Card */}
                { !doesUserHaveLibraryCard && 
                    <div>
                        <Grid item xs={12} my={3}>
                            <Typography variant="h4">Join us!</Typography>
                        </Grid>
                        <Grid item xs={12} my={3}>
                            <Button onClick={handleJoinLibrary} variant="contained">Create Library Card</Button>
                        </Grid>
                    </div>
                }
                
                
            </Grid>
        </div>
    );
}

export default LibraryPage;