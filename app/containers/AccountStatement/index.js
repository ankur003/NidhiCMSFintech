/**
 *
 * AccountStatement
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
import makeSelectAccountStatement from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function AccountStatement() {
  useInjectReducer({ key: 'accountStatement', reducer });
  useInjectSaga({ key: 'accountStatement', saga });

  const [filter, setFilter] = useState(true);

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>AccountStatement</title>
        <meta name="description" content="Description of AccountStatement" />
      </Helmet>

      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Account Statement</h6>
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
                <th>Date</th>
                <th>Unique Id</th>
                <th>Description</th>
                <th>Dr</th>
                <th>Cr</th>
                <th>Balance</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>17-02-2022</td>
                <td>204811305437</td>
                <td>/MAHIPALSINGH/204811305437</td>
                <td>0.0</td>
                <td>100.0</td>
                <td>100.0</td>
              </tr>
            </tbody>
          </table>
        </div>

        {/* <div className="content-message">
          <p>No Data Found</p>
        </div> */}

      </div>
      {filter &&
        <div className="content-filter-wrapper">
          <div className="content-filter-header">
            <h5>Filter By</h5>
            <button className="btn" onClick={() => setFilter(false)}><i className="fas fa-times"></i></button>
          </div>
          <div className="content-filter-body">
            <div className="form-group">
              <label className="form-group-label">Merchant Id :</label>
              <input type="text" className="form-control" placeholder="Enter Merchant Id" />
            </div>
            <div className="form-group">
              <label className="form-group-label">Pan Card :</label>
              <input type="text" className="form-control" placeholder="Enter Pan Card" />
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

    </React.Fragment>
  );
}

AccountStatement.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  accountStatement: makeSelectAccountStatement(),
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
)(AccountStatement);
