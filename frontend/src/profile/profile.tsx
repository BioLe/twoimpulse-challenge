import React, { useContext, useEffect, useState } from "react";
import { Redirect, RouteComponentProps } from "react-router";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import Library from "../library/libraryModel";
import LibraryService from '../library/libraryService';
import {Typography, Grid, Pagination, Button, Accordion, AccordionSummary, AccordionDetails, Chip} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Link } from 'react-router-dom';
import config from '../config/config';
import PaginatedResponse from "../common/models/PaginatedResponse";
import LoanTable from "../loan/components/loanTable";
import LoanService from "../loan/loanService";
import Loan from "../loan/loanModel";
import LibraryCardService from "../libraryCard/libraryCardService";
import LibraryCard from "../libraryCard/libraryCard";
import {CalendarDatum, ResponsiveCalendar} from "@nivo/calendar";
import MyDate from "../common/helpers/MyDate";
import moment from 'moment';
import LoanIdTypeEnum from "../common/models/LoanIdTypeEnum";
import { user } from "../user/user";

// let loanColumns = [
//     { field: 'id', headerName: 'LoanID', width: 125 },
//     { field: 'libraryId', headerName: 'LibraryID', width: 150, type: 'string'},
//     { field: 'inventoryId', headerName: 'InventoryID', width: 150},
//     { field: 'title', headerName: 'BookTitle', width: 250, type: 'string'},
//     { field: 'loanState', headerName: 'State', width: 140, renderCell: (params: any) => (
//         <Chip label={params.value.label} color={params.value.color} />
//     )},
//     { field: 'date', headerName: 'ActionDate', width: 250, type: 'date'}
// ];


