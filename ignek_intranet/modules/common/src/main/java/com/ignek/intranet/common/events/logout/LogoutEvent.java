package com.ignek.intranet.common.events.logout;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.common.events.listener.ActivityType;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

@Component(immediate = true, property = { "key=logout.events.pre",
		"service.ranking:Integer=1000" }, service = LifecycleAction.class)
public class LogoutEvent implements LifecycleAction {

	private static Log _log = LogFactoryUtil.getLog(LogoutEvent.class);

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private ObjectEntryLocalService objectEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService objectDefinitionLocalService;

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		try {
			long userId = PortalUtil.getUserId(lifecycleEvent.getRequest());
			Map<String, Serializable> map = new HashMap<String, Serializable>();
			map.put(IntranetConstants.ACTIVITY_CREATED_DATE, userLocalService.getUser(userId).getCreateDate());
			map.put(IntranetConstants.ACTIVITY_UPDATED_DATE, new Date());
			map.put(IntranetConstants.ACTIVITY_USER_ID, userId);
			map.put(IntranetConstants.ACTIVITY_IP_ADDRESS, Inet4Address.getLocalHost().getHostAddress());
			map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.LOGOUT.getValue());
			long objectDefinitionId = objectDefinitionLocalService
					.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
					.getObjectDefinitionId();
			objectEntryLocalService.addObjectEntry(userId, GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
					new ServiceContext());
		} catch (PortalException | UnknownHostException e) {
			_log.error(e.getMessage(), e);
		}
	}

}