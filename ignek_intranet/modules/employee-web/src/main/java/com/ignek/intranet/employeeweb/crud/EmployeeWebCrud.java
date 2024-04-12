package com.ignek.intranet.employeeweb.crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.CommonConstants;
import com.ignek.intranet.common.employee.dto.Employee;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.employee.service.EmployeeLocalService;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author ignek
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeWeb", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.name=" + EmployeeWebConstants.PORTLET_ID,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)

public class EmployeeWebCrud extends MVCPortlet {

	@Reference
	private EmployeeLocalService employeeLocalService;

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private EmployeeService employeeService;
	
	@ProcessAction(name = "addEmployee")
	public ThemeDisplay accessActionData(ActionRequest actionRequest, ActionResponse actionResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		return themeDisplay;
	}

	@ProcessAction(name = "addEmployee")
	public void addEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		long empId = 0;
		long userId = accessActionData(actionRequest, actionResponse).getUserId();
		long companyId = accessActionData(actionRequest, actionResponse).getCompanyId();
		accessActionData(actionRequest, actionResponse);
		String firstName = ParamUtil.getString(actionRequest, CommonConstants.FIRST_NAME);
		String lastName = ParamUtil.getString(actionRequest, CommonConstants.LAST_NAME);
		String emailAddress = ParamUtil.getString(actionRequest, CommonConstants.EMAIL_ADDRESS);
		long phoneNumber = ParamUtil.getLong(actionRequest, CommonConstants.PHONE_NUMBER);
		String addressLine1 = ParamUtil.getString(actionRequest, CommonConstants.ADDRESS_LINE_1);
		String addressLine2 = ParamUtil.getString(actionRequest, CommonConstants.ADDRESS_LINE_2);
		String city = ParamUtil.getString(actionRequest, CommonConstants.CITY);
		long zipCode = ParamUtil.getLong(actionRequest, CommonConstants.ZIPCODE);
		String designation = ParamUtil.getString(actionRequest, CommonConstants.DESIGNATION);

		employeeService.addUser(empId, userId, companyId, firstName, lastName, emailAddress, phoneNumber, addressLine1,
				addressLine2, city, zipCode, designation);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		List<Employee> employeeList = new ArrayList<>();
		try {
			employeeList = employeeService.getDataList();
			renderRequest.setAttribute("employeeList", employeeList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		super.render(renderRequest, renderResponse);
	}

	@ProcessAction(name = "updateEmployee")
	public void updateEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		long empId = ParamUtil.getLong(actionRequest, CommonConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		long userUniqueId = employeeLocalService.fetchEmployee(empId).getUserId();
		long companyId = accessActionData(actionRequest, actionResponse).getCompanyId();
		String firstName = ParamUtil.getString(actionRequest, CommonConstants.FIRST_NAME, GetterUtil.DEFAULT_STRING);
		String lastName = ParamUtil.getString(actionRequest, CommonConstants.LAST_NAME, GetterUtil.DEFAULT_STRING);
		String emailAddress = ParamUtil.getString(actionRequest, CommonConstants.EMAIL_ADDRESS,
				GetterUtil.DEFAULT_STRING);
		long phoneNumber = ParamUtil.getLong(actionRequest, CommonConstants.PHONE_NUMBER, GetterUtil.DEFAULT_LONG);
		String addressLine1 = ParamUtil.getString(actionRequest, CommonConstants.ADDRESS_LINE_1,
				GetterUtil.DEFAULT_STRING);
		String addressLine2 = ParamUtil.getString(actionRequest, CommonConstants.ADDRESS_LINE_2,
				GetterUtil.DEFAULT_STRING);
		String city = ParamUtil.getString(actionRequest, CommonConstants.CITY, GetterUtil.DEFAULT_STRING);
		long zipCode = ParamUtil.getLong(actionRequest, CommonConstants.ZIPCODE, GetterUtil.DEFAULT_LONG);
		String designation = ParamUtil.getString(actionRequest, CommonConstants.DESIGNATION, GetterUtil.DEFAULT_STRING);
		employeeService.updateUser(userUniqueId, companyId, empId, firstName, lastName, emailAddress, phoneNumber,
				addressLine1, addressLine2, city, zipCode, designation);
	}

	@ProcessAction(name = "deleteEmployee")
	public void deleteEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
		long empId = ParamUtil.getLong(actionRequest, CommonConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		long empUserUniqueId = employeeLocalService.fetchEmployee(empId).getUserId();
		if (Validator.isNotNull(empUserUniqueId)) {
			long userUniqueId = userLocalService.getUser(empUserUniqueId).getUserId();
			employeeLocalService.deleteEmployee(empId);
			userLocalService.deleteUser(userUniqueId);
		}
	}
}