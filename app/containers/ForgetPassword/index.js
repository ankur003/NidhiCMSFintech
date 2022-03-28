/**
 *
 * ForgetPassword
 *
 */

import React, { memo, useState } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import history from 'utils/history';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectForgetPassword from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function ForgetPassword() {
	useInjectReducer({ key: 'forgetPassword', reducer });
	useInjectSaga({ key: 'forgetPassword', saga });

	const [toggleButton, setToggleButton] = useState(true);
	const [toggleForm, setToggleForm] = useState(false);
	const [username, setUsername] = useState();
	const [forgotPassType, setForgotPassType] = useState("EMAIL");
	const [methodButton, setMethodButton] = useState(true);
	const [otpUuid, setOtpUuid] = useState();
	const [otp, setOtp] = useState();
	const [confirmPass, setConfirmPass] = useState();
	const [newPass, setNewPass] = useState();
	const [isloader, setloader] = useState(false);


	function selectMethodHandeler() {
		if (toggleButton === false) {
			setToggleButton(true);
			var passwordType = "EMAIL"
		}
		else {
			setToggleButton(false);
			var passwordType = "PHONE"
		}
		setForgotPassType(passwordType);
	}

	async function sendOtp() {
		setloader(true)
		let item = { forgotPassType, username };
		let result = await fetch('http://localhost:1234/api/v1/user/forgot-password',
			{
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(item),
			}).then(response =>
				response.json().then(data => (
					{
						data: data,
						status: response.status
					})
				).then(res => {
					setloader(false)
					if (res.status === 201) {
						if (forgotPassType === "EMAIL") {
							toast.success("OTP Send to your email Please Enter")
							setToggleForm(true)
							setMethodButton(false)
							setOtpUuid(res.data.otpUuid)
						}
						else {
							toast.success("OTP Send to your Mobile Number Please Enter")
							setToggleForm(true)
							setMethodButton(false)
							setOtpUuid(res.data.otpUuid)
						}
					} else {
						toast.error(res.data.message)
					}
				}));

	}

	async function veryfyOtp() {
		setloader(true)
		let updatePassword = { confirmPass, newPass, otp, otpUuid, }
		let resultUpdatePass = await fetch('http://localhost:1234/api/v1/user/update-password-by-otp',
			{
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(updatePassword),
			}).then(response => {
				if (response.status == 200) {
					setloader(false)
					toast.success("password Updated succesfully")
					history.push('/LoginPage');
				}
				response.json().then(data => (
					{
						data: data,
						status: response.status
					})
				).then(res => {
					setloader(false)
					if (res.status === 200) {
						toast.success("password Updated succesfully")
						history.push('/LoginPage')
					} else {
						toast.error(res.data.message)
						setToggleForm(true)
					}
				})
			});
	}



	return (
		<React.Fragment>
			<Helmet>
				<title>ForgetPassword</title>
				<meta name="description" content="Description of ForgetPassword" />
			</Helmet>
			<div className="login-wrapper-outer">
				<div className="login-inner-box">
					<div className="login-header">
						<div className="login-logo">
							<img src={require('../../assets/images/logo.png')} />
						</div>
					</div>
					<div className="login-content-box">
						<div className="login-body">
							<div className="form-group">
								<h5>Forget Password</h5>
								<p>Enter your email address and we'll send you an email with instructions to reset your password.</p>
							</div>

							{methodButton &&
								<ul className="list-tyle-none login-toggle">
									<li className={toggleButton ? "active" : ""} onClick={selectMethodHandeler}>
										EMAIL
                </li>
									<li className={toggleButton ? "" : "active"} onClick={selectMethodHandeler}>
										PHONE
                </li>
								</ul>
							}

							{toggleButton === true ?
								<React.Fragment>
									<div className="form-group">
										<label className="form-group-label">Email</label>
										<input type="email" className="form-control" onChange={(e) => setUsername(e.target.value)} placeholder="Enter your Email" />
									</div>
								</React.Fragment>
								:
								<React.Fragment>
									<div className="form-group">
										<label className="form-group-label">Phone Number</label>
										<input type="number" className="form-control" onChange={(e) => setUsername(e.target.value)} placeholder="Enter your Contact Number" />
									</div>
								</React.Fragment>
							}

							{methodButton &&
								<div className="form-group-button">
									<button className="btn btn-primary" onClick={sendOtp}>
										{isloader ? <i className="fas fa-spinner fa-pulse"></i> : "Submit"}
									</button>
								</div>
							}

							{toggleForm == true &&
								<React.Fragment>
									<div className="d-flex">
										<div className="flex-100">
											<div className="form-group">
												<label className="form-group-label">Enter OTP</label>
												<input type="number" className="form-control" placeholder="Enter your Email" onChange={(e) => setOtp(e.target.value)} />
											</div>
										</div>
										<div className="flex-50 pd-r-7">
											<div className="form-group">
												<label className="form-group-label">New Password</label>
												<input type="password" className="form-control" placeholder="Enter your new password" onChange={(e) => setNewPass(e.target.value)} />
											</div>
										</div>
										<div className="flex-50 pd-l-7">
											<div className="form-group">
												<label className="form-group-label">Confirm Password</label>
												<input type="password" className="form-control" placeholder="Enter your confirm password" onChange={(e) => setConfirmPass(e.target.value)} />
											</div>
										</div>
									</div>
									<div className="form-group-button">
										<button className="btn btn-primary" onClick={veryfyOtp}>
											{isloader ? <i className="fas fa-spinner fa-pulse"></i> : "Submit"}
										</button>
									</div>
								</React.Fragment>
							}
						</div>
						<div className="login-footer">
							<p>back to <a href="/LoginPage">Log In</a> <a href="/LoginPage">Home</a></p>
						</div>
					</div>
				</div>
			</div>

			<ToastContainer />

		</React.Fragment >
	);
}

ForgetPassword.propTypes = {
	dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
	forgetPassword: makeSelectForgetPassword(),
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
)(ForgetPassword);
