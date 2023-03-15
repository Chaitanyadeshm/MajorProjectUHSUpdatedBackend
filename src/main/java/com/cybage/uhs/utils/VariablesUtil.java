package com.cybage.uhs.utils;

public class VariablesUtil {

	public static final String ADMIN_EMAIL = "admin@cybage.com";
	public static final String TRY_AGAIN_LATER = "\nTry again later or write email to " + ADMIN_EMAIL;

	public static final int OTP_LENGTH = 6;
	public static final int MAX_LOGIN_ATTEMPTS_ALLOWED = 3;

	public static enum USERS_ROLE {
		 	ADMIN, DOCTOR, PATIENT, NURSE, RECEPTIONIST
	};

	public static enum ACCOUNT_STATUS {
		ACTIVE, INACTIVE, BLOCKED, BLOCKED_BY_ADMIN, DELETED
	};


	public static enum APPONTMENT_STATUS {
		PENDING, APPROVE, COMPLETED, REJECTED, DONE, CANCELED
	};

	public static enum COMPLAINT_STATUS {
		RESOLVED, UNRESOLVED, CANCELED
	};

	public static final String COMPLAINT_REGISTERED_SUCCESSFULLY = "Your complaint created successfully.\nComplaint details has been sent to your email.";
	public static final String COMPLAINT_REGISTERATION_FAILED = "Could not create complaint." + TRY_AGAIN_LATER;
	public static final String COMPLAINT_UPDATION_FAILED = "Could not update complaint." + TRY_AGAIN_LATER;
	public static final String COMPLAINT_UPDATED_SUCCESSFULLY = "Complaint updated successfully.";
	public static final String COMPLAINT_REMINDER_SENT_SUCCESSFULLY = "Reminder for your complaint has been sent successfully.";
	public static final String COMPLAINT_REMINDER_SENT_FAILED = "Could not send reminder." + TRY_AGAIN_LATER;

	public static final String USER_REGISTERED_SUCCESSFULLY = "Your account created successfully.\nVerification link has been sent to your email.";
	public static final String ALL_USERS_FETCHED_SUCCESSFULLY = "Users fetched successfully.";
	public static final String USERS_BY_USER_ROLE_FETCHED_SUCCESSFULLY = "Users of specific user role fetched successfully.";
	public static final String USERS_OF_USER_ROLE_NOT_PRESENT = "No users available of specific user role.";
	public static final String USER_ALREADY_REGISTERED_WITH_THIS_EMAIL = "Email already registered.\nTry logging in.";
	public static final String USER_REGISTERATION_FAILED = "Could not register account at this moment." + TRY_AGAIN_LATER;
	public static final String USER_ALREADY_EXISTS = "You already have an account with this email.\nTry logging in.";
	public static final String USER_ALREADY_REGISTERED = "Could not create account." + TRY_AGAIN_LATER;
	public static final String USER_UNBLOCKED_SUCCESSFULLY = "Your account has been unblocked successfully.";
	public static final String UNBLOCKING_USER_FAILED = "Your account could not be unblocked at this moment."
			+ TRY_AGAIN_LATER;
	
	public static final String ALL_DOCTORS_FETCHED_SUCCESSFULLY = "List of Doctor's details fetched successfully.";
	public static final String DOCTOR_REGISTERED_SUCCESSFULLY = "Doctor registered successfully.\nCredentials has been sent to his email.";
	public static final String DOCTOR_REGISTERATION_FAILED = "Could not register doctor." + TRY_AGAIN_LATER;
	public static final String DOCTOR__ACCORDING_TO_SPECIALIZATION_FETCHED_SUCCESSFULLY = "List of doctors according to specialization fetched successfully."
			+ TRY_AGAIN_LATER;
	public static final String DOCTOR__DETAILS_FETCHED_SUCCESSFULLY = "Doctors details fetched successfully.";

