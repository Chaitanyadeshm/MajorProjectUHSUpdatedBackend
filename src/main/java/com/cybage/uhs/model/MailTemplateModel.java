package com.cybage.uhs.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Entity
public class MailTemplateModel {
	private String mailSubject;
	private String mailBody;
	private Users user;


}
