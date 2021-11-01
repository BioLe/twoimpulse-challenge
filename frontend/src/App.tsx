import React, { useEffect, useState } from 'react';
import './App.css';
import {Route, Switch, RouteComponentProps} from 'react-router-dom';
import routes from './common/routes/routes';

import Navigation from './components/navigation';
import { UserProvider } from './user/user';

const App: React.FunctionComponent<{}> = props => {

  const [user, setUser] = useState<any>({});

  useEffect(() => {

  }, []);

  return (   
    <div>
      
      <UserProvider>
        <Switch>
          {routes.map( (route, index) => {
            return (
              <Route 
                key={index}
                path={route.path}
                exact={route.exact}
                render={(props: RouteComponentProps<any>) => (
                  <route.component
                    name={route.name}
                    {...props}
                    {...route.props}
                  />
                )}
              />
            )
          })}
        </Switch>
      </UserProvider>
    </div>
  );

}

export default App;
