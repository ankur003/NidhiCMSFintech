/**
 *
 * SignUp
 *
 */

import React, { useState, useEffect, memo } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import { ToastContainer, toast } from 'react-toastify';
import makeSelectSignUp from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';
import 'react-toastify/dist/ReactToastify.css';

export function SignUp() {
  useInjectReducer({ key: 'signUp', reducer });
  useInjectSaga({ key: 'signUp', saga });

  const [fullName, setFullName] = useState();
  const [userEmail, setEmail] = useState();
  const [password, setPassword] = useState();
  const [mobileNumber, setMobileNumber] = useState();
  const [referralCode, setReferralCode] = useState();
  const [isRegister, setRegister] = useState(true);
  const [loginError, setLoginError] = useState('');
  const [emailOtp, setEmailOtp] = useState('');
  const [mobileOtp, setMobilelOtp] = useState('');
  const [otpUuid, setOtpUuid] = useState('');
  const [veiwPassword, setviewPassword] = useState(false);
  const [isloader, setloader] = useState(false);

  async function signUp() {
    setloader(true);
    const item = { fullName, userEmail, password, mobileNumber, referralCode };
    const result = await fetch('http://localhost:1234/api/v1/user/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(item),
    }).then(response =>
      response
        .json()
        .then(data => ({
          data,
          status: response.status,
        }))
        .then(res => {
          setloader(false);
          if (res.status === 201 || res.status === 200) {
            toast.success(res.data.message);
            setRegister(false);
            setOtpUuid(res.data.otpUuid);
          } else {
            toast.error(res.data.message);
            toast.error(res.data.errorDesc);
            toast.error(res.data.errors);
          }
        }),
    );
  }

  async function verifyOtp() {
    setloader(true);
    const otpItem = { emailOtp, mobileOtp, otpUuid };
    const resultOtp = await fetch('http://localhost:1234/api/v1/user/otp', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(otpItem),
    }).then(responseOtp =>
      responseOtp
        .json()
        .then(data => ({
          data,
          status: responseOtp.status,
        }))
        .then(resOtp => {
          setloader(false);
          if (resOtp.status === 200) {
            toast.success(res.data.message);
            localStorage.setItem('user-info');
            history.push('/LoginPage');
          } else {
            toast.success(res.data.message);
            setRegister(false);
          }
        }),
    );
  }

  function showPassword() {
    if (veiwPassword === true) setviewPassword(false);
    else if (veiwPassword === false) setviewPassword(true);
  }

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
                <p>
                  Don't have an account? Create your account, it takes less than
                  a minute.
                </p>
              </div>
              {isRegister ? (
                <React.Fragment>
                  <div className="d-flex">
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Full Name</label>
                        <input
                          type="text"
                          className="form-control"
                          onChange={e => setFullName(e.target.value)}
                          required
                        />
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Email</label>
                        <input
                          type="email"
                          className="form-control"
                          onChange={e => setEmail(e.target.value)}
                          required
                        />
                      </div>
                    </div>
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">
                          Contact Numaber
                        </label>
                        <input
                          type="number"
                          className="form-control"
                          onChange={e => setMobileNumber(e.target.value)}
                          required
                        />
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Password</label>
                        <div className="input-group">
                          <input
                            type={veiwPassword ? 'text' : 'password'}
                            className="form-control"
                            onChange={e => setPassword(e.target.value)}
                            required
                          />
                          <div className="input-group-append">
                            <button
                              className="input-group-text"
                              onClick={showPassword}
                            >
                              {veiwPassword ? (
                                <i className="far fa-eye-slash" />
                              ) : (
                                <i className="far fa-eye" />
                              )}
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex-100">
                      <div className="form-group">
                        <label className="form-group-label">
                          Referral Code
                        </label>
                        <input
                          type="number"
                          className="form-control"
                          onChange={e => setReferralCode(e.target.value)}
                          required
                        />
                      </div>
                    </div>
                  </div>
                  <div className="form-group-button">
                    <button
                      className="btn btn-primary"
                      disabled={
                        password === undefined ||
                        fullName === undefined ||
                        userEmail === undefined ||
                        mobileNumber === undefined
                      }
                      onClick={signUp}
                    >
                      {isloader ? (
                        <i className="fas fa-spinner fa-pulse" />
                      ) : (
                        ' Sign Up'
                      )}
                    </button>
                  </div>
                </React.Fragment>
              ) : (
                <React.Fragment>
                  <div className="form-group">
                    <label className="form-group-label">Email OTP</label>
                    <input
                      type="text"
                      className="form-control"
                      onChange={e => setEmailOtp(e.target.value)}
                    />
                  </div>
                  <div className="form-group">
                    <label className="form-group-label">Mobile OTP</label>
                    <input
                      type="text"
                      className="form-control"
                      onChange={e => setMobilelOtp(e.target.value)}
                    />
                  </div>
                  <div className="form-group" />
                  <div className="form-group-button">
                    <button className="btn btn-primary" onClick={verifyOtp}>
                      {isloader ? (
                        <i className="fas fa-spinner fa-pulse" />
                      ) : (
                        'Verify OTP'
                      )}
                    </button>
                  </div>
                </React.Fragment>
              )}
            </div>
            <div className="login-footer">
              <p>
                Already have account? <a href="/LoginPage">Log In</a>{' '}
                <a href="/">Home</a>
              </p>
            </div>
          </div>
        </div>
      </div>

      <ToastContainer />
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
