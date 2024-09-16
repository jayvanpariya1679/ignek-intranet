package com.ignek.portal.employee.web.crud.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.ignek.portal.employee.web.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.employee.model.Employee;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.common.service.EmployeeServiceImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author ignek
 */
@Component(immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.ignek.portal",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.display-name=EmployeeWeb",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/view.jsp",
			"javax.portlet.name=" + EmployeeWebConstants.EMPLOYEE_WEB_PORTLET_KEY,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class)
public class EmployeeWebPortlet extends MVCPortlet {

	@Reference
	private EmployeeService employeeService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean hasRole = false;
		List<Employee> employeeList = new ArrayList<>();
		try {
			Hits hits = employeeService.getDataList();
			for (Document document : hits.getDocs()) {
				Employee employee = new Employee();
				long empId = GetterUtil.getLong(document.get(IntranetConstants.EMP_ID));
				String firsrtName = document.get(IntranetConstants.FIRST_NAME);
				String lastName = document.get(IntranetConstants.LAST_NAME);
				String emailAddress = document.get(IntranetConstants.EMAIL_ADDRESS);
				long phoneNumber = GetterUtil.getLong(document.get(IntranetConstants.PHONE_NUMBER));
				String addressLine1 = document.get(IntranetConstants.ADDRESS_LINE_1);
				String addressLine2 = document.get(IntranetConstants.ADDRESS_LINE_2);
				String city = document.get(IntranetConstants.CITY);
				long zipCode = GetterUtil.getLong(document.get(IntranetConstants.ZIPCODE));
				String designation = document.get(IntranetConstants.DESIGNATION);
				employee.setEmpId(empId);
				employee.setFirstName(firsrtName);
				employee.setLastName(lastName);
				employee.setEmailAddress(emailAddress);
				employee.setPhoneNumber(phoneNumber);
				employee.setAddressLine1(addressLine1);
				employee.setAddressLine2(addressLine2);
				employee.setCity(city);
				employee.setZipCode(zipCode);
				employee.setDesignation(designation);
				employeeList.add(employee);
			}
			renderRequest.setAttribute(IntranetConstants.EMPLOYEE_LIST, employeeList);

			long desiredRoleId = _roleLocalService.getRole(themeDisplay.getCompanyId(), "HR").getRoleId();
			long[] userRoleIds = _userLocalService.getUser(themeDisplay.getUserId()).getRoleIds();
			List list = Arrays.stream(userRoleIds).boxed().collect(Collectors.toList());
			hasRole = list.contains(desiredRoleId);

			
		} catch (PortalException e) {
			_log.error(e.getMessage());
		}
		renderRequest.setAttribute("hasRole",hasRole);
		super.render(renderRequest, renderResponse);
	}

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private Log _log = LogFactoryUtil.getLog(EmployeeServiceImpl.class.getName());

}