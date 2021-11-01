import * as React from 'react';
import {Avatar, Button, CssBaseline, TextField, FormControlLabel, 
        Grid, Box, Typography, Container, Alert} from '@mui/material'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { RouteComponentProps, useHistory } from 'react-router-dom';
import IPage from '../common/routes/page';
import Navigation from '../components/navigation';
import { Link } from 'react-router-dom';
import AuthService from './authService';
import Person from '../person/personModel';
import * as yup from 'yup';
import { ErrorMessage, useFormik } from 'formik';
import { useState, useContext } from 'react';
import { user } from '../user/user';
import { SetUserDetails } from '../user/user.actions';

const theme = createTheme();
const validationSchema = yup.object({
    name: yup
      .string()
      .required('name is required'),
    email: yup
      .string()
      .email('Enter a valid email')
      .defined('Email is required'),
    password: yup
      .string()
      .min(8, 'Password should be of minimum 8 characters length')
      .required('Password is requ ired'),
  });

const SignUpPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    const {state: {loading, userDetails}, dispatch} = useContext(user);
    const [somethingWentWrong, setSomethingWentWrong] = useState<boolean>(false);
    const history = useHistory();
    
    const formik = useFormik({
        initialValues: {
            name: 'Leonardo Melo', 
            email: 'foobar@example.com',
            password: 'password',
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            AuthService.signUp(values).then( resp => {
              console.log("Resp", typeof resp, resp);
              
              if(resp.status == 201){
                let person:Person = resp.data as Person;
                dispatch(
                  SetUserDetails({ userId: person.personId!, name: person.name!, libraryCards: null})
                );
                history.push('/');
              }
              else if(resp.status > 409){
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
                <Alert onClose={()=> {setSomethingWentWrong(false)}} variant="filled" severity="error">Email already in use.</Alert>
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
                Sign up
              </Typography>
              
              <Box sx={{ mt: 3 }}>
                <form onSubmit={formik.handleSubmit}>
                    <Grid container spacing={2}>
                        
                        <Grid item xs={12}>
                        <TextField
                            fullWidth
                            id="name"
                            name="name"
                            label="full name"
                            value={formik.values.name}
                            onChange={formik.handleChange}
                            error={formik.touched.name && Boolean(formik.errors.name)}
                            helperText={formik.touched.name && formik.errors.name}
                            />
                        </Grid>
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
                            Sign Up
                        </Button>

                        <Grid container alignItems="center" justifyContent="center">
                        <Grid item>
                            <Link to={'/sign-in'}>
                                {"Already have an account? Sign in"}
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

export default SignUpPage;