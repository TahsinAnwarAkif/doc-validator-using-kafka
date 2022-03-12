package com.sdase.docvalidator.controller;

import com.sdase.docvalidator.dto.AuthenticationRequest;
import com.sdase.docvalidator.service.LoginService;
import com.sdase.docvalidator.util.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sdase.docvalidator.util.Constants.SUCCESS_MSG;
import static com.sdase.docvalidator.util.Url.LOGIN;

/**
 * @author akif
 * @since 3/10/22
 */
@RestController
@RequestMapping(LOGIN)
public class AuthenticationController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	@ApiOperation(value = "${AuthenticationController.login}")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Something went wrong"), //
		@ApiResponse(code = 422, message = "Invalid username/password supplied")})
	public JSONObject login(@RequestBody @ApiParam(value = "authRequest", required = true)
								AuthenticationRequest authRequest) throws Exception {

		final JSONObject json = new JSONObject();
		json.put("status", SUCCESS_MSG);
		json.put("jwt", loginService.getJwtToken(authRequest.getUsername(), authRequest.getPassword()));

		return json;
	}
}
