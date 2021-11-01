import React, { useEffect, useState } from "react";
import {Card, CardContent, CardMedia, Typography, CardActionArea,
        Grid, Button} from '@mui/material';
import { Link } from 'react-router-dom';
import PaginatedResponse from "../../common/models/PaginatedResponse";
import Inventory from "../inventoryModel";


function InventoryList(props: {inventory: PaginatedResponse<Inventory> | undefined, libraryId: number}) {

    const [libId, setLibId] = useState(props.libraryId);



    return (<Grid container spacing={3}>
        
        {props.inventory && props.inventory.content.map( inv => (
            <Grid item xs={4} my={2} key={inv.inventoryId} >
                
                <Link to={`/library/${libId}/inventory/${inv.inventoryId}`} style={{ textDecoration: 'none' }}>
                    <Card style={{height: '100%'}}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="200"
                                src={`data:image/jpeg;base64, ${inv.book.bookCover}`}
                                width="100"
                                alt="book_cover"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    {inv.book.title} 
                                    <Typography variant="caption" color="text.secondary">
                                        (ID: {inv.inventoryId})
                                    </Typography> 
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {inv.book.description}
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Link>
            </Grid>

    ))}</Grid>);

}

export default InventoryList;