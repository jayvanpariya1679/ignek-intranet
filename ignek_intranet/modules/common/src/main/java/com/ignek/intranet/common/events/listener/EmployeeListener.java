package com.ignek.intranet.common.events.listener;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Users_RolesTable;

@Component(immediate = true, service = ModelListener.class)
public class EmployeeListener extends BaseModelListener<User> {

	private Log log = LogFactoryUtil.getLog(EmployeeListener.class.getName());

	@Override
	public void onBeforeUpdate(User originalModel, User model) throws ModelListenerException {
		log.info("Before Update Employee");
		super.onBeforeUpdate(originalModel, model);
	}

	@Override
	public void onAfterUpdate(User originalModel, User model) throws ModelListenerException {
		log.info("After Update Employee");
		super.onAfterUpdate(originalModel, model);
	}

	@Override
	public void onBeforeCreate(User model) throws ModelListenerException {
		log.info("Before Create Employee");
		super.onBeforeCreate(model);
	}

	@Override
	public void onAfterCreate(User model) throws ModelListenerException {
		log.info("After Create Employee");
		super.onAfterCreate(model);
	}

	@Override
	public void onBeforeRemove(User model) throws ModelListenerException {
		log.info("Before Remove Employee");
		super.onBeforeRemove(model);
	}

	@Override
	public void onAfterRemove(User model) throws ModelListenerException {
		log.info("After Remove Employee");
		super.onAfterRemove(model);
	}

}
