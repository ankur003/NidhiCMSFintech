/**
 *
 * SignUp
 *
 */

import React, { memo } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectSignUp from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function SignUp() {
  useInjectReducer({ key: 'signUp', reducer });
  useInjectSaga({ key: 'signUp', saga });

  return (
    <React.Fragment>
      <Helmet>
        <title>SignUp</title>
        <meta name="description" content="Description of SignUp" />
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
                <h5>Sign Up</h5>
                <p>Don't have an account? Create your account, it takes less than a minute.</p>
              </div>
              <div className="d-flex">
                <div className="flex-50 pd-r-7">
                  <div className="form-group">
                    <label className="form-group-label">Full Name</label>
                    <input type="text" className="form-control" />
                  </div>
                </div>
                <div className="flex-50 pd-l-7">
                  <div className="form-group">
                    <label className="form-group-label">Email</label>
                    <input type="email" className="form-control" />
                  </div>
                </div>
                <div className="flex-50 pd-r-7">
                  <div className="form-group">
                    <label className="form-group-label">Contact Numaber</label>
                    <input type="number" className="form-control" />
                  </div>
                </div>
                <div className="flex-50 pd-l-7">
                  <div className="form-group">
                    <label className="form-group-label">Password
                   </label>
                    <div className="input-group">
                      <input type="password" className="form-control" />
                      <div className="input-group-append">
                        <button className="input-group-text"><i className="far fa-eye"></i></button>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="flex-100">
                  <div className="form-group">
                    <label className="form-group-label">Referral Code</label>
                    <input type="number" className="form-control" />
                  </div>
                </div>
              </div>
              <div className="form-group-button">
                <button className="btn btn-primary">
                  Sign Up
                </button>
              </div>
            </div>
            <div className="login-footer">
              <p>Already have account?  <a href="/LoginPage">Log In</a> <a href="/">Home</a></p>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

SignUp.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  signUp: makeSelectSignUp(),
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
)(SignUp);
