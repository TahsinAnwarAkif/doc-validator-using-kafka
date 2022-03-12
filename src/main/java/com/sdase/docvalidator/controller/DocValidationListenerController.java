package com.sdase.docvalidator.controller;

import com.sdase.docvalidator.dto.CheckEvent;
import com.sdase.docvalidator.dto.CheckResultEvent;
import com.sdase.docvalidator.service.DocValidationService;
import com.sdase.docvalidator.util.JSONObject;
import com.sdase.docvalidator.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import static com.sdase.docvalidator.dto.CheckResultEvent.StateEnum.*;
import static com.sdase.docvalidator.util.Constants.*;
import static com.sdase.docvalidator.util.FileType.getFileType;
import static com.sdase.docvalidator.util.FileUtils.getText;
import static com.sdase.docvalidator.util.ValidationType.MONEY_LAUNDERING;

/**
 * @author akif
 * @since 3/11/22
 */
@Component
public class DocValidationListenerController {

	private static final Logger log = LoggerFactory.getLogger(DocValidationListenerController.class);

	@Autowired
	private DocValidationService docValidationService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@KafkaListener(topics = EVENT_SEND_RCV_TOPIC, groupId = GROUP_ID)
	public void listenToCheckEvent(final String msg) throws IOException {
		final CheckResultEvent result = new CheckResultEvent();
		final CheckEvent event = JSONObject.getObjectFromJson(msg, CheckEvent.class);
		final String text = getText(event.getFile().getAbsolutePath(), getFileType(event.getFileType()));
		final Set<String> ibanSet = StringUtils.getMatchedStringSet(text, IBAN_REGEX);

		result.setName(MONEY_LAUNDERING.getNaturalName());

		if (ibanSet.isEmpty()) {
			log.info("No IBAN found in the doc!");

			result.setState(IGNORED);
			result.setDetails(RCV_ERROR_DETAILS_MSG.replace("{validationType}", result.getName()));
		} else {
			for (String iban : ibanSet) {
				final JSONObject validatedResultJson = docValidationService.getValidatedIbanDetailsJson(iban);

				log.info("Validated Result for IBAN {} is: {}", iban, validatedResultJson);

				result.setState((boolean) validatedResultJson.getOrDefault("valid", false) ? OK : SUSPICIOUS);
				result.setDetails(validatedResultJson.toString());
			}
		}

		result.setFile(event.getFile());

		kafkaTemplate.send(RESULT_EVENT_SEND_RCV_TOPIC, result);
	}

	@KafkaListener(topics = RESULT_EVENT_SEND_RCV_TOPIC, groupId = GROUP_ID)
	public void listenToCheckResultEvent(final String msg) throws IOException {
		final CheckResultEvent event = JSONObject.getObjectFromJson(msg, CheckResultEvent.class);
		final boolean deletePerformed = event.getFile().delete();

		log.info(event.getFile().getName() + " Validated File Deletion Result: {}", deletePerformed);
	}
}
