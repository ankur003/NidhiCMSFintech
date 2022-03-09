/**
 *
 * App
 *
 * This component is the skeleton around the actual pages, and should only
 * contain code that should be seen on all pages. (e.g. navigation bar)
 */

import React from 'react';
import { Helmet } from 'react-helmet';
import styled from 'styled-components';
import { Switch, Route } from 'react-router-dom';

import HomePage from 'containers/HomePage/Loadable';
import LoginPage from 'containers/LoginPage/Loadable';
import SignUp from 'containers/SignUp/Loadable';
import ForgetPassword from 'containers/ForgetPassword/Loadable';
import WebiteLandingPage from 'containers/WebiteLandingPage/Loadable';
import NotFoundPage from 'containers/NotFoundPage/Loadable';



export default function App() {
  return (
    <React.Fragment>
      <Helmet
        titleTemplate="%s - React.js Boilerplate"
        defaultTitle="React.js Boilerplate"
      >
        <meta name="description" content="A React.js Boilerplate application" />
      </Helmet>
      <Switch>
        <Route exact path="/WebiteLandingPage" component={WebiteLandingPage} />
        <Route path="/LoginPage" component={LoginPage} />
        <Route path="/SignUp" component={SignUp} />
        <Route path="/ForgetPassword" component={ForgetPassword} />
        <Route path="/" render={props => <HomePage {...props} />} />

      </Switch>
    </React.Fragment>
  );
}
