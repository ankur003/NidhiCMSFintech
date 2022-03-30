/**
 *
 * PendingClient
 *
 */

import React, { memo, useState, useEffect } from 'react';
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

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export function PendingClient() {
  useInjectReducer({ key: 'pendingClient', reducer });
  useInjectSaga({ key: 'pendingClient', saga });

  let user = localStorage.getItem('user-info');


  const [slidingPane, setSlidingPane] = useState(false);
  const [pendingList, setPendingList] = useState();
  const [noDataList, setNoDataList] = useState(false);

  function refreshPage() {
    window.location.reload(false);
  }


  useEffect(() => {
    const url = "http://localhost:1234/api/v1/user/get-all-user?page=1&limit=250&isSubAdmin=false&isAdmin=false&isUserVerified=true&kycStatus=UNDER_REVIEW";

    const fetchData = async () => {
      try {
        const result = await fetch(url, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
          }
        });
        if (result.status === 204) {
          setNoDataList(true)
          localStorage.setItem('user-info');
        }
        else if (result.status === 200) {
          localStorage.setItem('user-info');
          setNoDataList(false)
          const response = await result.json();
          setPendingList(response.data);
        }
        else {
          toast.error("something Went Wrong")
        }
      } catch (error) {
        toast.error(error.message);
      }
    };
    fetchData();
  }, []);



  return (
    <React.Fragment>
      <Helmet>
        <title>PendingClient</title>
        <meta name="description" content="Description of PendingClient" />
      </Helmet>
      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Pending Client</h6>
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
        {noDataList === false ?
          <div className="content-table">
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Mobile</th>
                  <th>Dob</th>
                  <th>Privilage Names</th>
                  <th>User id</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {pendingList && pendingList.map((data, i) =>
                  <tr key={i}>
                    <td className="text-capitalize">{data.fullName}</td>
                    <td>{data.userEmail}</td>
                    <td>{data.mobileNumber}</td>
                    <td>{data.dob ? data.dob : "Not Avaialble"}</td>
                    <td>{data.privilageNames ? data.privilageNames : "Not Avaialble"}</td>
                    <td>{data.userUuid}</td>
                    <td>
                      <div className="button-group">
                        <button className="btn btn-outline-success">
                          <i className="fas fa-thumbs-up"></i>
                        </button>
                        <button className="btn btn-outline-danger">
                          <i className="fas fa-trash-alt"></i>
                        </button>
                        <button className="btn btn-outline-primary">
                          <i className="fas fa-info"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
          :
          <div className="content-message">
            <p>No Data Found</p>
          </div>
        }
      </div>



      <SlidingPane
        className="sliding-pane"
        overlayClassName="sliding-pane-wrapper"
        isOpen={slidingPane || false}
        from="right"
        width="400px"
        onRequestClose={() => setSlidingPane(false)}
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

      <ToastContainer />
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
