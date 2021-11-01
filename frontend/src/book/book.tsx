import React, { useContext, useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import {Typography, Grid, Button, Card, CardActionArea, CardMedia, CardContent} from '@mui/material';
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
import { user } from "../user/user";

const BookPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    const MAX_NUM_OF_CAMPAIGNS = 2;
    const MAX_NUM_OF_BOOKS = 3;
    const PAGE_INIT = 0;

    const {state: {userDetails}} = useContext(user);
    const [libraryId, setLibraryId] = useState<number>();
    const [libraryCardId, setLibraryCardId] = useState<number>();
    const [bookId, setBookId] = useState<number>();
    const [loans, setLoans] = useState<PaginatedResponse<Loan>>();
    const [inventory, setInventory] = useState<Inventory>();
    const [availableToLoan, setAvailableToLoan] = useState<boolean>();
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>();
    const [isLibraryCardActive, setIsLibraryCardActive] = useState<boolean>();
    const [doesUserHaveBook, setDoesUserHaveBook] = useState<boolean>();
    const [updatePage, setUpdatePage] = useState<boolean>(false);

    //
    useEffect( () => {

        let libId = props.match.params.libraryId;
        let invId = props.match.params.inventoryId;

        setLibraryId(libId);
        setBookId(invId);

        InventoryService.getInventoryByLibraryIdAndInventoryId(libId, invId).then( inventory => {
            setInventory(inventory);
        });

        LoanService.getLoans({libraryId: libId, id: invId, idType:LoanIdTypeEnum.INVENTORY_ID}).then( loans => {
            if(!loans || loans.content.length == 0){
                //If there's no loan history, its available
                setAvailableToLoan(true);
            } else {
                let available:boolean = 
                    (loans.content[0].loanState.state.toLowerCase() === LoanStateEnum.BORROWED.valueOf()) 
                        ? false : true;
                setAvailableToLoan(available);
            }
            
            setLoans(loans);
        });

        
        if(userDetails != null && userDetails.libraryCards != null){
            setIsAuthenticated(true);
            let libCards = userDetails.libraryCards;
            if(libCards.length > 0){
                let libCard = (libCards.filter(libCard => libCard.library.l_ID == libId));
                let libCardId = libCard[0].libraryCardId;
                setLibraryCardId(libCardId);

                LibraryCardService.isSubscriptionActive(+libId, libCardId).then((isCardActive) => {
                    setIsLibraryCardActive(isCardActive);
                })
            }
        }

    }, [updatePage]);

    useEffect( () => {
        
        if(!availableToLoan && libraryCardId && (loans && loans.content.length > 0)){
            let l:Loan = loans.content[0]; //Get most recent state
            if(l.libraryCard.libraryCardId == libraryCardId){
                console.log("Este user tem este livro");
                setDoesUserHaveBook(true);
            }   
        }

    }, [libraryCardId, availableToLoan, loans])

    function handleLoan(event:any){

        console.log("LibraryId: ", libraryId);
        console.log("LCid", libraryCardId);

        if(libraryId && libraryCardId){
            LoanService
            .borrowBook(libraryId, libraryCardId, event.currentTarget.value)
            .then( loan => {
                setUpdatePage(!updatePage);
                console.log("Book borrowed.");
            });
        }
    }

    function handleReturn(event:any){
        if(libraryId && libraryCardId){
            LoanService
            .returnBook(libraryId, libraryCardId, event.currentTarget.value)
            .then( loan => {
                setUpdatePage(!updatePage);
                console.log("Borrow returned");
            });
        }
    }

    return (
        <div>
            <Navigation />
            <Grid container direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={12} my={3}>
                    <Typography variant="h4">Book Details</Typography>
                </Grid>
                <Grid item xs={12}>
                    
                    {inventory && 
                        <Grid container direction="row" alignItems="center" justifyContent="center">
                            <Grid item xs={6}>
                                <Card style={{height: '100%'}}>
                                    <CardActionArea>
                                        <CardMedia
                                            component="img"
                                            
                                            src={`data:image/jpeg;base64, ${inventory.book.bookCover}`}
                                            
                                            alt="book_cover"
                                        />
                                    </CardActionArea>
                                </Card>
                                
                            </Grid>
                            <Grid item xs={6}>
                                <Grid container direction="column" alignItems="center" justifyContent="center">
                                    <Grid item xs={12} my={4}>
                                        <Typography variant="h5"  align="center" >
                                            {inventory.book.title} 
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <Typography variant="caption" color="text.secondary" align="center">
                                            ISBN: {inventory.book.isbn}
                                        </Typography> 
                                    </Grid>
                                    <Grid item xs={12}>
                                        <Typography variant="caption" color="text.secondary" align="center">
                                            InventoryID: {inventory.inventoryId}
                                        </Typography> 
                                    </Grid>
                                    <Grid item xs={12} my={5}>
                                        <Typography variant="h6" color="text.secondary"  align="center">
                                                Description
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary"  align="center">
                                                {inventory.book.description}
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={12} my={5}>
                                        { isAuthenticated && libraryCardId 
                                            ? availableToLoan 
                                                ? isLibraryCardActive &&
                                                    <Button value={inventory.inventoryId} variant="contained" onClick={handleLoan}>Borrow</Button> 
                                                : doesUserHaveBook 
                                                    ? <Button value={inventory.inventoryId} variant="contained" onClick={handleReturn}>Return</Button>
                                                    : <Button variant="contained" disabled>Borrow</Button>
                                            : <div></div>
                                        }
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    }
                    
                </Grid>
                <Grid item xs={12} my={3}>
                    <Typography variant="h4">Loan History</Typography>
                </Grid>
                
                <LoanTable loans={loans} includeBookDetails={false} includeLibraryDetails={true}/>
                <Grid item xs={12} my={3}>
                    <Typography variant="h4"></Typography>
                </Grid>
            </Grid>
        </div>
    );
}

export default BookPage;