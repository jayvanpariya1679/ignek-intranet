package com.ignek.intranet.common.employee.response;

import javax.ws.rs.core.Response.Status;

public class EmployeeResponse {

	private Status status;
	private String message;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
