package com.ignek.intranet.common.events.logout;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component(immediate = true, property = { "key=logout.events.post",
		"service.ranking:Integer=1000" }, service = LifecycleAction.class)
public class PostLogoutEvent implements LifecycleAction {

	private static Log _log = LogFactoryUtil.getLog(PreLogoutEvent.class);

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		_log.info("Post Logout");
	}

}
