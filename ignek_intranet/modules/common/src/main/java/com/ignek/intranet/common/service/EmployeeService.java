package com.ignek.intranet.common.service;

import com.ignek.intranet.common.employee.response.EmployeeResponse;
import com.liferay.portal.kernel.exception.PortalException;

public interface EmployeeService {

	EmployeeResponse addUser(long empId, long userId, long companyId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException;

	EmployeeResponse updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException;

	EmployeeResponse deleteUser(long empId) throws PortalException, InstantiationException, IllegalAccessException;

}