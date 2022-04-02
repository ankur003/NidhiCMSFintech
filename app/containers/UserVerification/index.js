/**
 *
 * UserVerification
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
import makeSelectUserVerification from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function UserVerification(userData,userEmail, userPhone) {
  useInjectReducer({ key: 'userVerification', reducer });
  useInjectSaga({ key: 'userVerification', saga });
  return (
    <React.Fragment>
      <Helmet>
        <title>UserVerification</title>
        <meta name="description" content="Description of UserVerification" />
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
                <h5>User Verification</h5>
                <p>
                  Please verify your email address and Phone Number.
                </p>
              </div>
              <div className="form-group">
                <label className="form-group-label">Email</label>
                <input
                  type="email"
                  className="form-control"
                  value=""
                />
              </div>
              <div className="form-group">
                <label className="form-group-label">Phone Number</label>
                <input
                  type="email"
                  className="form-control"
                  value=""
                />
              </div>
              <div className="form-group-button">
                <button
                  // disabled={email === '' || password === ''}
                  // onClick={login}
                  className="btn btn-primary"
                >

                  Verify
                </button>
              </div>
            </div>
            <div className="login-footer">
              <p>
                Don't have an account?
                <a onClick={() => history.push('/LoginPage')}>Login</a>
                <a href="/">Home</a>
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="login-wrapper-outer d-none">
        <div className="login-inner-box">
          <div className="login-header">
            <div className="login-logo">
              <img src={require('../../assets/images/logo.png')} />
            </div>
          </div>

          <div className="login-content-box">
            <div className="login-body">
              <div className="form-group">
                <h5>User Verification</h5>
                <p>
                  Please verify your email address and Phone Number.
                </p>
              </div>
              <div className="form-group">
                <label className="form-group-label">Email</label>
                <input
                  type="email"
                  className="form-control"
                  value=""
                />
              </div>
              <div className="form-group">
                <label className="form-group-label">Phone Number</label>
                <input
                  type="email"
                  className="form-control"
                  value=""
                />
              </div>
              <div className="form-group-button">
                <button
                  // disabled={email === '' || password === ''}
                  // onClick={login}
                  className="btn btn-primary"
                >

                  Verify
                </button>
              </div>
            </div>
            <div className="login-footer">
              <p>
                Don't have an account?
                <a onClick={() => history.push('/LoginPage')}>Login</a>
                <a href="/">Home</a>
              </p>
            </div>
          </div>
        </div>
      </div>

    </React.Fragment>
  );
}

UserVerification.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  userVerification: makeSelectUserVerification(),
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
)(UserVerification);
