package com.ignek.intranet.employeerest.client.serdes.v1_0;

import com.ignek.intranet.employeerest.client.dto.v1_0.Employee;
import com.ignek.intranet.employeerest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author ignek
 * @generated
 */
@Generated("")
public class EmployeeSerDes {

	public static Employee toDTO(String json) {
		EmployeeJSONParser employeeJSONParser = new EmployeeJSONParser();

		return employeeJSONParser.parseToDTO(json);
	}

	public static Employee[] toDTOs(String json) {
		EmployeeJSONParser employeeJSONParser = new EmployeeJSONParser();

		return employeeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Employee employee) {
		if (employee == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (employee.getAddressLine1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressLine1\": ");

			sb.append("\"");

			sb.append(_escape(employee.getAddressLine1()));

			sb.append("\"");
		}

		if (employee.getAddressLine2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressLine2\": ");

			sb.append("\"");

			sb.append(_escape(employee.getAddressLine2()));

			sb.append("\"");
		}

		if (employee.getCity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"city\": ");

			sb.append("\"");

			sb.append(_escape(employee.getCity()));

			sb.append("\"");
		}

		if (employee.getDesignation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"designation\": ");

			sb.append("\"");

			sb.append(_escape(employee.getDesignation()));

			sb.append("\"");
		}

		if (employee.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(employee.getEmailAddress()));

			sb.append("\"");
		}

		if (employee.getEmpId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"empId\": ");

			sb.append(employee.getEmpId());
		}

		if (employee.getFirstName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(employee.getFirstName()));

			sb.append("\"");
		}

		if (employee.getLastName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(employee.getLastName()));

			sb.append("\"");
		}

		if (employee.getPhoneNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneNumber\": ");

			sb.append(employee.getPhoneNumber());
		}

		if (employee.getStatusCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statusCode\": ");

			sb.append(employee.getStatusCode());
		}

		if (employee.getStatusMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statusMessage\": ");

			sb.append("\"");

			sb.append(_escape(employee.getStatusMessage()));

			sb.append("\"");
		}

		if (employee.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(employee.getUserId());
		}

		if (employee.getZipCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"zipCode\": ");

			sb.append(employee.getZipCode());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		EmployeeJSONParser employeeJSONParser = new EmployeeJSONParser();

		return employeeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Employee employee) {
		if (employee == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (employee.getAddressLine1() == null) {
			map.put("addressLine1", null);
		}
		else {
			map.put("addressLine1", String.valueOf(employee.getAddressLine1()));
		}

		if (employee.getAddressLine2() == null) {
			map.put("addressLine2", null);
		}
		else {
			map.put("addressLine2", String.valueOf(employee.getAddressLine2()));
		}

		if (employee.getCity() == null) {
			map.put("city", null);
		}
		else {
			map.put("city", String.valueOf(employee.getCity()));
		}

		if (employee.getDesignation() == null) {
			map.put("designation", null);
		}
		else {
			map.put("designation", String.valueOf(employee.getDesignation()));
		}

		if (employee.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put("emailAddress", String.valueOf(employee.getEmailAddress()));
		}

		if (employee.getEmpId() == null) {
			map.put("empId", null);
		}
		else {
			map.put("empId", String.valueOf(employee.getEmpId()));
		}

		if (employee.getFirstName() == null) {
			map.put("firstName", null);
		}
		else {
			map.put("firstName", String.valueOf(employee.getFirstName()));
		}

		if (employee.getLastName() == null) {
			map.put("lastName", null);
		}
		else {
			map.put("lastName", String.valueOf(employee.getLastName()));
		}

		if (employee.getPhoneNumber() == null) {
			map.put("phoneNumber", null);
		}
		else {
			map.put("phoneNumber", String.valueOf(employee.getPhoneNumber()));
		}

		if (employee.getStatusCode() == null) {
			map.put("statusCode", null);
		}
		else {
			map.put("statusCode", String.valueOf(employee.getStatusCode()));
		}

		if (employee.getStatusMessage() == null) {
			map.put("statusMessage", null);
		}
		else {
			map.put(
				"statusMessage", String.valueOf(employee.getStatusMessage()));
		}

		if (employee.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(employee.getUserId()));
		}

		if (employee.getZipCode() == null) {
			map.put("zipCode", null);
		}
		else {
			map.put("zipCode", String.valueOf(employee.getZipCode()));
		}

		return map;
	}

	public static class EmployeeJSONParser extends BaseJSONParser<Employee> {

		@Override
		protected Employee createDTO() {
			return new Employee();
		}

		@Override
		protected Employee[] createDTOArray(int size) {
			return new Employee[size];
		}

		@Override
		protected void setField(
			Employee employee, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addressLine1")) {
				if (jsonParserFieldValue != null) {
					employee.setAddressLine1((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "addressLine2")) {
				if (jsonParserFieldValue != null) {
					employee.setAddressLine2((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "city")) {
				if (jsonParserFieldValue != null) {
					employee.setCity((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "designation")) {
				if (jsonParserFieldValue != null) {
					employee.setDesignation((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					employee.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "empId")) {
				if (jsonParserFieldValue != null) {
					employee.setEmpId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				if (jsonParserFieldValue != null) {
					employee.setFirstName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				if (jsonParserFieldValue != null) {
					employee.setLastName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "phoneNumber")) {
				if (jsonParserFieldValue != null) {
					employee.setPhoneNumber(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statusCode")) {
				if (jsonParserFieldValue != null) {
					employee.setStatusCode(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statusMessage")) {
				if (jsonParserFieldValue != null) {
					employee.setStatusMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					employee.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "zipCode")) {
				if (jsonParserFieldValue != null) {
					employee.setZipCode(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}