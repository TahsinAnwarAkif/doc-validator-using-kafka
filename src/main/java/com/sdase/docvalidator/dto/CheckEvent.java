package com.sdase.docvalidator.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.io.Serializable;

/**
 * @author akif
 * @since 3/10/22
 */
public class CheckEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String HEADER_REQUESTER_SERVICE = "requester_service";

	private String url;

	private File file;

	private String fileType;

	public CheckEvent() {
	}

	public CheckEvent(String url, File file, String fileType) {
		this.url = url;
		this.file = file;
		this.fileType = fileType;
	}

	public String getUrl() {
		return url;
	}

	public CheckEvent setUrl(String url) {
		this.url = url;
		return this;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileType() {
		return fileType;
	}

	public CheckEvent setFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
