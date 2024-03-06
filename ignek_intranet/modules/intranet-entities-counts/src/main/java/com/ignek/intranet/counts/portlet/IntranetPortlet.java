package com.ignek.intranet.counts.portlet;

import com.ignek.intranet.counts.config.IntranetConfig;
import com.ignek.intranet.counts.constants.IntranetConstant;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import aQute.bnd.annotation.metatype.Configurable;
import java.io.IOException;
import java.util.Map;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author ignek
 */
@Component(
	immediate = true, 
	property = { 
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", 
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=IntranetEntitiesCounts", 
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", 
		"javax.portlet.name=" + IntranetConstant.PORTLET_ID,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" 
	},
	service = Portlet.class
)
public class IntranetPortlet extends MVCPortlet {
	
	private volatile IntranetConfig intranetConfiguration;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderRequest.setAttribute(IntranetConfig.class.getName(), intranetConfiguration);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		intranetConfiguration = Configurable.createConfigurable(IntranetConfig.class, properties);
	}
}