	public static final String ALL_PATIENTS_FETCHED_SUCCESSFULLY = "List of patients details fetched successfully.";
	public static final String NO_PATIENTS_AVAILABLE = "There are no patients available at this moment.";
	public static final String ALL_USERS_BY_ACCOUNT_STATUS_FETCHED_SUCCESSFULLY = "List of all users by thier account status fetched successfully.";
	public static final String USERS_WITH_ACCOUNT_STATUS_ARE_NOT_PRESENT = "There are no users present with account status you entered.";
	public static final String ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED = "Could not fetch list of users by thier account status.";

	public static final String ERROR_RESPONSE = "Something went wrong." + TRY_AGAIN_LATER;
	public static final String USER_DELETED_SUCCESSFULLY = "Your account has been deleted successfully.";
	public static final String USER_ALREADY_DELETED = "Your account had been already deleted.";
	public static final String USER_UPDATED_FAILED = "Could not update user." + TRY_AGAIN_LATER;
	public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully.";
	public static final String USERS_DATA_BY_USER_ID = "User's data by user's Id fetched successfully.";
	public static final String INVALID_USER_ID = "Could not find data for entered user's data id.";
	public static final String EMAIL_NOT_REGISTERED= "No user is registered with this email.\nTry registering to our application first.";
	public static final String OTP_SENT_SUCCESSFULLY = "OTP has been sent successfully to your registerd email.";

	public static final String VERIFY_ACCOUNT_BEFORE_LOGIN = "Your account is not verified yet.\nCheck your email for verification link.";
	public static final String ACCOUNT_BLOCKED = "Your account is blocked." + TRY_AGAIN_LATER;
	public static final String ACCOUNT_BLOCKED_BY_ADMIN = "Your account has been blocked by admin." + TRY_AGAIN_LATER;
	public static final String ACCOUNT_DELETED = "Your account is deleted.\nCreate new account to use this application.";
	public static final String OTP_VERIFIED_SUCCESSFULLY = "OTP verified successfully.";
	public static final String WRONG_OTP_ENTERED = "You have entered wrong OTP.";
	public static final String MAX_OTP_ENTERED_EXCEEDED = "You have entered wrong OTP 3 times.\nYour account has been blocked. Write email to admin@cybage.com";
	public static final String COULD_NOT_FOUND__USERS_ACCOUNT = "Could not found your account.\n";

	public static final String ALL_APPOINTMENT_FETCHED_SUCCESSFULLY = "Appointments fetched successfully. \nYou will get further updates on your mail.";
	public static final String APPOINTMENT_FETCHED_SUCCESSFULLY = "Appointment fetched successfully.";
	public static final String APPOINTMENT_FOR_ADMIN_FETCHED_SUCCESSFULLY = "Appointments for admin fetched successfully.";
	public static final String APPOINTMENT_FOR_PATIENT_FETCHED_SUCCESSFULLY = "Appointments for patient fetched successfully.";
	public static final String APPOINTMENT_FOR_USER_FETCHED_SUCCESSFULLY = "Appointments for user fetched successfully.";
	public static final String APPOINTMENT_ADDED_SUCCESSFULLY = "Your appointment is booked successfully.\nYou will get further updates on your mail.";
	public static final String APPOINTMENT_ADDED_FAILED = "Could not book your appointment." + TRY_AGAIN_LATER;
	public static final String APPOINTMENT_IVALID_ID = "Could not find appointment.\nInvalid appointment Id";
	public static final String APPOINTMENT_STATUS_UPDATED_SUCCESSFULLY = "Appointment updated successfully.";
	public static final String APPOINTMENT_PRESCRIPTION_UPDATED_SUCCESSFULLY = "Prescription added successfully.";
	public static final String APPOINTMENT_PRESCRIPTION_UPDATION_FAILED = "Could not add prescription at this moment."
			+ TRY_AGAIN_LATER;

	public static final String SOMETHING_WENT_WRONG = "Something went wrong." + TRY_AGAIN_LATER;

