package com.ignek.intranet.employeerest.internal.resource.v1_0;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.ignek.intranet.employeerest.dto.v1_0.Employee;
import com.ignek.intranet.employeerest.resource.v1_0.EmployeeResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author ignek
 */
@Component(properties = "OSGI-INF/liferay/rest/v1_0/employee.properties", scope = ServiceScope.PROTOTYPE, service = EmployeeResource.class)
public class EmployeeResourceImpl extends BaseEmployeeResourceImpl {

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Reference
	private EmployeeService employeeService;

	public static final Log _log = LogFactoryUtil.getLog(EmployeeResourceImpl.class);

	@Override
	public Employee getEmployeeById(@NotNull Long empId) throws PortalException {
		com.ignek.intranet.employee.model.Employee employee = employeeService.getEmployee(empId);
		return getEmployeeData(employee);
	}

	public Employee getEmployeeData(com.ignek.intranet.employee.model.Employee employee) {
		Employee employeeObject = new Employee();
		employeeObject.setEmpId(employee.getEmpId());
		employeeObject.setUserId(employee.getUserId());
		employeeObject.setFirstName(employee.getFirstName());
		employeeObject.setLastName(employee.getLastName());
		employeeObject.setPhoneNumber(employee.getPhoneNumber());
		employeeObject.setEmailAddress(employee.getEmailAddress());
		employeeObject.setAddressLine1(employee.getAddressLine1());
		employeeObject.setAddressLine2(employee.getAddressLine2());
		employeeObject.setCity(employee.getCity());
		employeeObject.setZipCode(employee.getZipCode());
		employeeObject.setDesignation(employee.getDesignation());
		return employeeObject;
	}

	@Override
	public Employee updateEmployee(Employee employeeObject) throws Exception {
		long empId = employeeObject.getEmpId();
		long userId = employeeObject.getUserId();
		long currentUserId = contextCompany.getUserId();
		long companyId = contextCompany.getCompanyId();
		String firstName = employeeObject.getFirstName();
		String lastName = employeeObject.getLastName();
		String emailAddress = employeeObject.getEmailAddress();
		long phoneNumber = employeeObject.getPhoneNumber();
		String addressLine1 = employeeObject.getAddressLine1();
		String addressLine2 = employeeObject.getAddressLine2();
		String city = employeeObject.getCity();
		long zipCode = employeeObject.getZipCode();
		String designation = employeeObject.getDesignation();
		User user = null;
		try {
			user = Validator.isNotNull(empId)
					? employeeService.updateUser(userId, companyId, empId, firstName, lastName, emailAddress,
							phoneNumber, addressLine1, addressLine2, city, zipCode, designation)
					: employeeService.addUser(empId, currentUserId, companyId, firstName, lastName, emailAddress,
							phoneNumber, addressLine1, addressLine2, city, zipCode, designation);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return employeeObject;
	}

	@Override
	public Employee deleteEmployee(@NotNull Long empId) throws PortalException {
		employeeService.deleteUser(empId);
		Employee employeeObject = new Employee();
		employeeObject.setStatusMessage("Deleted Successfully");
		return employeeObject;
	}

	@Override
	public Page getEmployees(Pagination pagination) throws Exception {
		List<Employee> employeeObjects = new ArrayList<>();
		List<com.ignek.intranet.employee.model.Employee> employees = employeeService.getEmployees(pagination);
		for (com.ignek.intranet.employee.model.Employee employee : employees) {
			Employee employeeObject = getEmployeeData(employee);
			employeeObjects.add(employeeObject);
		}
		return Page.of(employeeObjects, pagination, employeeObjects.size());
	}

}