package com.ignek.intranet.employeeweb.crud.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.service.EmployeeService;
import com.ignek.intranet.employeeweb.constants.EmployeeWebConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + EmployeeWebConstants.PORTLET_ID,
		"mvc.command.name=updateEmployee" }, service = MVCActionCommand.class)
public class UpdateMVCActionCommand extends BaseMVCActionCommand {

	@Reference
	private EmployeeService employeeService;

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long empId = ParamUtil.getLong(actionRequest, IntranetConstants.EMP_ID, GetterUtil.DEFAULT_LONG);
		long userId = themeDisplay.getUserId();
		long userUniqueId = GetterUtil.DEFAULT_LONG;
		if (Validator.isNull(userUniqueId)) {
			userUniqueId = employeeService.fetchEmployeeById(empId);
		}
		long companyId = themeDisplay.getCompanyId();
		String firstName = ParamUtil.getString(actionRequest, IntranetConstants.FIRST_NAME, GetterUtil.DEFAULT_STRING);
		String lastName = ParamUtil.getString(actionRequest, IntranetConstants.LAST_NAME, GetterUtil.DEFAULT_STRING);
		String emailAddress = ParamUtil.getString(actionRequest, IntranetConstants.EMAIL_ADDRESS,
				GetterUtil.DEFAULT_STRING);
		long phoneNumber = ParamUtil.getLong(actionRequest, IntranetConstants.PHONE_NUMBER, GetterUtil.DEFAULT_LONG);
		String addressLine1 = ParamUtil.getString(actionRequest, IntranetConstants.ADDRESS_LINE_1,
				GetterUtil.DEFAULT_STRING);
		String addressLine2 = ParamUtil.getString(actionRequest, IntranetConstants.ADDRESS_LINE_2,
				GetterUtil.DEFAULT_STRING);
		String city = ParamUtil.getString(actionRequest, IntranetConstants.CITY, GetterUtil.DEFAULT_STRING);
		long zipCode = ParamUtil.getLong(actionRequest, IntranetConstants.ZIPCODE, GetterUtil.DEFAULT_LONG);
		String designation = ParamUtil.getString(actionRequest, IntranetConstants.DESIGNATION,
				GetterUtil.DEFAULT_STRING);

		if (Validator.isNull(empId)) {
			employeeService.addUser(empId, userId, companyId, firstName, lastName, emailAddress, phoneNumber,
					addressLine1, addressLine2, city, zipCode, designation);
		} else {
			employeeService.updateUser(userUniqueId, companyId, empId, firstName, lastName, emailAddress, phoneNumber,
					addressLine1, addressLine2, city, zipCode, designation);
		}

	}

}