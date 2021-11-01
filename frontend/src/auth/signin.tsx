import * as React from 'react';
import {Avatar, Button, CssBaseline, TextField, FormControlLabel, 
        Checkbox, Grid, Box, Typography, Container, Alert} from '@mui/material'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Link, RouteComponentProps, useHistory } from 'react-router-dom';
import IPage from '../common/routes/page';
import Navigation from '../components/navigation';
import { useContext, useEffect, useState, useCallback } from 'react';
import AuthService from './authService';
import { useFormik } from 'formik';
import * as yup from 'yup';
//Context
import { user } from "../user/user";
import { SetUserDetails } from "../user/user.actions";
import LibraryCardService from '../libraryCard/libraryCardService';
import Person from '../person/personModel';

const theme = createTheme();

const validationSchema = yup.object({
  email: yup
    .string()
    .email('Enter a valid email')
    .defined('Email is required'),
  password: yup
    .string()
    .min(8, 'Password should be of minimum 8 characters length')
    .required('Password is required'),
});

const SignInPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

  //Context
  const {state: {loading, userDetails}, dispatch} = useContext(user);

  const [somethingWentWrong, setSomethingWentWrong] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const history = useHistory();

  const formik = useFormik({
    initialValues: {
        email: 'leo@gmail.com',
        password: 'password',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      console.log("values ", values); 
        AuthService.signIn(values).then( resp => {
          console.log("SignInResp: ", resp);
          if(resp.status == 200 ){

            let person:Person = resp.data as Person;
            localStorage.setItem('person', ""+person.personId);
            
            LibraryCardService.getLibraryCardsByPerson(person.personId!).then( libCards => {
              dispatch(
                SetUserDetails({ userId: person.personId!, name: person.name!, libraryCards: libCards})
              );
            })
            
            history.push('/');
          }
          else if(resp.status > 400){
            setSomethingWentWrong(true);
                setTimeout(() => {
                  setSomethingWentWrong(false);
                }, 15000)
          }
        });
    },
  });


  return (
    <ThemeProvider theme={theme}>
        <Navigation />
        {somethingWentWrong &&
                <Alert onClose={()=> {setSomethingWentWrong(false)}} variant="filled" severity="error">Invalid password/email.</Alert>
              }
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign In
          </Typography>
          <Box sx={{ mt: 3 }}>
            <form onSubmit={formik.handleSubmit}>
                <Grid container spacing={2}>

                    <Grid item xs={12}>
                    <TextField
                        fullWidth
                        id="email"
                        name="email"
                        label="Email"
                        value={formik.values.email}
                        onChange={formik.handleChange}
                        error={formik.touched.email && Boolean(formik.errors.email)}
                        helperText={formik.touched.email && formik.errors.email}
                        />
                    </Grid>
                    <Grid item xs={12}>
                    <TextField
                            fullWidth
                            id="password"
                            name="password"
                            label="Password"
                            type="password"
                            value={formik.values.password}
                            onChange={formik.handleChange}
                            error={formik.touched.password && Boolean(formik.errors.password)}
                            helperText={formik.touched.password && formik.errors.password}
                        />
                    </Grid>
                    </Grid>
                    
                    <Button variant="contained" fullWidth type="submit" sx={{ mt: 3, mb: 2 }}>
                        Sign In
                    </Button>

                    <Grid container alignItems="center" justifyContent="center">
                      <Grid item>
                          <Link to={'/sign-up'}>
                          {"Don't have an account? Sign Up"}
                          </Link>
                      </Grid>
                    </Grid>
            </form>
            </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}

export default SignInPage;