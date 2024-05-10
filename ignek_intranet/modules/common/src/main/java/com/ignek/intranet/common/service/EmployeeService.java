package com.ignek.intranet.common.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.ignek.intranet.common.employee.response.EmployeeResponse;
import com.ignek.intranet.employee.model.Employee;
import com.itextpdf.text.DocumentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

public interface EmployeeService {

	EmployeeResponse addUser(long empId, long userId, long companyId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException;

	EmployeeResponse updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException;

	EmployeeResponse deleteUser(long empId) throws PortalException, InstantiationException, IllegalAccessException;

	Employee getEmployee(long empId) throws PortalException;

	long fetchUserIdByEmpId(long empId) throws PortalException;

	List<Employee> getEmployees(int start, int end) throws Exception;

	Hits getDataList() throws ParseException, SearchException;

	ByteArrayOutputStream getPDFDocument(long empId) throws DocumentException, PortalException;

	SearchContext setSearchContextData();

}