package com.ignek.intranet.common.employee.service;

import java.util.Locale;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.employee.service.EmployeeLocalServiceUtil;
import com.liferay.calendar.model.Calendar;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

public class EmployeeServiceImpl implements EmployeeService {

	@Reference
	UserLocalService userLocalService;

	@Reference
	UserPersistence userPersistence;

	@Reference
	RoleLocalService roleLocalService;

	@Reference
	CounterLocalService counterLocalService;

	private Log log = LogFactoryUtil.getLog(this.getClass().getName());



	public User addUser(long userId,long userUId, String firstName, String lastName, long companyId,String emailAddress) throws PortalException {
		
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userUId);
//		ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), actionRequest);

//		long creatorUserId, long companyId, boolean autoPassword,
//		String password1, String password2, boolean autoScreenName,
//		String screenName, String emailAddress, java.util.Locale locale,
//		String firstName, String middleName, String lastName, long prefixId,
//		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
//		int birthdayYear, String jobTitle, long[] groupIds,
//		long[] organizationIds, long[] roleIds, long[] userGroupIds,
//		boolean sendEmail, ServiceContext serviceContext
		User user = null;
		try {
			user = UserLocalServiceUtil.addUser(userId, PortalUtil.getDefaultCompanyId(), true, StringPool.BLANK,
					StringPool.BLANK, true, StringPool.BLANK, emailAddress, LocaleUtil.getDefault(),
					firstName, StringPool.BLANK, lastName, GetterUtil.DEFAULT_LONG, GetterUtil.DEFAULT_LONG, false, 01, 01,
					1970, StringPool.BLANK, GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES,
					GetterUtil.DEFAULT_LONG_VALUES, GetterUtil.DEFAULT_LONG_VALUES, false, serviceContext);
			
			 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		long roleId =RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(),"Employee").getRoleId(); 
		
		RoleLocalServiceUtil.addUserRole(userUId+1, roleId);
		 
		
		return user;
	}
	
	public User updateUser(long userId,long userUid, String firstName, String lastName, long companyId,String emailAddress) throws PortalException  {
		User user = null;
		try {
			user=UserLocalServiceUtil.getUser(userUid);
//			user.setUserId(userUid);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(emailAddress);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return UserLocalServiceUtil.updateUser(user);
		
	}

}
