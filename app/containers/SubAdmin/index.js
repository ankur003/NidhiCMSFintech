/**
 *
 * SubAdmin
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
import makeSelectSubAdmin from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

import SlidingPane from "react-sliding-pane";
import "react-sliding-pane/dist/react-sliding-pane.css";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export function SubAdmin() {
  useInjectReducer({ key: 'subAdmin', reducer });
  useInjectSaga({ key: 'subAdmin', saga });

  const [filter, setFilter] = useState(false);
  const [slidingPane, setSlidingPane] = useState(false);
  const [previllageList, setPrevillageList] = useState();

  const [fullName, setFullName] = useState();
  const [mobileNumber, setMobileNumber] = useState();
  const [password, setPassword] = useState();
  const [privilageNames, setPrivilageNames] = useState([]);
  const [referralCode, setReferralCode] = useState();
  const [userEmail, setUserEmail] = useState();
  const [subAdminListMapping, setSubAdminListMapping] = useState([]);

  function refreshPage() {
    window.location.reload(false);
  }

  useEffect(() => {
    async function subAdminList() {
      let result = await fetch('http://localhost:1234/api/v1/admin/get-all-user?page=1&limit=150&isSubAdmin=true', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      }).then(response => response.json().then(data => ({
        data,
        status: response.status,
      }))).then(res => {
        if (res.status === 200) {
          setSubAdminListMapping(res.data.data)
        }
        else {
          toast.error("something went wrong");
        }
      })
    }
    subAdminList();
  }, [])

  async function setSlidingPaneHandeler() {
    setSlidingPane(true)
    let result = await fetch('http://localhost:1234/api/v1/admin/system-privileges', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
    }).then(response => {
      response.json().then(data => ({
        data,
        status: response.status,
      }))
        .then(res => {
          if (res.status === 200) {
            setPrevillageList(res.data)
          } else {
            toast.error("something went wrong");
          }
        })
    }
    );
  }

  function privilageNamesFun(privilegeName, index) {
    if (privilageNames.includes(privilegeName)) {
      let localArray = [...privilageNames];
      localArray.splice(index, 1);
      setPrivilageNames(localArray);
    }
    else {
      setPrivilageNames([...privilageNames, privilegeName])
    }
  }

  async function createSubAdmin() {
    let item = { fullName, userEmail, mobileNumber, referralCode, privilageNames, password }
    let result = await fetch('http://localhost:1234/api/v1/admin/create-sub-admin', {
      method: 'Post',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(item),
    }).then(response => {
      if (response.status === 201) {
        toast.success("Sub Admin Created Successfully");
      }
      else {
        response.json().then(data => ({
          data,
          status: response.status,
        })).then(res => {
          if (res.status != 201) {
            toast.error(res.data.error);
            toast.error(res.data.message)
          }
        })
      }
    })
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
                <button className="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Add Client" onClick={setSlidingPaneHandeler}>
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
              {subAdminListMapping && subAdminListMapping.map((data, i) =>
                <tr key={i}>
                  <td>{data.fullName}</td>
                  <td>{data.userEmail}</td>
                  <td>{data.mobileNumber}</td>
                  <td>
                    {data.privilageNames.split(",").map((foo, index) =>
                      <span key={index} className="badge badge-primary">{foo}</span>
                    )}
                  </td>
                  <td>
                    <div className="button-group">
                      <button className="btn btn-outline-primary" disabled>
                        <i className="fas fa-pen"></i>
                      </button>
                      <button className="btn btn-outline-danger" disabled>
                        <i className="fas fa-trash-alt"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              )}
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
        onRequestClose={() => { }}
      >
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Add Sub Admin</h5>
            <button type="button" className="btn" onClick={() => setSlidingPane(false)}><i className="fas fa-chevron-left"></i></button>
          </div>
          <div className="modal-body">
            <div className="form-group">
              <label className="form-group-label">Full Name : <i className="fas fa-asterisk"></i></label>
              <input type="text" className="form-control" placeholder="Enter Full Name" onChange={(e) => setFullName(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Email : <i className="fas fa-asterisk"></i></label>
              <input type="email" className="form-control" placeholder="Enter Email" onChange={(e) => setUserEmail(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Contact Number : <i className="fas fa-asterisk"></i></label>
              <input type="number" className="form-control" placeholder="Enter Contact Number" onChange={(e) => setMobileNumber(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Referral Code : <i className="fas fa-asterisk"></i></label>
              <input type="number" className="form-control" placeholder="Enter Referral Code" onChange={(e) => setReferralCode(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Password : <i className="fas fa-asterisk"></i></label>
              <input type="password" className="form-control" placeholder="Enter Password" onChange={(e) => setPassword(e.target.value)} />
            </div>
            <div className="form-group m-0">
              <label className="form-group-label">Select Privilege :</label>
              <ul className="privailage-item">
                {previllageList && previllageList.map((data, index) =>
                  <li className={privilageNames.includes(data.privilegeName) ? "active" : ""} key={index} onClick={() => privilageNamesFun(data.privilegeName, index)}>
                    <p>{data.privilegeName}</p>
                  </li>
                )}
              </ul>
            </div>
          </div>
        </div>
        <div className="modal-footer">
          <button type="button" className="btn btn-secondary" onClick={() => setSlidingPane(false)}>Cancel</button>
          <button type="button" className="btn btn-primary" onClick={createSubAdmin}>Save</button>
        </div>
      </SlidingPane>

      <ToastContainer position="bottom-left" />
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
