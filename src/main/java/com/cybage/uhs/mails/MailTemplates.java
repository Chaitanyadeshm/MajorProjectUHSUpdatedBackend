package com.cybage.uhs.mails;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cybage.uhs.model.Users;

public class MailTemplates {
	@Autowired
	private static JavaMailSender mailSender;

	public static boolean sendOTP(Users user) {

		String toAddress = user.getEmail();
		String fromAddress = "Trng2@evolvingsols.com";
		String senderName = "Universal Health Services";
		String subject = "OTP for Sign In to your account ";

		String newContent = " <div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
				+ "  <div style='margin:50px auto;width:70%;padding:20px 0'>"
				+ "    <div style='border-bottom:1px solid #eee'>"
				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
				+ "    </div>  <p style='font-size:1.1em'>Hi,</p>"
				+ "    <p>Thank you for choosing UHS. Use the following OTP to login to your account.</p>"
				+ "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"
				+ user.getUsersOTP() + "</h2>"
				+ "    <p style='font-size:0.9em;'>Regards,<br/>Universal Health Services</p>"
				+ "    <hr style='border:none;border-top:1px solid #eee' />"
				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
				+ "    </div> </div>	";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(newContent, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

		mailSender.send(message);

		return true;
	}
	
	public static boolean sendUserAccountUnblockedMail(Users user) {

		String toAddress = user.getEmail();
		String fromAddress = "Trng2@evolvingsols.com";
		String senderName = "Universal Health Services";
		String subject = "Account Unblocked";

		String newContent = " <div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
				+ "  <div style='margin:50px auto;width:70%;padding:20px 0'>"
				+ "    <div style='border-bottom:1px solid #eee'>"
				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
				+ "    </div>  <p style='font-size:1.1em'>Hi,</p>"
				+ "    <p>Thank you for choosing UHS. Use the following OTP to login to your account.</p>"
				+ "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'> "
						
				+ "		</h2>"
				+ "    <p style='font-size:0.9em;'>Regards,<br/>Universal Health Services</p>"
				+ "    <hr style='border:none;border-top:1px solid #eee' />"
				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
				+ "    </div> </div>	";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(newContent, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

		mailSender.send(message);

		return true;
	}

}
