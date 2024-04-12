package com.ignek.intranet.common.service;

import java.util.List;

import com.ignek.intranet.common.employee.dto.Employee;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.ParseException;

public interface EmployeeService {

	User addUser(long empId, long userId, long companyId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation)
			throws PortalException;

	User updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException;

	List<Employee> getDataList() throws ParseException;

}
