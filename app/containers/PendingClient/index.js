/**
 *
 * PendingClient
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
import makeSelectPendingClient from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';


import SlidingPane from "react-sliding-pane";
import "react-sliding-pane/dist/react-sliding-pane.css";

export function PendingClient() {
  useInjectReducer({ key: 'pendingClient', reducer });
  useInjectSaga({ key: 'pendingClient', saga });

  const [slidingPane, setSlidingPane] = useState(false);
  const [filter, setFilter] = useState(false);

  function refreshPage() {
    window.location.reload(false);
  }



  return (
    <React.Fragment>
      <Helmet>
        <title>PendingClient</title>
        <meta name="description" content="Description of PendingClient" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>All Client</h6>
          </div>
          <div className="flex-70">
            <div className="header-group">
              <div className="button-group">
                <button className="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Refresh" onClick={refreshPage}>
                  <i className="fas fa-retweet"></i>
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
                <th>Full Name</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Dob</th>
                <th>Pan</th>
                <th>Aadhar</th>
                <th>Gst</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Shakher Chauhan</td>
                <td>Shakher.Chauhan@gmail.com</td>
                <td>9717806435</td>
                <td>02-20-2020</td>
                <td>ABD7806435</td>
                <td>97178064351212</td>
                <td>GST-97178064351212</td>
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

     

      <SlidingPane
        className="sliding-pane"
        overlayClassName="sliding-pane-wrapper"
        isOpen={slidingPane || false}
        from="right"
        width="400px"

      >
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Add Client</h5>
            <button type="button" className="btn" onClick={() => setSlidingPane(false)}><i className="fas fa-chevron-left"></i></button>
          </div>
          <div class="modal-body">
            <div className="form-group">
              <label className="form-group-label">Full Name : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" placeholder="Enter full name" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Email : <i className="fas fa-asterisk"></i></label>
              <input type="email" className="form-control" placeholder="Enter email" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Contact Number : <i className="fas fa-asterisk"></i></label>
              <input type="number" className="form-control" placeholder="Enter number" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Password : <i className="fas fa-asterisk"></i></label>
              <input type="password" className="form-control" placeholder="Enter password" />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onClick={() => setSlidingPane(false)}>Cancel</button>
            <button type="button" class="btn btn-primary">Save</button>
          </div>
        </div>
      </SlidingPane>


    </React.Fragment>
  );
}

PendingClient.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  pendingClient: makeSelectPendingClient(),
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
)(PendingClient);
