/**
 *
 * LoginPage
 *
 */

import React, { memo } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';
import history from 'utils/history';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectLoginPage from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function LoginPage() {
  useInjectReducer({ key: 'loginPage', reducer });
  useInjectSaga({ key: 'loginPage', saga });

  return (
    <React.Fragment>
      <Helmet>
        <title>LoginPage</title>
        <meta name="description" content="Description of LoginPage" />
      </Helmet>

      <div className="login-wrapper-outer">
        <div className="login-inner-box">
          <div className="login-header">
            <div className="login-logo">
              <img src={require('../../assets/images/logo.png')} />
            </div>
          </div>

          <div className="login-content-box">
            <div className="login-body">
              <div className="form-group">
                <h5>Login</h5>
                <p>Enter your email address and password to access admin panel.</p>
              </div>
              <div className="form-group">
                <label className="form-group-label">Email</label>
                <input type="email" className="form-control" />
              </div>
              <div className="form-group">
                <label className="form-group-label">Password
                  <a href="/ForgetPassword">Forget Password ?</a>
                </label>
                <div className="input-group">
                  <input type="password" className="form-control" />
                  <div className="input-group-append">
                    <button className="input-group-text"><i className="far fa-eye"></i></button>
                  </div>
                </div>

              </div>
              <div className="form-group-button">
                <a
                  // href="/LandingPage"
                  onClick={() => history.push('/landingPage')}
                  className="btn btn-primary">
                  Login
                </a>
              </div>
            </div>
            <div className="login-footer">
              <p>Don't have an account? <a
                href="/SignUp"
              >Sign Up</a></p>
            </div>
          </div>
        </div>
      </div>

    </React.Fragment>
  );
}

LoginPage.propTypes = {
  dispatch: PropTypes.func.isRequired,
};  

const mapStateToProps = createStructuredSelector({
  loginPage: makeSelectLoginPage(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(
  mapStateToProps,
  mapDispatchToProps,
);

export default compose(
  withConnect,
  memo,
)(LoginPage);
