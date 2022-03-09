/**
 *
 * SubAdmin
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
import makeSelectSubAdmin from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

import SlidingPane from "react-sliding-pane";
import "react-sliding-pane/dist/react-sliding-pane.css";

export function SubAdmin() {
  useInjectReducer({ key: 'subAdmin', reducer });
  useInjectSaga({ key: 'subAdmin', saga });

  const [filter, setFilter] = useState(false);
  const [slidingPane, setSlidingPane] = useState(false);

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>SubAdmin</title>
        <meta name="description" content="Description of SubAdmin" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Sub Admin</h6>
          </div>
          <div className="flex-70">
            <div className="header-group">
              <div className="button-group">
                <button className="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Refresh" onClick={refreshPage}>
                  <i className="fas fa-retweet"></i>
                </button>
                <button className="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Filter" onClick={() => setFilter(true)}>
                  <i className="fas fa-filter"></i>
                </button>
                <button className="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Add Client" onClick={() => setSlidingPane(true)}>
                  <i className="fas fa-plus"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className={filter ? "content-body content-body-filter" : "content-body"} >
        <div className="content-table">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th width="15%">Full Name</th>
                <th width="25%">Email</th>
                <th width="15%">Mobile</th>
                <th width="35%">Privileges</th>
                <th width="10%">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Shakher Chauhan</td>
                <td>Shakher.Chauhan@gmail.com</td>
                <td>9717806435</td>
                <td><span className="badge badge-primary">Onboarding</span></td>
                <td>
                  <div className="button-group">
                    <button className="btn btn-outline-primary">
                      <i className="fas fa-pen"></i>
                    </button>
                    <button className="btn btn-outline-danger">
                      <i className="fas fa-trash-alt"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {filter &&
        <div className="content-filter-wrapper">
          <div className="content-filter-header">
            <h5>Filter By</h5>
            <button className="btn" onClick={() => setFilter(false)}><i className="fas fa-times"></i></button>
          </div>
          <div className="content-filter-body">
            <div className="form-group">
              <label className="form-group-label">First Name :</label>
              <input type="text" className="form-control" placeholder="Enter First Name" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Last Name :</label>
              <input type="text" className="form-control" placeholder="Enter Last Name" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Email :</label>
              <input type="email" className="form-control" placeholder="Enter Email" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Contact Number :</label>
              <input type="number" className="form-control" placeholder="Enter Contact Number" />
            </div>
          </div>

          <div className="content-filter-footer">
            <button className="btn btn-light" onClick={() => setFilter(false)}>Cancel</button>
            <button className="btn btn-success">Search</button>
          </div>
        </div>
      }

      <SlidingPane
        className="sliding-pane"
        overlayClassName="sliding-pane-wrapper"
        isOpen={slidingPane || false}
        from="right"
        width="400px"

      >
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Add Sub Admin</h5>
            <button type="button" className="btn" onClick={() => setSlidingPane(false)}><i className="fas fa-chevron-left"></i></button>
          </div>
          <div class="modal-body">
            <div className="form-group">
              <label className="form-group-label">Full Name : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" placeholder="Enter Full Name" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Email : <i className="fas fa-asterisk"></i></label>
              <input type="email" className="form-control" placeholder="Enter Email" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Contact Number : <i className="fas fa-asterisk"></i></label>
              <input type="number" className="form-control" placeholder="Enter Contact Number" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Password : <i className="fas fa-asterisk"></i></label>
              <input type="password" className="form-control" placeholder="Enter Password" />
            </div>
            <div className="form-group m-0">
              <label className="form-group-label">Select Privilege :</label>
              <ul className="privailage-item">
                <li>
                  <p>Onboarding</p>
                </li>
                <li>
                  <p>Create New</p>
                </li>
                <li>
                  <p>Pending Client</p>
                </li>
                <li>
                  <p>Manage Client</p>
                </li>
                <li>
                  <p>Product Featuring</p>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" onClick={() => setSlidingPane(false)}>Cancel</button>
          <button type="button" class="btn btn-primary">Save</button>
        </div>
      </SlidingPane>
    </React.Fragment >
  );
}

SubAdmin.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  subAdmin: makeSelectSubAdmin(),
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
)(SubAdmin);
