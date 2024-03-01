package Count.Of.portlet;

import Count.Of.config.CountOf;
import Count.Of.constants.CountOfPortletKeys;
import aQute.bnd.annotation.metatype.Configurable;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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
		"javax.portlet.display-name=CountOf",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CountOfPortletKeys.COUNTOF,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CountOfPortlet extends MVCPortlet {
	private volatile CountOf countConfiguration;
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderRequest.setAttribute(CountOf.class.getName(), countConfiguration);
		
		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		countConfiguration = Configurable.createConfigurable(CountOf.class, properties);
	}
}