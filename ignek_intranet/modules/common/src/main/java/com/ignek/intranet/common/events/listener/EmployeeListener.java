package com.ignek.intranet.common.events.listener;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ignek.intranet.common.constants.IntranetConstants;
import com.ignek.intranet.employee.model.Employee;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

@Component(immediate = true, service = ModelListener.class)
public class EmployeeListener extends BaseModelListener<Employee> {

	private Log _log = LogFactoryUtil.getLog(EmployeeListener.class.getName());

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private ObjectEntryLocalService objectEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService objectDefinitionLocalService;

	@Override
	public void onBeforeUpdate(Employee originalModel, Employee model) throws ModelListenerException {
		_log.info("Before Update Employee");
		super.onBeforeUpdate(originalModel, model);
	}

	@Override
	public void onAfterUpdate(Employee originalModel, Employee model) throws ModelListenerException {
		_log.info("After Update Employee");

		try {
			Map<String, Serializable> map = objectData(model);
			map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.UPDATE.getValue());
			long objectDefinitionId = objectDefinitionLocalService
					.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
					.getObjectDefinitionId();
			objectEntryLocalService.addObjectEntry(model.getUserId(), GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
					new ServiceContext());
		} catch (PortalException | UnknownHostException e) {
			_log.error(e.getMessage(), e);
		}

		super.onAfterUpdate(originalModel, model);
	}

	@Override
	public void onBeforeCreate(Employee model) throws ModelListenerException {
		_log.info("Before Create Employee");
		super.onBeforeCreate(model);
	}

	@Override
	public void onAfterCreate(Employee model) throws ModelListenerException {
		_log.info("After Create Employee");
		try {
			Map<String, Serializable> map = objectData(model);
			map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.ADD.getValue());
			long objectDefinitionId = objectDefinitionLocalService
					.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
					.getObjectDefinitionId();
			objectEntryLocalService.addObjectEntry(model.getUserId(), GetterUtil.DEFAULT_LONG, objectDefinitionId, map,
					new ServiceContext());
		} catch (PortalException | UnknownHostException e) {
			_log.error(e.getMessage(), e);
		}
		super.onAfterCreate(model);
	}

	@Override
	public void onBeforeRemove(Employee model) throws ModelListenerException {
		_log.info("Before Remove Employee");
		try {
			Map<String, Serializable> map = objectData(model);
			map.put(IntranetConstants.ACTIVITY_TYPE, ActivityType.DELETE.getValue());
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			long objectDefinitionId = objectDefinitionLocalService
					.fetchObjectDefinition(PortalUtil.getDefaultCompanyId(), IntranetConstants.ACTIVITY_EVENT)
					.getObjectDefinitionId();

			objectEntryLocalService.addObjectEntry(serviceContext.getUserId(), GetterUtil.DEFAULT_LONG,
					objectDefinitionId, map, new ServiceContext());
		} catch (PortalException | UnknownHostException e) {
			_log.error(e.getMessage(), e);
		}
		super.onBeforeRemove(model);
	}

	@Override
	public void onAfterRemove(Employee model) throws ModelListenerException {
		_log.info("After Remove Employee");
		super.onAfterRemove(model);
	}

	public Map<String, Serializable> objectData(Employee model) throws PortalException, UnknownHostException {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put(IntranetConstants.ACTIVITY_CREATED_DATE, model.getCreateDate());
		map.put(IntranetConstants.ACTIVITY_UPDATED_DATE, new Date());
		map.put(IntranetConstants.ACTIVITY_USER_ID, model.getUserId());
		map.put(IntranetConstants.ACTIVITY_IP_ADDRESS, Inet4Address.getLocalHost().getHostAddress());
		return map;
	}

}