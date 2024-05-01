package com.ignek.intranet.common.service;

import java.util.List;

import com.ignek.intranet.employee.model.Employee;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.vulcan.pagination.Pagination;

public interface EmployeeService {

	User addUser(long empId, long userId, long companyId, String firstName, String lastName, String emailAddress,
			long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode, String designation)
			throws PortalException;

	User updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException;

	void deleteUser(long empId) throws PortalException;

	Employee getEmployee(long empId) throws PortalException;

	long fetchEmployeeById(long empId) throws PortalException;

	List<Employee> getEmployees(Pagination pagination) throws Exception;

	Hits getDataList() throws ParseException, SearchException;

}
