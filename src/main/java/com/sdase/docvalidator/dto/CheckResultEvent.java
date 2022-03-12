package com.sdase.docvalidator.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;

/**
 * @author akif
 * @since 3/10/22
 */
public class CheckResultEvent {

	public enum StateEnum {
		OK, // check has passed successfully.
		SUSPICIOUS, // check has found something suspicious.
		ERROR, // check has failed in a technical way.
		IGNORED; // check has not been executed, due to some pre-conditions.

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	private StateEnum state;

	/**
	 * The name of the check.
	 */
	private String name;

	private File file;

	/**
	 * Result details from the check.
	 */
	private String details;

	public CheckResultEvent() {
	}

	public String getName() {
		return name;
	}

	public CheckResultEvent setName(String name) {
		this.name = name;
		return this;
	}

	public StateEnum getState() {
		return state;
	}

	public CheckResultEvent setState(StateEnum status) {
		this.state = status;
		return this;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getDetails() {
		return details;
	}

	public CheckResultEvent setDetails(String details) {
		this.details = details;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
