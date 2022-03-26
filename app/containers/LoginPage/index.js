/**
 *
 * LoginPage
 *
 */

import React, { useEffect, useState, memo } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';
import history from 'utils/history';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectLoginPage from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';
import SignUp from '../SignUp/Loadable';

export function LoginPage() {
  useInjectReducer({ key: 'loginPage', reducer });
  useInjectSaga({ key: 'loginPage', saga });

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isloader, setloader] = useState(false);
  const [veiwPassword, setviewPassword] = useState(false);

  useEffect(() => {
    if (localStorage.getItem('user-info')) {
      history.push('/landingPage');
    }
  }, {});

  async function login() {
    setloader(true);
    const item = { email, password };
    const result = await fetch('http://localhost:1234/api/v1/login/client', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
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
          if (res.status === 200) {
            localStorage.setItem('user-info', res.data.token);
            history.push('/LandingPage');
          } else {
            toast.error('Invalid Email or Password');
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
                <p>
                  Enter your email address and password to access admin panel.
                </p>
              </div>
              <div className="form-group">
                <label className="form-group-label">Email</label>
                <input
                  type="email"
                  className="form-control"
                  onChange={e => setEmail(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label className="form-group-label">
                  Password
                  <a href="/ForgetPassword">Forget Password ?</a>
                </label>
                <div className="input-group">
                  <input
                    type={veiwPassword ? 'text' : 'password'}
                    className="form-control"
                    onChange={e => setPassword(e.target.value)}
                  />
                  <div className="input-group-append">
                    <button className="input-group-text" onClick={showPassword}>
                      {veiwPassword ? (
                        <i className="far fa-eye-slash" />
                      ) : (
                        <i className="far fa-eye" />
                      )}
                    </button>
                  </div>
                </div>
              </div>
              <div className="form-group-button">
                <button
                  disabled={email === '' || password === ''}
                  // href="/LandingPage"
                  onClick={login}
                  className="btn btn-primary"
                >
                  {isloader ? (
                    <i className="fas fa-spinner fa-pulse" />
                  ) : (
                    'Login'
                  )}
                </button>
              </div>
            </div>
            <div className="login-footer">
              <p>
                Don't have an account?{' '}
                <a onClick={() => history.push('/SignUp')}>Sign Up</a>
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
