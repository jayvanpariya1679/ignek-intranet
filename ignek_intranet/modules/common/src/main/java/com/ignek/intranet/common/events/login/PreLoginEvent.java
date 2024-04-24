package com.ignek.intranet.common.events.login;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component(immediate = true, property = { "key=login.events.pre",
		"service.ranking:Integer=1000" }, service = LifecycleAction.class)
public class PreLoginEvent implements LifecycleAction {

	private static Log _log = LogFactoryUtil.getLog(PreLoginEvent.class);

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		_log.info("Pre Login");
	}

}
