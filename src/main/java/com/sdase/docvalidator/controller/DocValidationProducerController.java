package com.sdase.docvalidator.controller;

import com.sdase.docvalidator.dto.CheckEvent;
import com.sdase.docvalidator.service.DocValidationService;
import com.sdase.docvalidator.util.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.sdase.docvalidator.util.Constants.*;
import static com.sdase.docvalidator.util.Url.SEND_EVENT;

/**
 * @author akif
 * @since 3/10/22
 */
@RestController
@RequestMapping(SEND_EVENT)
public class DocValidationProducerController {

	@Autowired
	private DocValidationService docValidationService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${DocValidationProducerController.sendEvent}")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Something went wrong"),
		@ApiResponse(code = 403, message = "Access Denied"),
		@ApiResponse(code = 422, message = "Username is already in use")})
	public JSONObject sendEvent(@RequestParam(defaultValue = "true") @ApiParam("isLocalFile") final boolean isLocalFile,
								@RequestBody @ApiParam(value = "event", required = true) CheckEvent event) throws IOException {

		final JSONObject json = new JSONObject();

		if (docValidationService.isEventInvalid(event)) {
			json.put("status", ERROR_MSG);
			json.put("message", SEND_ERROR_DETAILS_MSG);

			return json;
		}

		event = docValidationService.getPreparedCheckEvent(event, isLocalFile);

		json.put("status", SUCCESS_MSG);
		json.put("event", event);

		kafkaTemplate.send(EVENT_SEND_RCV_TOPIC, event);

		return json;
	}
}
