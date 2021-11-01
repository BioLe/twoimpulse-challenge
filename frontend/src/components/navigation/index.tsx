import React, { useContext, useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import logo from '../../common/graphics/logo2.svg'
import {AppBar, Box, Toolbar, Typography, Button, Icon} from '@mui/material';
import { makeStyles } from '@mui/styles';
import { user } from "../../user/user";

export interface INavigationProps {}

const useStyles = makeStyles({
    imageIcon: {
        display: 'flex',
        height: 'inherit',
        width: 'inherit'
      }
  });

const Navigation: React.FunctionComponent<INavigationProps> = props => {

    const classes = useStyles();
    const {state: {loading, userDetails}, dispatch} = useContext(user);
    const [updateNavigationBar, setUpdateNavigationBar] = useState<boolean>(true);

    useEffect( () => {}, [updateNavigationBar])
    
    function handleLogout(): void {
        localStorage.clear();
        setUpdateNavigationBar(!updateNavigationBar);
    }

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <Icon component={Link} to="/">
                        <img className={classes.imageIcon} src={logo}/>
                    </Icon>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Library
                    </Typography>
                    {userDetails != null && localStorage.getItem('userState')
                        ? 
                            <div>
                                <Button color="inherit" component={Link} to="/profile">Profile</Button>
                                <Button color="inherit" component={Link} to="/sign-in" onClick={handleLogout}>Logout</Button>
                            </div>
                        :
                            <div>
                                <Button color="inherit" component={Link} to="/sign-up">Sign Up</Button>
                                <Button color="inherit" component={Link} to="/sign-in">Sign In</Button>
                            </div>
                    }
                   
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default Navigation;
