/**
 *
 * AddPrivileges
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
import makeSelectAddPrivileges from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

import SlidingPane from "react-sliding-pane";
import "react-sliding-pane/dist/react-sliding-pane.css";

export function AddPrivileges() {
  useInjectReducer({ key: 'addPrivileges', reducer });
  useInjectSaga({ key: 'addPrivileges', saga });

  const [slidingPane, setSlidingPane] = useState(false);

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>AddPrivileges</title>
        <meta name="description" content="Description of AddPrivileges" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>All Privileges</h6>
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

      <div className="content-body">
        <div className="content-table">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Privilege Name</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Onboarding</td>
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
            <h5 class="modal-title">Add Privilege</h5>
            <button type="button" className="btn" onClick={() => setSlidingPane(false)}><i className="fas fa-chevron-left"></i></button>
          </div>
          <div class="modal-body">
            <div className="form-group">
              <label className="form-group-label">Privilege Name : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" placeholder="Enter full name" />
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

AddPrivileges.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  addPrivileges: makeSelectAddPrivileges(),
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
)(AddPrivileges);
