/**
 *
 * ChargesReport
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
import makeSelectChargesReport from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function ChargesReport() {
  useInjectReducer({ key: 'chargesReport', reducer });
  useInjectSaga({ key: 'chargesReport', saga });

  const [filter, setFilter] = useState(true);

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>ChargesReport</title>
        <meta name="description" content="Description of ChargesReport" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Billing & Charges Report</h6>
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
                <th>Fee</th>
                <th>Charge Detail</th>
                <th>UTR</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>17-02-2022</td>
                <td>	12.9</td>
                <td>IFS</td>
                <td>204817786323</td>
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
              <label className="form-group-label">Client Name  : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" />
            </div>
            <div className="form-group">
              <label className="form-group-label">From date :  <i className="fas fa-asterisk"></i></label>
              <input type="date" className="form-control" />
            </div>
            <div className="form-group">
              <label className="form-group-label">To date :  <i className="fas fa-asterisk"></i></label>
              <input type="date" className="form-control" />
            </div>
          </div>
          <div className="content-filter-footer">
            <button className="btn btn-light" onClick={() => setFilter(false)}>Cancel</button>
            <button className="btn btn-success">Submit</button>
          </div>
        </div>
      }
    </React.Fragment>
  );
}

ChargesReport.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  chargesReport: makeSelectChargesReport(),
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
)(ChargesReport);
