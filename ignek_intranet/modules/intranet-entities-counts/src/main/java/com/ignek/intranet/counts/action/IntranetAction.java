package com.ignek.intranet.counts.action;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import com.ignek.intranet.counts.config.IntranetConfig;
import com.ignek.intranet.counts.constants.IntranetConstant;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

import aQute.bnd.annotation.metatype.Configurable;

@Component(
	immediate = true,
	configurationPid = IntranetConstant.CONFIGURATION_ID,
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = "javax.portlet.name="+ IntranetConstant.PORTLET_ID,
	service = ConfigurationAction.class
)
public class IntranetAction extends DefaultConfigurationAction {

	private volatile IntranetConfig intranetConfiguration;

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		String card = ParamUtil.getString(actionRequest, IntranetConstant.CARD);
		setPreference(actionRequest, IntranetConstant.CARD, card);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		intranetConfiguration = Configurable.createConfigurable(IntranetConfig.class, properties);
	}

}