	public static final String ALL_SPECIALIZATION_FETCHED_SUCCESSFULLY = "List of all specialization fetched successfully.";
	public static final String SPECIALIZATION_ADDDED_SUCCESSFULLY = "New Specialization added successfully.";

	// @formatter:off
	
	public static final String EMAIL_VERIFIED_PAGE = "<!DOCTYPE html>" + 
			"<html>" + 
			"" + 
			"<head>" + 
			"    <title></title>" + 
			"    <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" + 
			"    <meta name='viewport' content='width=device-width, initial-scale=1'>" + 
			"    <meta http-equiv='X-UA-Compatible' content='IE=edge' />" + 
			"    <style type='text/css'>" + 
			"        @media screen {" + 
			"            @font-face {" + 
			"                font-family: 'Lato';" + 
			"                font-style: normal;" + 
			"                font-weight: 400;" + 
			"                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');" + 
			"            }" + 
			"" + 
			"            @font-face {" + 
			"                font-family: 'Lato';" + 
			"                font-style: normal;" + 
			"                font-weight: 700;" + 
			"                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');" + 
			"            }" + 
			"" + 
			"            @font-face {" + 
			"                font-family: 'Lato';" + 
			"                font-style: italic;" + 
			"                font-weight: 400;" + 
			"                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');" + 
			"            }" + 
			"" + 
			"            @font-face {" + 
			"                font-family: 'Lato';" + 
			"                font-style: italic;" + 
			"                font-weight: 700;" + 
			"                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');" + 
			"            }" + 
			"        }" + 
			"" + 
			"        /* CLIENT-SPECIFIC STYLES */" + 
			"        body," + 
			"        table," + 
			"        td," + 
			"        a {" + 
			"            -webkit-text-size-adjust: 100%;" + 
			"            -ms-text-size-adjust: 100%;" + 
			"        }" + 
			"" + 
			"        table," + 
			"        td {" + 
			"            mso-table-lspace: 0pt;" + 
			"            mso-table-rspace: 0pt;" + 
			"        }" + 
			"" + 
			"        img {" + 
			"            -ms-interpolation-mode: bicubic;" + 
			"        }" + 
			"" + 
			"        /* RESET STYLES */" + 
			"        img {" + 
			"            border: 0;" + 
			"            height: auto;" + 
			"            line-height: 100%;" + 
			"            outline: none;" + 
			"            text-decoration: none;" + 
			"        }" + 
			"" + 
			"        table {" + 
			"            border-collapse: collapse !important;" + 
			"        }" + 
			"" + 
			"        body {" + 
			"            height: 100% !important;" + 
			"            margin: 0 !important;" + 
			"            padding: 0 !important;" + 
			"            width: 100% !important;" + 
			"        }" + 
			"" + 
			"        /* iOS BLUE LINKS */" + 
			"        a[x-apple-data-detectors] {" + 
			"            color: inherit !important;" + 
			"            text-decoration: none !important;" + 
			"            font-size: inherit !important;" + 
			"            font-family: inherit !important;" + 
			"            font-weight: inherit !important;" + 
			"            line-height: inherit !important;" + 
			"        }" + 
			"" + 
			"        /* MOBILE STYLES */" + 
			"        @media screen and (max-width:600px) {" + 
			"            h1 {" + 
			"                font-size: 32px !important;" + 
			"                line-height: 32px !important;" + 
			"            }" + 
			"        }" + 
			"" + 
			"        /* ANDROID CENTER FIX */" + 
			"        div[style*='margin: 16px 0;'] {" + 
			"            margin: 0 !important;" + 
			"        }" + 
			"    </style>" + 
			"</head>" + 
			"" + 
			"<body style='background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;'>" + 
			"    <!-- HIDDEN PREHEADER TEXT -->" + 
			"    <div" + 
			"        style='display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family:  Lato, Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;'>" + 
			"    </div>" + 
			"    <table border='0' cellpadding='0' cellspacing='0' width='100%'>" + 
			"        <!-- LOGO -->" + 
			"        <tr>" + 
			"            <td bgcolor='#73c1d4' align='center'>" + 
			"                <table border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px;'>" + 
			"                    <tr>" + 
			"                        <td align='center' valign='top' style='padding: 40px 10px 40px 10px;'> </td>" + 
			"                    </tr>" + 
			"                </table>" + 
			"            </td>" + 
			"        </tr>" + 
			"        <tr>" + 
			"            <td bgcolor='#73c1d4' align='center' style='padding: 0px 10px 0px 10px;'>" + 
			"                <table border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px;'>" + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='center' valign='top'" + 
			"                            style='padding: 40px 20px 20px 20px; border-radius: 10px 10px 0px 0px; color: #111111; font-family: Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;'>" + 
			"                            <h1 style='font-size: 48px; font-weight: 400; margin: 2;'>Welcome!</h1> <img" + 
			"                                src=' https://img.icons8.com/clouds/100/000000/handshake.png' width='125' height='120'" + 
			"                                style='display: block; border: 0px;' />" + 
			"                        </td>" + 
			"                    </tr>" + 
			"                </table>" + 
			"            </td>" + 
			"        </tr>" + 
			"        <tr>" + 
			"            <td bgcolor='#f4f4f4' align='center' style='padding: 0px 10px 0px 10px;'>" + 
			"                <table border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px;'>" + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='left'" + 
			"                            style='padding: 20px 30px 40px 30px; color: #666666; font-family: Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;'>" + 
			"                            <p style='margin: 0;'><h2>Your account has been verified successfully.</h2>" + 
			"                                Use below button to login to your account.</p>" + 
			"                        </td>" + 
			"                    </tr>" + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='left'>" + 
			"                            <table width='100%' border='0' cellspacing='0' cellpadding='0'>" + 
			"                                <tr>" + 
			"                                    <td bgcolor='#ffffff' align='center' style='padding: 20px 30px 60px 30px;'>" + 
			"                                        <table border='0' cellspacing='0' cellpadding='0'>" + 
			"                                            <tr>" + 
			"                                                <td align='center' style='border-radius: 3px;' bgcolor='#73c1d4'><a" + 
			"                                                        href='http://localhost:3000/' target='_blank'" + 
			"                                                        style='font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 10px; border: 1px solid #73c1d4; display: inline-block;'>"
			+ "															Login to UHS</a></td>" + 
			"                                            </tr>" + 
			"                                        </table>" + 
			"                                    </td>" + 
			"                                </tr>" + 
			"                            </table>" + 
			"                        </td>" + 
			"                    </tr> " + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='left'" + 
			"                            style='padding: 20px 30px 20px 30px; color: #666666; font-family: Lato, Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;'>" + 
			"                            <p style='margin: 0;'><a href='#' target='_blank'" + 
			"                                    style='color: #73c1d4;'></a></p>" + 
			"                        </td>" + 
			"                    </tr>" + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='left'" + 
			"                            style='padding: 0px 30px 20px 30px; color: #666666; font-family: Lato, Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;'>" + 
			"                            <p style='margin: 0;'>If you have any questions, just reply to this email admin@cybage.com we're always" + 
			"                                happy to help out.</p>" + 
			"                        </td>" + 
			"                    </tr>" + 
			"                    <tr>" + 
			"                        <td bgcolor='#ffffff' align='left'" + 
			"                            style='padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: Lato, Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;'>" + 
			"                            <hr style='border:none;border-top:1px solid #eee' />" + 
			"                            <div" + 
			"                                style='float:left;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>" + 
			"                                <p  style='margin: 0;'>Universal Health Services Inc.</p>" + 
			"                                <p  style='margin: 0;'>1600 Amphitheatre Parkway</p>" + 
			"                                <p  style='margin: 0;'>Pune</p>" + 
			"                            </div>" + 
			"                        </td>" + 
			"                    </tr>" + 
			"                </table>" + 
			"            </td>" + 
			"        </tr>" + 
			"    </table>" + 
			"</body>" + 
			"" + 
			"</html>";
	
	// @formatter:on
}
