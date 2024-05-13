package com.ignek.intranet.common.service;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.employee.response.EmployeeResponse;
import com.ignek.intranet.common.util.CommonUtil;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, service = EmployeeService.class)
public class EmployeeServiceImpl implements EmployeeService {

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private RoleLocalService roleLocalService;

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Reference
	private CommonUtil commonUtil;

	@Override
	public EmployeeResponse addUser(long empId, long userId, long companyId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException {
		ServiceContext serviceContext = commonUtil.getServiceContext(companyId, userId);
		User user = null;
		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {
			user = userLocalService.addUser(userId, PortalUtil.getDefaultCompanyId(), true, StringPool.BLANK,
					StringPool.BLANK, true, StringPool.BLANK, emailAddress, LocaleUtil.getDefault(), firstName,
					StringPool.BLANK, lastName, GetterUtil.DEFAULT_LONG, GetterUtil.DEFAULT_LONG, false,
					Integer.parseInt(IntranetConstants.BIRTHDAY_MONTH),
					Integer.parseInt(IntranetConstants.BIRTHDAY_DAY), Integer.parseInt(IntranetConstants.BIRTHDAY_YEAR),
					StringPool.BLANK, GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES,
					GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES, false, serviceContext);
			userId = user.getUserId();
			long roleId = roleLocalService.getRole(PortalUtil.getDefaultCompanyId(), IntranetConstants.EMPLOYEE_ROLE)
					.getRoleId();
			roleLocalService.addUserRole(userId, roleId);
			employeeLocalService.addEmployee(empId, userId, companyId, firstName, lastName, emailAddress, phoneNumber,
					addressLine1, addressLine2, city, zipCode, designation);
			employeeResponse.setStatus(Response.Status.OK);
			employeeResponse.setMessage(IntranetConstants.EMPLOYEE_CREATED_STATUS_MESSAGE);
		} catch (Exception e) {
			if (e.getMessage().contains(emailAddress)) {
				employeeResponse.setStatus(Response.Status.CONFLICT);
				employeeResponse.setMessage(IntranetConstants.ERROR_FOR_EXISTING_EMAIL_STATUS_MESSAGE);
			} else {
				employeeResponse.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
				employeeResponse.setMessage(IntranetConstants.EMPLOYEE_NOT_CREATED_STATUS_MESSAGE);
			}
			_log.error(e.getMessage(), e);
		}
		return employeeResponse;
	}

	@Override
	public EmployeeResponse updateUser(long userUniqueId, long companyId, long empId, String firstName, String lastName,
			String emailAddress, long phoneNumber, String addressLine1, String addressLine2, String city, long zipCode,
			String designation) throws PortalException, InstantiationException, IllegalAccessException {
		User user = null;
		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {
			user = userLocalService.getUser(userUniqueId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(emailAddress);
			user = userLocalService.updateUser(user);

			employeeLocalService.updateEmployee(userUniqueId, companyId, empId, firstName, lastName, emailAddress,
					phoneNumber, addressLine1, addressLine2, city, zipCode, designation);

			employeeResponse.setStatus(Response.Status.OK);
			employeeResponse.setMessage(IntranetConstants.EMPLOYEE_UPDATED_STATUS_MESSAGE);
		} catch (Exception e) {
			if (e.getMessage().contains(IntranetConstants.CONSTRAINT_VIOLATION_EXCEPTION)) {
				employeeResponse.setStatus(Response.Status.CONFLICT);
				employeeResponse.setMessage(IntranetConstants.ERROR_FOR_EXISTING_EMAIL_STATUS_MESSAGE);
			} else {
				employeeResponse.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
				employeeResponse.setMessage(IntranetConstants.EMPLOYEE_NOT_UPDATED_STATUS_MESSAGE);
			}
			_log.error(e.getMessage(), e);
		}
		return employeeResponse;
	}

	@Override
	public EmployeeResponse deleteUser(long empId)
			throws PortalException, InstantiationException, IllegalAccessException {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {
			if (Validator.isNotNull(empId)) {
				userLocalService.deleteUser(commonUtil.fetchUserIdByEmpId(empId));
				employeeLocalService.deleteEmployee(empId);
				employeeResponse.setStatus(Response.Status.OK);
				employeeResponse.setMessage(IntranetConstants.EMPLOYEE_DELETED_STATUS_MESSAGE);
			}
		} catch (Exception e) {
			employeeResponse.setStatus(Response.Status.CONFLICT);
			employeeResponse.setMessage(IntranetConstants.EMPLOYEE_NOT_DELETED_STATUS_MESSAGE);
			_log.error(e.getMessage(), e);
		}
		return employeeResponse;
	}

	private Log _log = LogFactoryUtil.getLog(EmployeeServiceImpl.class.getName());

}