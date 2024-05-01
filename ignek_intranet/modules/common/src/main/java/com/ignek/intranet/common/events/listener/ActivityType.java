package com.ignek.intranet.common.events.listener;

public  enum ActivityType {
	ADD("Add Employee", "addEmployee"),
	UPDATE("Update Employee","updateEmployee"),
	DELETE("Delete Employee","deleteEmployee"),
	ROLE_ASSIGN("Role Assign","roleAssign"),
	ROLE_UPDATE("Role Update","roleUpdate"),
	ROLE_DELETE("Role Delete","roleDelete"),
	LOGIN("Login","login"),
	LOGOUT("Logout","logout");

	private String label;
	private String value;

	ActivityType(String key, String value) {
		this.label = key;
		this.value = value;
	}

	public String getKey() {
		return label;
	}

	public String getValue() {
		return value;
	}

}