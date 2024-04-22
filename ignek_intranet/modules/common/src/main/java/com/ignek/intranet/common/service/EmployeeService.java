package com.ignek.intranet.common.service;

import java.util.List;

import com.ignek.intranet.employee.model.Employee;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.vulcan.pagination.Pagination;

public interface EmployeeService {

	User addUser(long empId, long userId, long companyId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation)
			throws PortalException;

	User updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException;

	User deleteUser(long empId, long userUniqueId) throws PortalException;

	Employee getEmployee(long empId) throws PortalException;

	long fetchEmployee(long empId);

	List<com.ignek.intranet.employee.model.Employee> getEmployees(Pagination pagination) throws Exception;

	List<com.ignek.intranet.common.employee.dto.Employee> getDataList() throws ParseException;

}
