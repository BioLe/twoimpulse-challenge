import React, { useContext, useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import Library from "../library/libraryModel";
import LibraryService from '../library/libraryService';
import {Typography, Grid, Pagination} from '@mui/material';
import { Link } from 'react-router-dom';
import config from '../config/config';
import PaginatedResponse from "../common/models/PaginatedResponse";
import LibraryList from "../library/components/libraryList";
import { user } from "../user/user";

const LibrariesPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    
    
    const MAX_NUM_OF_LIBRARIES = 2;
    const [page, setPage] = useState(0);
    const [libraries, setLibraries] = useState<PaginatedResponse<Library>>();

    useEffect( () => {
        
        console.log("Page: ", page);
        LibraryService.getLibraries(page, MAX_NUM_OF_LIBRARIES).then( libs => {
            setLibraries(libs);
        });
    }, [page]);

    const handlePageChange = (event:any, value:number) => {
        setPage(value-1); //Page index starts at 0, but shown to user as 1
    }

    return (
        <div>
            <Navigation />
            <Grid container direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={4}>
                    <Typography variant="h4">Pick a library</Typography>
                </Grid>
                <LibraryList libraries={libraries}/>
                {libraries && 
                    <Pagination count={libraries.totalPages} color="primary" onChange={handlePageChange}/>
                }
            </Grid>
        </div>
    );
}

export default LibrariesPage;
