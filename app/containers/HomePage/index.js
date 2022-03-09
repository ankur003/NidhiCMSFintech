/*
 * HomePage
 *
 * This is the first thing users see of our App, at the '/' route
 */

import React, { useEffect, memo, useState } from 'react';
import PropTypes from 'prop-types';
import { Helmet } from 'react-helmet';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { createStructuredSelector } from 'reselect';

import { useInjectReducer } from 'utils/injectReducer';
import { useInjectSaga } from 'utils/injectSaga';
import {
  makeSelectRepos,
  makeSelectLoading,
  makeSelectError,
} from 'containers/App/selectors';
import { loadRepos } from '../App/actions';
import { changeUsername } from './actions';
import { makeSelectUsername } from './selectors';
import reducer from './reducer';
import saga from './saga';
import SideNav from '../SideNav/Loadable';
import { BrowserRouter as Route, Switch } from 'react-router-dom';
import LoginPage from 'containers/LoginPage/Loadable';
import SignUp from 'containers/SignUp/Loadable';
import ForgetPassword from 'containers/ForgetPassword/Loadable';
import WebiteLandingPage from 'containers/WebiteLandingPage/Loadable';
import LandingPage from 'containers/LandingPage/Loadable';
import PendingClient from 'containers/PendingClient/Loadable';
import ManageClient from 'containers/ManageClient/Loadable';
import SubAdmin from 'containers/SubAdmin/Loadable';
import TransactionReport from 'containers/TransactionReport/Loadable';
import ChargesReport from 'containers/ChargesReport/Loadable';
import TransactionInquiry from 'containers/TransactionInquiry/Loadable';
import AccountStatement from 'containers/AccountStatement/Loadable';
import AddPrivileges from 'containers/AddPrivileges/Loadable';

import history from "../../utils/history";

const key = 'home';

export function HomePage({
  username,
  loading,
  error,
  repos,
  onSubmitForm,
  onChangeUsername,
  props,
}) {
  useInjectReducer({ key, reducer });
  useInjectSaga({ key, saga });

  let [activeNav, setNav] = useState(history.location.pathname);

  useEffect(() => {
    history.push(activeNav);
  }, []);


  const handleSubmit = (evt) => {
    evt.preventDefault();

    // To close the popup in <ModalPopup/>
    setState(close);
  }


  let getPath = () => {
    if (history.location.pathname) {
      return history.location.pathname
    }
    return "/";
  }

  let getComponent = () => {
    switch (history.location.pathname) {
      case '/':
        return <WebiteLandingPage />
      case '/':
        return <LoginPage />
      case '/':
        return <SignUp />
      case '/':
        return <ForgetPassword />
      case '/landingPage':
        return <LandingPage />
      case '/pendingClient':
        return <PendingClient />
      case '/manageClient':
        return <ManageClient />
      case '/subAdmin':
        return <SubAdmin />
      case '/transactionReport':
        return <TransactionReport />
      case '/chargesReport':
        return <ChargesReport />
      case '/transactionInquiry':
        return <TransactionInquiry />
      case '/accountStatement':
        return <AccountStatement />
      case '/addPrivileges':
        return <AddPrivileges />
      default:
        return <LandingPage />
    }
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>Home Page</title>
        <meta
          name="description"
          content="Enzen"
        />
      </Helmet>
      <div className="content-wrapper animate__animated animate__zoomIn">
        {history.location.pathname === "/" ? "" :
          <SideNav activeNav={activeNav} path={history.location.pathname} setNav={setNav} />
        }
        <Switch>
          <Route exact path={getPath()}>{getComponent()}</Route>
        </Switch>
      </div>
    </React.Fragment>
  );
}

HomePage.propTypes = {
  loading: PropTypes.bool,
  error: PropTypes.oneOfType([PropTypes.object, PropTypes.bool]),
  repos: PropTypes.oneOfType([PropTypes.array, PropTypes.bool]),
  onSubmitForm: PropTypes.func,
  username: PropTypes.string,
  onChangeUsername: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  repos: makeSelectRepos(),
  username: makeSelectUsername(),
  loading: makeSelectLoading(),
  error: makeSelectError(),
});

export function mapDispatchToProps(dispatch) {
  return {
    onChangeUsername: evt => dispatch(changeUsername(evt.target.value)),
    onSubmitForm: evt => {
      if (evt !== undefined && evt.preventDefault) evt.preventDefault();
      dispatch(loadRepos());
    },
  };
}

const withConnect = connect(
  mapStateToProps,
  mapDispatchToProps,
);

export default compose(
  withConnect,
  memo,
)(HomePage);
