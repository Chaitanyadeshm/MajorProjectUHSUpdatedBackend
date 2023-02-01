package com.cybage.uhs.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.mail.javamail.JavaMailSender;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.PatientsAppointments;
import com.cybage.uhs.model.Users;

public class ConstantMethods {

//	@Autowired
//	private static JavaMailSender mailSender;

	private static final Logger LOG = LogManager.getLogger(ConstantMethods.class);
	
	public static APIResponseEntity successRespone(Object data, String message) {
		APIResponseEntity successResponse = new APIResponseEntity();
		successResponse.setHttpStatusCode(HttpStatus.OK.value());
		successResponse.setHttpStatus(HttpStatus.OK);
		successResponse.setStatus(true);
		successResponse.setData(data);
		successResponse.setMessage(message);
		return successResponse;
	}

	public static APIResponseEntity failureRespone(String message, HttpStatus... code) {
		APIResponseEntity failureResponse = new APIResponseEntity();
		if(code.length > 0) {
			failureResponse.setHttpStatus(code[0]);
			failureResponse.setHttpStatusCode(code[0].value());
		}else {
			failureResponse.setHttpStatus(HttpStatus.BAD_GATEWAY);
			failureResponse.setHttpStatusCode(HttpStatus.BAD_GATEWAY.value());
		}
		failureResponse.setStatus(false);
		failureResponse.setMessage(message);
		return failureResponse;
	}

	public static int generateOtp() {
		Random randomOTP = new Random();
		char[] otp = new char[ConstantVars.OTP_LENGTH];
		for (int i = 0; i < ConstantVars.OTP_LENGTH; i++) {
			otp[i] = (char) (randomOTP.nextInt(10) + 48);
		}
		return Integer.parseInt(String.valueOf(otp));
	}

	// @formatter:off
	public String emailLayout(String emailBody) {
		return "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
				+ "  <div style='margin:50px auto;width:70%;padding:20px 0'>"
				+ "    <div style='border-bottom:1px solid #eee'>"
				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
				+ "    </div>  <p style='font-size:1.1em'>" + 
				emailBody
				+ "    <br/> <p style='font-size:0.9em;'>Regards,<br/>Your UHS</p>"
				+ "    <hr style='border:none;border-top:1px solid #eee' />"
				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
				+ "    </div> </div";
	}
	// @formatter:on

	public static boolean isMailSentSuccessfully(MailTemplateModel mailTemplateModel) {
//		String recieversEmail = mailTemplateModel.getUser().getEmail();
//		String sendersEmail = "Trng2@evolvingsols.com";
//		String senderName = "Universal Health Services";
//		String subject = mailTemplateModel.getMailSubject();
//		String actualMailBody = mailTemplateModel.getMailBody();
//		
//		String completeMailBody = " <div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"
//				+ "  	<div style='margin:50px auto;width:70%;padding:20px 0'>"
//				+ "    <div style='border-bottom:1px solid #eee'>"
//				+ "      <a href='' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Universal Health Services</a>"
//				+ "    </div> "
//				+      actualMailBody
//				+ "    <p style='font-size:0.9em;'>Regards,<br/>Universal Health Services</p>"
//				+ "    <hr style='border:none;border-top:1px solid #eee' />"
//				+ "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
//				+ "      <p>Universal Health Services Inc.</p>   <p>1600 Amphitheatre Parkway</p> <p>Pune</p>"
//				+ "    </div> </div>	";
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		try {
//			helper.setFrom(sendersEmail, senderName);
//			helper.setTo(recieversEmail);
//			helper.setSubject(subject);
//			helper.setText(completeMailBody, true);
//		} catch (UnsupportedEncodingException | MessagingException e) {
//			LOG.error("An error occured while sending mail. " + e.getMessage());
//			return false;
//		}
//		mailSender.send(message);
		return true;
	}
	public static boolean isPrescriptionSavedSuccessfully(PatientsAppointments appointment, Users doctor, Users patient)
			{
		try (PDDocument document = new PDDocument()) {

			PDPage prescriptionPage = new PDPage();
			document.addPage(prescriptionPage);

			try (PDPageContentStream content = new PDPageContentStream(document, prescriptionPage)) {

				content.beginText();

				content.setFont(PDType1Font.TIMES_ROMAN, 28);
				content.setLeading(14.5f);

				content.newLineAtOffset(25, 700);
				
				String hospitalName = "Universal Health Services";
//	                content.setHorizontalScaling(0);
				content.showText(hospitalName);
				content.newLine();

				content.setFont(PDType1Font.TIMES_ROMAN, 12);
				content.setLeading(14.5f);

				content.newLineAtOffset(25, 700);
				String line1 = "Name: " + patient.getFirstname() + " " + patient.getLastname();
				content.showText(line1);
				content.newLine();
				String line2 = "Email: " + patient.getEmail();
				content.showText(line2);
				content.newLine();
				String line3 = "Mobile no.: " + patient.getMobile();
				content.showText(line3);
				content.newLine();
				content.newLine();

				String line4 = "Doctor' Name:" + doctor.getFirstname() + " " + doctor.getLastname();
				content.showText(line4);
				content.newLine();
				content.newLine();

				String line5 = "Prescription: " + appointment.getPrescription();
				content.showText(line5);
				content.newLine();

				String line6 = "Appointment Date:" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(appointment.getCreatedTime());
				content.showText(line6);
				content.newLine();

				String line7 = "Universal Health Services";
				content.showText(line7);
				content.newLine();

				content.endText();
			}
//			String prescriptionFileName = appointment.getPatientDetails().getUsersId() + "_" + appointment.getAppointmentId() 
//					+ ".pdf";

	            String prescriptionFileName = appointment.getPatientDetails().getUsersId()+"_"+appointment.getAppointmentId()+".pdf";
			String docFolderPath = "src/main/resources/prescription/" + appointment.getDoctorDetails().getUsersId();

			if (!Files.exists(Paths.get(docFolderPath))) {
				new File(docFolderPath).mkdirs();
			}

			document.save("src/main/resources/prescription/" + appointment.getDoctorDetails().getUsersId() + "/" + prescriptionFileName);
			return true;
		}catch (Exception e) {
			LOG.error("An error occured while saving prescription pdf: "+ e.getLocalizedMessage());
			return false;
		}
	}

}
