package com.sdase.docvalidator.util;

/**
 * @author akif
 * @since 3/10/22
 */
public class Constants {

	public static final String EVENT_SEND_RCV_TOPIC = "EVENT_SEND_RCV_TOPIC";
	public static final String RESULT_EVENT_SEND_RCV_TOPIC = "RESULT_EVENT_SEND_RCV_TOPIC";
	public static final String GROUP_ID = "groupId";

	public static final String IBAN_VALIDATOR_URL = "https://openiban.com/validate/{iban}?getBIC=true&validateBankCode=true";
	public static final String IBAN_REGEX = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}";

	public static final String SUCCESS_MSG = "success";
	public static final String ERROR_MSG = "error";

	public static final String SEND_ERROR_DETAILS_MSG = "Invalid File URL!"
		+ "Please provide a local URL or a downloadable File URL with .doc/.pdf/.docx/.txt extensions";
	public static final String RCV_ERROR_DETAILS_MSG = "This Document does not have any IBAN in it. Therefore, "
		+ "{validationType} Check is ignored.";
}
