/**
 *
 * TransactionInquiry
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
import makeSelectTransactionInquiry from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function TransactionInquiry() {
  useInjectReducer({ key: 'transactionInquiry', reducer });
  useInjectSaga({ key: 'transactionInquiry', saga });

  const [filter, setFilter] = useState(true);

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>TransactionInquiry</title>
        <meta name="description" content="Description of TransactionInquiry" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Transaction Inquiry</h6>
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
                <th>Tx Type</th>
                <th>Amount</th>
                <th>Fee</th>
                <th>Currency</th>
                <th>Transaction</th>
                <th>Status</th>
                <th>Txn Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>17-02-2022</td>
                <td>Cr.</td>
                <td>0.0</td>
                <td>100.0</td>
                <td>	INR</td>
                <td>	IMPS</td>
                <td><span className="text-success">Success</span></td>
                <td>
                  <div className="button-group">
                    <button className="btn btn-outline-primary" data-toggle="modal" data-target="#myModal">
                      <i className="fas fa-phone-alt"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        {/* <div className="content-message">
          <p>No Data Found</p>
        </div> */}

      </div>

      <div id="myModal" class="modal fade" role="dialog">
        <div className="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
          <div className="modal-content">
            <div className="modal-header">
              <h4 className="modal-title">Manage Client Details</h4>
              <button type="button" className="close" data-dismiss="modal">&times;</button>
            </div>
            <div className="modal-body">
              <div className="content-table">
                <table className="table table-bordered">
                  <thead>
                    <tr>
                      <th>Date</th>
                      <th>Tx Type</th>
                      <th>Amount</th>
                      <th>Fee</th>
                      <th>Currency</th>
                      <th>Transaction</th>
                      <th>Status</th>
                      <th>Txn Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>17-02-2022</td>
                      <td>Cr.</td>
                      <td>0.0</td>
                      <td>100.0</td>
                      <td>	INR</td>
                      <td>	IMPS</td>
                      <td><span className="text-success">Success</span></td>
                      <td>
                        <div className="button-group">
                          <button className="btn btn-outline-primary" >
                            <i className="fas fa-phone-alt"></i>
                          </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-light" data-dismiss="modal">Cancel</button>
              <button type="button" className="btn btn-primary" data-dismiss="modal">Save</button>
            </div>
          </div>
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
              <label className="form-group-label">Unique Number  : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" />
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

TransactionInquiry.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  transactionInquiry: makeSelectTransactionInquiry(),
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
)(TransactionInquiry);
