/**
 *
 * ForgetPassword
 *
 */

import React, { memo, useState } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectForgetPassword from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function ForgetPassword() {
  useInjectReducer({ key: 'forgetPassword', reducer });
  useInjectSaga({ key: 'forgetPassword', saga });

  const [toggleLogin, setToggleLogin] = useState(false);
  const [toggleForm, setToggleForm] = useState();

  return (
    <React.Fragment>
      <Helmet>
        <title>ForgetPassword</title>
        <meta name="description" content="Description of ForgetPassword" />
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
                <h5>Forget Password</h5>
                <p>Enter your email address and we'll send you an email with instructions to reset your password.</p>
              </div>
              <ul className="list-tyle-none login-toggle">
                <li className={toggleLogin == false ? "active" : ""} onClick={() => setToggleLogin(false)}>
                  Email
                </li>
                <li className={toggleLogin == true ? "active" : ""} onClick={() => setToggleLogin(true)}>
                  Contact Number
                </li>
              </ul>
              {!toggleLogin ?
                <div className="form-group">
                  <label className="form-group-label">Email</label>
                  <input type="email" className="form-control" placeholder="Enter your Email" />
                </div>
                :
                <div className="form-group">
                  <label className="form-group-label">Contact Number</label>
                  <input type="number" className="form-control" placeholder="Enter your Contact Number" />
                </div>
              }
              {toggleForm == true &&
                <div className="d-flex">
                  <div className="flex-100">
                    <div className="form-group">
                      <label className="form-group-label">Enter OTP</label>
                      <input type="email" className="form-control" placeholder="Enter your Email" />
                    </div>
                  </div>
                  <div className="flex-50 pd-r-7">
                    <div className="form-group">
                      <label className="form-group-label">New Password</label>
                      <input type="password" className="form-control" placeholder="Enter your new password" />
                    </div>
                  </div>
                  <div className="flex-50 pd-l-7">
                    <div className="form-group">
                      <label className="form-group-label">Confirm Password</label>
                      <input type="password" className="form-control" placeholder="Enter your confirm password" />
                    </div>
                  </div>
                </div>
              }

              {toggleForm == false &&
                <div className="d-flex">
                  <div className="flex-100">
                    <div className="form-group">
                      <label className="form-group-label">Enter OTP</label>
                      <input type="email" className="form-control" placeholder="Enter your Email" />
                    </div>
                  </div>
                  <div className="flex-50 pd-r-7">
                    <div className="form-group">
                      <label className="form-group-label">New Password</label>
                      <input type="password" className="form-control" placeholder="Enter your new password" />
                    </div>
                  </div>
                  <div className="flex-50 pd-l-7">
                    <div className="form-group">
                      <label className="form-group-label">Confirm Password</label>
                      <input type="password" className="form-control" placeholder="Enter your confirm password" />
                    </div>
                  </div>
                </div>
              }

              <div className="form-group-button">
                {!toggleLogin ?
                  <button className="btn btn-primary" onClick={() => setToggleForm(true)}>
                    Submit
                </button>
                  :
                  <button className="btn btn-primary" onClick={() => setToggleForm(false)}>
                    Submit
                </button>
                }
              </div>
            </div>
            <div className="login-footer">
              <p>back to <a href="/LoginPage">Log In</a> <a href="/LoginPage">Home</a></p>
            </div>
          </div>
        </div>
      </div>

    </React.Fragment>
  );
}

ForgetPassword.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  forgetPassword: makeSelectForgetPassword(),
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
)(ForgetPassword);