const ProfilePage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    const SUBSCRIPTION_DURATION_IN_MONTHS = 1;
    
    const {state: {userDetails}} = useContext(user);
    const [loans, setLoans] = useState<Map<number, PaginatedResponse<Loan>>>(new Map());
    const [libCards, setLibCards] = useState<LibraryCard[]|null>(null);
    const [expanded, setExpanded] = useState<string|boolean>(false);
    const [subscriptionData, setSubscriptionData] = useState<Map<number, CalendarDatum[]>>(new Map());
    const [updateSubscription, setUpdateSubscription] = useState<boolean>(false);
    const [minDate, setMinDate] = useState<string>(moment().subtract(6, 'months').format('YYYY-MM-DD'));
    const [maxDate, setMaxDate] = useState<string>(moment().add(6, 'months').format('YYYY-MM-DD'));
    
    const handleChange = (panel:string) => (event:any, isExpanded:any) => {
        setExpanded(isExpanded ? panel : false);
    };

    useEffect( () => {

        if(userDetails){
            setLibCards(userDetails.libraryCards);
            userDetails?.libraryCards?.map( lCard => {
                let libraryCardId = lCard.libraryCardId;
                let libraryId = lCard.library.l_ID;
    
                let subValues:CalendarDatum[] = [];
    
                LibraryCardService.getLibraryCardSubscriptionByLibraryIdAndLibraryCardId(libraryId, libraryCardId)
                    .then( subs => {
                        /* Fill array with dates between stard and end of subscription */
                        subs.forEach( sub => {
                            let date:Date = new Date(sub.startDate);
                            let endSubDate = new Date(sub.endDate);
    
                            let md =  new MyDate();
                            let daysBetween: Date[] = md.getDates(date, endSubDate);
                            
                            daysBetween.forEach(day => {
                                let newDate = `${day.getFullYear()}-${ (day.getMonth()+1).toString().padStart(2, "0")}-${day.getDate().toString().padStart(2, "0")}`
                                subValues.push({value: 1, day: newDate.toString()});
                            })
                        })  
                    }).then( () => {
                        setSubscriptionData(new Map(subscriptionData.set(libraryCardId, subValues)));
                });
    
                LoanService.getLoans({libraryId: libraryId, id: (libraryCardId+""), idType:LoanIdTypeEnum.LIBRARY_CARD_ID}).then( loansFetched => {
                    console.log("loansFetched: ", loansFetched);
                    setLoans(new Map(loans.set(libraryCardId, loansFetched)));
                });
            })
        }
   
    }, [updateSubscription]);


    function getSubData(libraryCardId: number): CalendarDatum[] {
        let sData = subscriptionData.get(libraryCardId);
        return (sData) ? sData : [];
    }
    
    function handleSubscriptionRenewal(event:any, libCard:LibraryCard){
        LibraryCardService.renewLibraryCardSubscription(libCard.library.l_ID, libCard.libraryCardId).then(res => {
            console.log(res);
            setUpdateSubscription(!updateSubscription);
        })
    }
    
    if(!userDetails) return <Redirect to="/sign-in" />;

    return (
        <div>
            <Navigation />
            <Grid container direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={12} my={3}>
                    <Typography variant="h4">Library Cards</Typography>
                </Grid>
                { libCards 
                    ? 
                        libCards.map(libCard => (
                            
                            <Grid container direction="row" alignItems="center" justifyContent="center" key={`gridCont${libCard.libraryCardId}`}> 
                                <Grid item xs={10} my={1} key={`grid${libCard.libraryCardId}`}>
                                    <Accordion expanded={expanded === `panel${libCard.libraryCardId}`} onChange={handleChange(`panel${libCard.libraryCardId}`)}>
                                        <AccordionSummary
                                            
                                            expandIcon={<ExpandMoreIcon />}
                                            aria-controls={`panel${libCard.libraryCardId}bh-content`}
                                            id={`panel${libCard.libraryCardId}bh-header`}
                                        >
                                            <Typography sx={{ width: '33%', flexShrink: 0 }}>
                                                CardID: {libCard.libraryCardId}
                                            </Typography>
                                            <Typography  sx={{ color: 'text.secondary' }}>{libCard.library.name}</Typography>
                                        </AccordionSummary>
                                        <AccordionDetails>

                                        <div style={{height: '350px', width: '100%'}}>
                                            {subscriptionData && 
                                                <ResponsiveCalendar          
                                                    data={getSubData(libCard.libraryCardId)}
                                                    from={minDate}
                                                    to={maxDate}
                                                    emptyColor="#eeeeee"
                                                    colors={[ '#67f460' ]}
                                                    margin={{ top: 40, right: 40, bottom: 40, left: 40 }}
                                                    yearSpacing={40}
                                                    monthBorderColor="#ffffff"
                                                    dayBorderWidth={2}
                                                    dayBorderColor="#ffffff"
                                                    legends={[
                                                        {
                                                            anchor: 'bottom-right',
                                                            direction: 'row',
                                                            translateY: 36,
                                                            itemCount: 4,
                                                            itemWidth: 42,
                                                            itemHeight: 36,
                                                            itemsSpacing: 14,
                                                            itemDirection: 'right-to-left'
                                                        }
                                                    ]}  
                                                />
                                            }
                                        </div>    
                                        <Grid container direction="column" alignItems="center" justifyContent="center" key={`gridContSub${libCard.libraryCardId}`}> 
                                            <Grid item xs={12} my={1} key={`gridSub${libCard.libraryCardId}`}>
                                                <Button value={libCard.libraryCardId} variant="contained" onClick={ e => handleSubscriptionRenewal(e, libCard)}>Renew Subscription</Button>
                                            </Grid>
                                            <Grid item xs={12} my={3}>
                                                <Typography variant="h4">Loan History</Typography>
                                                
                                            </Grid>
                                            <LoanTable loans={loans.get(libCard.libraryCardId)} includeBookDetails={true} includeLibraryDetails={false}/>
                                        </Grid>
                                        
                                        </AccordionDetails>
                                    </Accordion>
                                </Grid>
                            </Grid>
                        ))  
                    :
                    <Grid item xs={12} my={3}>
                        <Typography variant="body1">No cards available, go to a library and subscribe to one!</Typography>
                    </Grid>
                } 
            </Grid>
        </div>
    );
}

export default ProfilePage;