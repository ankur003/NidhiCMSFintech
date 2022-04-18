/**
 *
 * ManageClient
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
import makeSelectManageClient from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';
import { method } from 'lodash';

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export function ManageClient() {
  useInjectReducer({ key: 'manageClient', reducer });
  useInjectSaga({ key: 'manageClient', saga });

  // let user = localStorage.getItem('user-info');


  const [filter, setFilter] = useState(true);
  const [merchantId, setMerchantId] = useState();
  const [pancard, setPancard] = useState();
  const [userEmail, setUserEmail] = useState();
  const [contactNumber, setContactNumber] = useState();
  const [manageList, setManageList] = useState();
  const [noDataList, setNoDataList] = useState(true);
  const [panImageData, setPanImageData] = useState('');
  const [adharImageData, setAdharImageData] = useState('');
  const [isfetchPanImage, setIsfetchPanImage] = useState(true);
  const [isfetchAdharImage,setIsfetchAdharImage] =  useState(true);

  function refreshPage() {
    window.location.reload(false);
  }

  async function getUserDocument(userUuid) {
    console.warn(userUuid);
    let url = 'http://localhost:1234/api/v1/admin/get/all-documents/user/' + userUuid;
    let result = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
    }).then(response => {
      if (response.status == 204) {

      }
      else {
        response.json().then(data => ({
          data,
          status: response.status,
        }))
          .then(res => {
            if (res.status === 200) {
              setAdharImageData(res.data.DOCUMENT_AADHAR)
              setPanImageData(res.data.DOCUMENT_PAN)
            } else {
              setAdharImageData(' ')
              setPanImageData(' ')
            }
          })
      }
    }
    );
  }

  async function filterHandeler() {
    let url = `http://localhost:1234/api/v1/admin/get-filter-users?`;
    if (merchantId !== undefined) {
      url = url + "merchantId=" + merchantId + "&";
    }
    if (pancard !== undefined) {
      url = url + "pancard=" + pancard + "&";
    }
    if (userEmail !== undefined) {
      url = url + "userEmail=" + userEmail + "&";
    }
    if (contactNumber !== undefined) {
      url = url + "contactNumber=" + contactNumber + "&";
    }
    let result = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
    }).then(response => {
      if (response.status == 204) {
        setNoDataList(true)
      }
      else {
        response.json().then(data => ({
          data,
          status: response.status,
        }))
          .then(res => {
            if (res.status === 200) {
              setNoDataList(false)
              setManageList(res.data);
            } else {
              toast.error("error");
            }
          })
      }
    }
    );
  }

  const onImageChange = (e) => {
    setIsfetchPanImage(false);
    const [file] = e.target.files;
    setPanImageData(URL.createObjectURL(file));
  };

  const onImageAdaharChange = (e) => {
    setIsfetchAdharImage(false);
    const [file] = e.target.files;
    setAdharImageData(URL.createObjectURL(file));
  };

  return (
    <React.Fragment>
      <Helmet>
        <title>ManageClient</title>
        <meta name="description" content="Description of ManageClient" />
      </Helmet>

      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Manage Client</h6>
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
        {noDataList === false ?
          <div className="content-table">
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Mobile</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {manageList && manageList.map((data, i) =>
                  <tr key={i}>
                    <td>{data.fullName}</td>
                    <td>{data.email}</td>
                    <td>{data.mobile}</td>
                    <td>
                      <div className="button-group">
                        <button onClick={() => getUserDocument(data.userUuid)} className="btn btn-outline-primary" data-toggle="modal" data-target="#myModal">
                          <i className="fas fa-check"></i>
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

      <div id="myModal" className="modal fade" role="dialog">
        <div className="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
          <div className="modal-content">
            <div className="modal-header">
              <h4 className="modal-title">Manage Client Details</h4>
              <button type="button" className="close" data-dismiss="modal">&times;</button>
            </div>
            <div className="modal-body">
              <ul className="nav nav-pills mb-3" id="pills-tab" role="tablist">
                <li className="nav-item" role="presentation">
                  <button className="nav-link active" id="pills-PersonalKYC-tab" data-bs-toggle="pill" data-bs-target="#pills-PersonalKYC" type="button" role="tab" aria-controls="pills-PersonalKYC" aria-selected="true">Personal KYC</button>
                </li>
                <li className="nav-item" role="presentation">
                  <button className="nav-link" id="pills-BusinessDetails-tab" data-bs-toggle="pill" data-bs-target="#pills-BusinessDetails" type="button" role="tab" aria-controls="pills-BusinessDetails" aria-selected="false">Business Details</button>
                </li>
                <li className="nav-item" role="presentation">
                  <button className="nav-link" id="pills-BankDetails-tab" data-bs-toggle="pill" data-bs-target="#pills-BankDetails" type="button" role="tab" aria-controls="pills-BankDetails" aria-selected="false">Bank Details</button>
                </li>
                <li className="nav-item" role="presentation">
                  <button className="nav-link" id="pills-Deactivate-tab" data-bs-toggle="pill" data-bs-target="#pills-Deactivate" type="button" role="tab" aria-controls="pills-Deactivate" aria-selected="true">Deactivate</button>
                </li>
                <li className="nav-item" role="presentation">
                  <button className="nav-link" id="pills-BillingCharges-tab" data-bs-toggle="pill" data-bs-target="#pills-BillingCharges" type="button" role="tab" aria-controls="pills-BillingCharges" aria-selected="false">Billing Charges</button>
                </li>
                <li className="nav-item" role="presentation">
                  <button className="nav-link" id="pills-WhiteListIP-tab" data-bs-toggle="pill" data-bs-target="#pills-WhiteListIP" type="button" role="tab" aria-controls="pills-WhiteListIP" aria-selected="false">White List IP</button>
                </li>
              </ul>
              <div className="tab-content" id="pills-tabContent">
                <div className="tab-pane fade show active" id="pills-PersonalKYC" role="tabpanel" aria-labelledby="pills-PersonalKYC-tab">
                  <div className="d-flex">
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Upload PAN : <i className="fas fa-asterisk"></i></label>
                        <div className="uplaod-image">
                          <input type="file" className="form-control" onChange={onImageChange} />
                          <p>Click to Upload Image</p>
                          <div className="uploaded-image">
                            <img src={isfetchPanImage ? `data:image/jpeg;base64,${panImageData}` : panImageData} />
                            <button className="btn">
                              <input type="file" onChange={onImageChange} />
                              <span><i className="fas fa-pen"></i></span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Upload Aadhar card : <i className="fas fa-asterisk"></i></label>
                        <div className="uplaod-image">
                          <input type="file" className="form-control" onChange={onImageAdaharChange}/>
                          <p>Click to Upload Image</p>
                          <div className="uploaded-image">
                            <img src={isfetchAdharImage ? `data:image/jpeg;base64,${adharImageData}` : adharImageData} />
                            <button className="btn">
                              <input type="file" onChange={onImageAdaharChange}/>
                              <span><i className="fas fa-pen"></i></span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="tab-pane fade" id="pills-BusinessDetails" role="tabpanel" aria-labelledby="pills-BusinessDetails-tab">
                  <div className="d-flex">
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Business Entity Type : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Industry : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Register Company Name : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" placeholder="Golden Sparrow Nidhi Limited" />
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">No.of Employees : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-33 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Individual Pan : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-33 pd-l-7 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">GST Number : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-33 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Business Website Link : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>

                    </div>
                  </div>
                </div>

                <div className="tab-pane fade" id="pills-BankDetails" role="tabpanel" aria-labelledby="pills-BankDetails-tab">
                  <div className="d-flex">
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Bank Account Holder Name : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">Bank Name : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-50 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Account Number : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-50 pd-l-7">
                      <div className="form-group">
                        <label className="form-group-label">IFSC : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                  </div>
                </div>
                <div className="tab-pane fade" id="pills-Deactivate" role="tabpanel" aria-labelledby="pills-Deactivate-tab">
                  <div className="form-group">
                    <label className="form-group-label">Account : </label>
                    <div className="d-flex">
                      <div className="flex-25 pd-r-7">
                        <label className="radio-button">
                          <span className="radio-text">Active</span>
                          <input type="radio" name="radio" />
                          <span className="checkmark"></span>
                        </label>
                      </div>
                      <div className="flex-25 pd-l-7">
                        <label className="radio-button">
                          <span className="radio-text">Deactive</span>
                          <input type="radio" name="radio" />
                          <span className="checkmark"></span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <div className="form-group">
                    <label className="form-group-label"> Message : </label>
                    <textarea rows="5" className="form-control" />
                  </div>
                </div>
                <div className="tab-pane fade" id="pills-BillingCharges" role="tabpanel" aria-labelledby="pills-BillingCharges-tab">
                  <div className="d-flex">
                    <div className="flex-100">
                      <div className="form-group">
                        <label className="form-group-label">Product : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Set Charges : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Bill Charge : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-15 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Amount : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-35 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Account : </label>
                        <div className="d-flex">
                          <div className="flex-50 pd-r-7">
                            <label className="radio-button">
                              <span className="radio-text">Active</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                          <div className="flex-50 pd-l-7">
                            <label className="radio-button">
                              <span className="radio-text">Deactive</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Set Charges : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Bill Charge : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-15 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Amount : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-35 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Account : </label>
                        <div className="d-flex">
                          <div className="flex-50 pd-r-7">
                            <label className="radio-button">
                              <span className="radio-text">Active</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                          <div className="flex-50 pd-l-7">
                            <label className="radio-button">
                              <span className="radio-text">Deactive</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Set Charges : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-25 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Bill Charge : <i className="fas fa-asterisk"></i></label>
                        <select className="form-control">
                          <option value="">select</option>
                        </select>
                      </div>
                    </div>
                    <div className="flex-15 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Amount : <i className="fas fa-asterisk"></i></label>
                        <input type="text" className="form-control" />
                      </div>
                    </div>
                    <div className="flex-35 pd-r-7">
                      <div className="form-group">
                        <label className="form-group-label">Account : </label>
                        <div className="d-flex">
                          <div className="flex-50 pd-r-7">
                            <label className="radio-button">
                              <span className="radio-text">Active</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                          <div className="flex-50 pd-l-7">
                            <label className="radio-button">
                              <span className="radio-text">Deactive</span>
                              <input type="radio" name="radio" />
                              <span className="checkmark"></span>
                            </label>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>
                <div className="tab-pane fade" id="pills-WhiteListIP" role="tabpanel" aria-labelledby="pills-WhiteListIP-tab">
                  <div className="form-group">
                    <label className="form-group-label">Enter IP : <i className="fas fa-asterisk"></i></label>
                    <input type="text" className="form-control" />
                  </div>
                </div>
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
              <label className="form-group-label">Merchant Id :</label>
              <input type="text" className="form-control" placeholder="Enter Merchant Id" onChange={e => setMerchantId(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Pan Card :</label>
              <input type="text" className="form-control" placeholder="Enter Pan Card" onChange={e => setPancard(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Email :</label>
              <input type="email" className="form-control" placeholder="Enter Email" onChange={e => setUserEmail(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-group-label">Contact Number :</label>
              <input type="number" className="form-control" placeholder="Enter Contact Number" onChange={e => setContactNumber(e.target.value)} />
            </div>
          </div>
          <div className="content-filter-footer">
            <button className="btn btn-light" onClick={() => setFilter(false)}>Cancel</button>
            <button className="btn btn-success" onClick={filterHandeler}>Search</button>
          </div>
        </div>
      }

      <ToastContainer position="bottom-left" />
    </React.Fragment>
  );
}

ManageClient.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  manageClient: makeSelectManageClient(),
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
)(ManageClient);
