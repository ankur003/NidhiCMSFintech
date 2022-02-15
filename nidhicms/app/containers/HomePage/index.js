/*
 * HomePage
 *
 * This is the first thing users see of our App, at the '/' route
 */

import React, { useEffect, memo } from 'react';
import PropTypes from 'prop-types';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
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
import H2 from 'components/H2';
import ReposList from 'components/ReposList';
import AtPrefix from './AtPrefix';
import CenteredSection from './CenteredSection';
import Form from './Form';
import Input from './Input';
import Section from './Section';
import messages from './messages';
import { loadRepos } from '../App/actions';
import { changeUsername } from './actions';
import { makeSelectUsername } from './selectors';
import reducer from './reducer';
import saga from './saga';

const key = 'home';

export function HomePage({
  username,
  loading,
  error,
  repos,
  onSubmitForm,
  onChangeUsername,
}) {
  useInjectReducer({ key, reducer });
  useInjectSaga({ key, saga });

  useEffect(() => {
    // When initial state username is not null, submit the form to load repos
    if (username && username.trim().length > 0) onSubmitForm();
  }, []);

  const reposListProps = {
    loading,
    error,
    repos,
  };

  return (
    <React.Fragment>
      <Helmet>
        <title>Home Page</title>
        <meta
          name="description"
          content="A React.js Boilerplate application homepage"
        />
      </Helmet>
      <React.Fragment>
        <div className="web-wrapper">
          <div className="web-nav-bar">
            <div className="web-nav-logo">
              <img src={require('../../assets/images/logo.png')} />
            </div>
            <ul className="web-nav-list">
              <li>
                <a><i className="fas fa-home"></i>Home</a>
              </li>
              <li>
                <a><i className="fas fa-address-book"></i>Contact Us</a>
              </li>
              <li>
                <a href="/LoginPage"><i className="fas fa-sign-in-alt"></i>Login</a>
              </li>
              <li>
                <a href="/SignUp"><i className="fas fa-user-plus"></i>Sign Up</a>
              </li>
            </ul>
          </div>
          <div className="web-content-wrapper">
            <div className="d-flex web-content-top">
              <div className="flex-50 web-content-left">
                <h1>Grow Your Business With Nidhi Fintech</h1>
                <h3>made smarter for your business</h3>
                <button className="btn btn-light">Contact US</button>
              </div>
              <div className="flex-50 web-content-right">
                <ul className="web-content-left-list">
                  <li><i className="fas fa-book"></i>Business Services</li>
                  <li><i className="fas fa-users"></i>Finance Business</li>
                  <li><i className="fal fa-table"></i>CMS Services</li>
                </ul>
              </div>
            </div>
            <div className="feature-wrapper">
              <h6>ABOUT US</h6>
              <p>Nidhi Fintech Consultancy is a fastest growing Fintech Consultancy which help to entrepreneur</p>
              <p>start-ups to setup their business and provide best consultancy services across the PAN India.</p>
            </div>
            <div className="feature-wrapper">
              <h6>Our Features</h6>
              <ul className="fetaure-list">
                <li>
                  <i className="fas fa-rupee-sign"></i>
                  <p>Lowest Fees</p>
                </li>
                <li>
                  <i className="fas fa-money-bill-alt"></i>
                  <p>Transparency in cost</p>
                </li>
                <li>
                  <i className="fas fa-lock"></i>
                  <p>Confidential</p>
                </li>
                <li>
                  <i className="fas fa-bars"></i>
                  <p>Quality Work</p>
                </li>
                <li>
                  <i className="fas fa-smile"></i>
                  <p>
                    Satisfaction Guaranteed</p>
                </li>
                <li>
                  <i className="fas fa-user-plus"></i>
                  <p>Best CA/CS Consultant</p>
                </li>
              </ul>
            </div>
            <div className="Testimonial-wrapper">
              <div className="testimonial-wrapper-box">
                <div className="testimonial-wrapper">
                  <div className="testimonila-image">
                    <i className="far fa-user"></i>
                  </div>
                  <h4>Deepak Gupta</h4>
                  <h5>Happy Customer</h5>
                  <p>
                    Really nice service and co-operative staff.
                    Nidhi Fin Tech doing very good and hia staff is
                    really nice.. keep it up sir..
									</p>
                </div>
                <div className="testimonial-wrapper">
                  <div className="testimonila-image">
                    <i className="far fa-user"></i>
                  </div>
                  <h4>Anil Singh</h4>
                  <h5>Happy Customer</h5>
                  <p>
                    Good communication and friendly accessable Work provided by the Nidhi Fin Tech
									</p>
                </div>
              </div>
            </div>
          </div>
          <div className="web-footer-wrapper">
            <h6>Contact Us</h6>
            <ul className="web-footer-list">
              <li><p><i className="fas fa-map-marker-alt"></i>Opp. SBI Bank Near Anaj Mandi
            Virender Bansur Alwar,
            Rajasthan 301402.</p></li>
              <li> <p><i className="fas fa-phone-alt"></i>+91-6377201669</p></li>
              <li><p><i className="fas fa-envelope"></i>cs@nidhicms.com</p></li>
            </ul>
          </div>
          <h1 className="web-copy-right"><i className="far fa-copyright"></i> All Right Reserved. Designed by Devendra Gread</h1>
        </div>

      </React.Fragment>
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
