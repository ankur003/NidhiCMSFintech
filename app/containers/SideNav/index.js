/**
 *
 * SideNav
 *
 */

import React, { memo, useState } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Helmet } from "react-helmet";
import { FormattedMessage } from "react-intl";
import { createStructuredSelector } from "reselect";
import { compose } from "redux";

import { useInjectSaga } from "utils/injectSaga";
import { useInjectReducer } from "utils/injectReducer";
import makeSelectSideNav from "./selectors";
import reducer from "./reducer";
import saga from "./saga";
import messages from "./messages";
import history from 'utils/history';
import HomePage from 'containers/HomePage';

const ROUTE_HANDELR = [
  {
    navName: 'Landing Pages',
    routeName: '/landingPage',
    iconClass: 'fad fa-chart-area',
  },
  {
    navName: 'Onboarding',
    routeName: '/pendingClient',
    iconClass: 'fas fa-boxes',
    subMenu: [
      {
        navName: 'Pending Client',
        routeName: '/pendingClient',
        iconClass: 'fas fa-users',
      },
      {
        navName: 'Manage Client',
        routeName: '/manageClient',
        iconClass: 'fas fa-wrench',
      },
    ]
  },
  {
    navName: 'Sub Admin',
    routeName: '/subAdmin',
    iconClass: 'fas fa-user-cog',
  },
  {
    navName: 'Report',
    routeName: '/transactionReport',
    iconClass: 'fas fa-file-alt',
    subMenu: [
      {
        navName: 'Transaction Report',
        routeName: '/transactionReport',
        iconClass: 'fas fa-money-check-alt',
      },
      {
        navName: 'Billing & Charges Report',
        routeName: '/chargesReport',
        iconClass: 'fas fa-calculator',
      },
      {
        navName: 'Transaction Inquiry',
        routeName: '/transactionInquiry',
        iconClass: 'fas fa-search-dollar',
      },
      {
        navName: 'Account Statement',
        routeName: '/accountStatement',
        iconClass: 'fas fa-piggy-bank',
      },
    ]
  },
];
export function SideNav(props) {
  useInjectReducer({ key: "sideNav", reducer });
  useInjectSaga({ key: "sideNav", saga });

  const redirectPageHandler = pageRoute => {
    props.setNav(pageRoute);
    history.push(pageRoute);
  };

  const navlinkHighlighter = currentNav => {
    currentNav = currentNav === '/' ? '/landingPage' : currentNav;
    const pathName = history.location.pathname === '/' ? '/landingPage' : history.location.pathname;
    if (pathName === currentNav) {
      return 'active';
    }
  };

  const [state, setState] = useState(() => ({
    toggle: 'close'
  }))

  return (
    <React.Fragment>
      <Helmet>
        <title>SideNav</title>
        <meta name="description" content="Description of SideNav" />
      </Helmet>

      <header className={state.toggle === 'open' ? "nav-header nav-header-collapsed d-flex align-items-center" : "nav-header d-flex align-items-center"}>
        <div className="flex-40 d-flex">
          {state.toggle === 'open' ?
            <button className="btn btn-outline-light" onClick={() => setState({ toggle: 'close' })}><i className="fas fa-bars"></i></button> :
            <button className="btn btn-outline-light" onClick={() => setState({ toggle: 'open' })}><i className="fas fa-bars"></i></button>
          }
        </div>
        <div className="flex-60 nav-header-content-group">
          <div className="dropdown nav-dropdwon">
            <button className="btn btn-light dropdown-toggle" data-bs-toggle="dropdown">
              <i className="fas fa-flag-usa mr-r-10 text-primary" style={{ fontSize: "12px" }}></i>English
            </button>
            <div className="dropdown-menu animate__animated animate__flipInX">
              <div className="dropdown-item"><i className="fas fa-flag-usa mr-r-10 text-primary"></i>English</div>
            </div>
          </div>
          <div className="dropdown nav-dropdwon nav-notification">
            <button className="btn btn-light" data-bs-toggle="dropdown">
              <i className="far fa-bell"></i>
              <span className="status bg-danger"></span>
            </button>
            <div className="dropdown-menu animate__animated animate__flipInX">
              <div className="dropdown-menu-header">
                <h6>Notifications</h6>
                <button className="btn btn-link">Clear All</button>
              </div>
              <div className="dropdown-menu-body">
                <div className="dropdown-item"><span className="icon bg-primary"><i class="fas fa-bell"></i></span>
                  <h6>Lorem ipsum dolor sit amet</h6>
                  <p>2 Min</p>
                </div>
                <div className="dropdown-item"><span className="icon bg-primary"><i class="fas fa-bell"></i></span>
                  <h6>Lorem ipsum dolor sit amet</h6>
                  <p>2 Min</p>
                </div>
              </div>
              <div className="dropdown-menu-footer">
                <button className="btn btn-link">View All</button>
              </div>
            </div>
          </div>

          <div className="dropdown nav-dropdwon">
            <button className="btn btn-light" data-bs-toggle="dropdown">
              <i className="fas fa-border-none"></i>
            </button>
            <div className="dropdown-menu third-party-dropdown animate__animated animate__flipInX">
              <ul className="list-style-none third-party-list">
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
                <li>
                  <div className="dropdown-item">
                    <div className="dropdown-img">
                      <img src={require('../../assets/images/Octocat.png')} />
                    </div>
                    <p>Github</p>
                  </div>
                </li>
              </ul>

            </div>
          </div>
          <div className="dropdown nav-dropdwon">
            <button className="btn btn-light"  onClick={() => history.push('/addPrivileges')}>
              <i className="far fa-cog"></i>
            </button>
          </div>
          <div className="nav-profile">
            <div className="dropdown">
              <button className="btn btn-light" data-bs-toggle="dropdown">
                <span className="nav-profile-icon"><i className="fad fa-user"></i></span>
                <h6>Nidhi CMS Fintech</h6>
                <p>Admin</p>
              </button>
              <div className="dropdown-menu animate__animated animate__flipInX">
                <div className="dropdown-item">Profile</div>
                <div className="dropdown-item">Notifications</div>
                <div className="dropdown-item" onClick={() => { history.push('/loginPage') }}>Logout</div>
              </div>
            </div>
          </div>
        </div>
      </header>


      <nav className={state.toggle === 'open' ? "side-nav-wrapper " : "side-nav-wrapper side-nav-collapsed"}>
        <div className="side-nav-header">
          <div className="side-nav-icon">
            <img src={require('../../assets/images/logo.png')} />
          </div>
        </div>
        <div className="side-nav-body">
          {state.toggle === 'open' ?
            <ul className="side-nav-menu list-style-none">
              {ROUTE_HANDELR.map((item, index) =>
                <li key={index} className={navlinkHighlighter(item.routeName)}>
                  {item.subMenu ?
                    <p data-toggle="collapse" data-target={`#sub${index}`}><i className={item.iconClass}></i>{state.toggle === 'open' ? <React.Fragment>{item.navName}</React.Fragment> : ""}</p> :
                    <p onClick={() => redirectPageHandler(item.routeName)}><i className={item.iconClass}></i>{state.toggle === 'open' ? <React.Fragment>{item.navName}</React.Fragment> : ""}</p>
                  }
                  {item.subMenu &&
                    <div className="collapse show" id={`sub${index}`}>
                      <ul className="side-nav-sub-menu">
                        {item.subMenu.map((subItem, subIndex) =>
                          <li key={subIndex} className={navlinkHighlighter(subItem.routeName)} onClick={() => redirectPageHandler(subItem.routeName)}>
                            <p><i className={subItem.iconClass}></i>{subItem.navName}</p>
                          </li>
                        )}
                      </ul>
                    </div>
                  }
                </li>
              )}
            </ul>
            :
            <ul className="side-nav-menu list-style-none">
              {ROUTE_HANDELR.map((item, index) =>
                <li key={index} className={navlinkHighlighter(item.routeName)}>
                  {item.subMenu ?
                    <p><i className={item.iconClass}></i>{state.toggle === 'open' ? <React.Fragment>{item.navName}</React.Fragment> : ""}</p> :
                    <p onClick={() => redirectPageHandler(item.routeName)}><i className={item.iconClass}></i>{state.toggle === 'open' ? <React.Fragment>{item.navName}</React.Fragment> : ""}</p>
                  }
                  {item.subMenu &&
                    <div className="dropdown animate__animated animate__backInLeft">
                      <ul className="side-nav-sub-menu">
                        {item.subMenu.map((subItem, subIndex) =>
                          <li key={subIndex} className={navlinkHighlighter(subItem.routeName)} onClick={() => redirectPageHandler(subItem.routeName)}>
                            <p><i className={subItem.iconClass}></i>{subItem.navName}</p>
                          </li>
                        )}
                      </ul>
                    </div>
                  }
                </li>
              )}
            </ul>
          }
        </div>
      </nav>

      {/* <HomePage side={state.toggle} /> */}

    </React.Fragment>
  );
}

SideNav.propTypes = {
  dispatch: PropTypes.func.isRequired
};

const mapStateToProps = createStructuredSelector({
  sideNav: makeSelectSideNav()
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch
  };
}

const withConnect = connect(
  mapStateToProps,
  mapDispatchToProps
);

export default compose(
  withConnect,
  memo
)(SideNav);
