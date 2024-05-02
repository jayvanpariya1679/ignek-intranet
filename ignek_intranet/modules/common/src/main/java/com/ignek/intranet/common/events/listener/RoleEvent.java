package com.ignek.intranet.common.events.listener;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

@Component(immediate = true, service = ServiceWrapper.class)
public class RoleEvent extends UserLocalServiceWrapper {

	public RoleEvent() {
		super(null);
	}

	@Reference
	private RoleLocalService roleLocalService;

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private ObjectEntryLocalService objectEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService objectDefinitionLocalService;

	@Override
	public User updateUser(long userId, String oldPassword, String newPassword1, String newPassword2,
			boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName,
			String emailAddress, boolean hasPortrait, byte[] portraitBytes, String languageId, String timeZoneId,
			String greeting, String comments, String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
			String facebookSn, String jabberSn, String skypeSn, String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			ServiceContext serviceContext) throws PortalException {

		long hrRoleId = roleLocalService.fetchRole(PortalUtil.getDefaultCompanyId(), IntranetConstants.HR_ROLE)
				.getRoleId();
		long employeeRoleId = roleLocalService
				.fetchRole(PortalUtil.getDefaultCompanyId(), IntranetConstants.EMPLOYEE_ROLE).getRoleId();

		User user = userLocalService.fetchUser(userId);

		List<Long> userRoleIds = Arrays.stream(user.getRoleIds()).boxed().collect(Collectors.toList());

		List<Long> updatedRoleIds = Arrays.stream(roleIds).boxed().collect(Collectors.toList());

		if (!userRoleIds.equals(updatedRoleIds)) {
			try {
				Map<String, Serializable> map = new HashMap<>();
				map.put(IntranetConstants.ACTIVITY_CREATED_DATE, userLocalService.getUser(userId).getCreateDate());
				map.put(IntranetConstants.ACTIVITY_UPDATED_DATE, new Date());
				map.put(IntranetConstants.ACTIVITY_USER_ID, userId);
				map.put(IntranetConstants.ACTIVITY_IP_ADDRESS, Inet4Address.getLocalHost().getHostAddress());
				long objectDefinitionId = objectDefinitionLocalService
						.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
						.getObjectDefinitionId();

				if (userRoleIds.size() == updatedRoleIds.size() && ((!userRoleIds.contains(hrRoleId)
						&& updatedRoleIds.contains(hrRoleId))
						|| (!userRoleIds.contains(employeeRoleId) && updatedRoleIds.contains(employeeRoleId))
						|| (userRoleIds.contains(hrRoleId) && !updatedRoleIds.contains(hrRoleId))
						|| (userRoleIds.contains(employeeRoleId) && !updatedRoleIds.contains(employeeRoleId)))) {
					map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.ROLE_UPDATE.getValue());
					objectEntryLocalService.addObjectEntry(userId, GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
							new ServiceContext());
				} else if (userRoleIds.size() < updatedRoleIds.size()
						&& (!userRoleIds.contains(hrRoleId) && updatedRoleIds.contains(hrRoleId))
						|| (!userRoleIds.contains(employeeRoleId) && updatedRoleIds.contains(employeeRoleId))) {
					map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.ROLE_ASSIGN.getValue());
					objectEntryLocalService.addObjectEntry(userId, GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
							new ServiceContext());
				} else if (userRoleIds.size() > updatedRoleIds.size()
						&& (userRoleIds.contains(hrRoleId) && !updatedRoleIds.contains(hrRoleId))
						|| (userRoleIds.contains(employeeRoleId) && !updatedRoleIds.contains(employeeRoleId))) {
					map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.ROLE_DELETE.getValue());
					objectEntryLocalService.addObjectEntry(userId, GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
							new ServiceContext());
				}
			} catch (UnknownHostException e) {
				_log.error(e.getMessage(), e);
			}
		}
		return super.updateUser(userId, oldPassword, newPassword1, newPassword2, passwordReset, reminderQueryQuestion,
				reminderQueryAnswer, screenName, emailAddress, hasPortrait, portraitBytes, languageId, timeZoneId,
				greeting, comments, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn, jobTitle, groupIds,
				organizationIds, roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

	@Override
	public User addUser(long creatorUserId, long companyId, boolean autoPassword, String password1, String password2,
			boolean autoScreenName, String screenName, String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds, long[] organizationIds, long[] roleIds,
			long[] userGroupIds, boolean sendEmail, ServiceContext serviceContext) throws PortalException {

		User user = super.addUser(creatorUserId, companyId, autoPassword, password1, password2, autoScreenName,
				screenName, emailAddress, locale, firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				sendEmail, serviceContext);

		Map<String, Serializable> map = new HashMap<>();
		map.put(IntranetConstants.ACTIVITY_CREATED_DATE, userLocalService.getUser(user.getUserId()).getCreateDate());
		map.put(IntranetConstants.ACTIVITY_UPDATED_DATE, new Date());
		map.put(IntranetConstants.ACTIVITY_USER_ID, user.getUserId());
		try {
			map.put(IntranetConstants.ACTIVITY_IP_ADDRESS, Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			_log.error(e.getMessage(), e);
		}
		map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.ROLE_ASSIGN.getValue());
		long objectDefinitionId = objectDefinitionLocalService
				.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
				.getObjectDefinitionId();
		objectEntryLocalService.addObjectEntry(user.getUserId(), GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
				new ServiceContext());
		return user;
	}

	private Log _log = LogFactoryUtil.getLog(RoleEvent.class.getName());

}