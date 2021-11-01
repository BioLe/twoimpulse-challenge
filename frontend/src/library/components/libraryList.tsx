import React, { useEffect, useState } from "react";
import {Card, CardContent, CardMedia, Typography, CardActionArea,
        Grid, Button} from '@mui/material';
import { Link } from 'react-router-dom';
import PaginatedResponse from "../../common/models/PaginatedResponse";
import Library from "../libraryModel";


function LibraryList(props: {libraries: PaginatedResponse<Library> | undefined}) {

    return (<div>
        
        {props.libraries && props.libraries.content.map( lib => (
            <Grid item xs={12} my={3} key={lib.l_ID}>
                <Link to={`/library/${lib.l_ID}`} style={{ textDecoration: 'none' }}>
                    <Card>
                        <CardActionArea>
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="div">
                                {lib.name}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                {lib.address}
                            </Typography>
                        </CardContent>
                        </CardActionArea>
                    </Card>
                </Link>
            </Grid>

    ))}</div>);

}

export default LibraryList;