import React, { useContext, useEffect, useState } from "react";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation"
import Library from "../library/libraryModel";
import LibraryService from '../library/libraryService';
import {Typography, Grid, Button} from '@mui/material';
import { Link } from 'react-router-dom';
import PaginatedResponse from "../common/models/PaginatedResponse";
import LibraryList from "../library/components/libraryList";
import { user } from "../user/user";

const HomePage: React.FunctionComponent<IPage> = props => {

    const PAGE_NUM = 0;
    const MAX_NUM_OF_LIBRARIES = 2;
    const [libraries, setLibraries] = useState<PaginatedResponse<Library>>();

    useEffect( () => {
        LibraryService.getLibraries(PAGE_NUM, MAX_NUM_OF_LIBRARIES).then( libs => {
            setLibraries(libs);
        });
    }, []);

    return (
        <div>
            <Navigation />
            <Grid container direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={4}>
                    <Typography variant="h4">Pick a library</Typography>
                </Grid>
                
                <LibraryList libraries={libraries}/>
                {libraries && libraries.totalElements > MAX_NUM_OF_LIBRARIES &&
                    <Grid item xs={4}> 
                        <Button component={Link} to="/library" variant="contained">Show More</Button>
                    </Grid>
                }
            </Grid>
        </div>
    );
}

export default HomePage;