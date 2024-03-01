package Count.Of.action;

import java.util.Map;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import Count.Of.config.CountOf;
import Count.Of.constants.CountOfPortletKeys;
import aQute.bnd.annotation.metatype.Configurable;

@Component(
		configurationPid = CountOfPortletKeys.CONFIGURATION_ID,
		configurationPolicy = ConfigurationPolicy.OPTIONAL,
		immediate = true,
		property = "javax.portlet.name=" + CountOfPortletKeys.PORLET_ID,
		service = ConfigurationAction.class
	)

	public class CountOfaction extends DefaultConfigurationAction {
	
		private volatile CountOf countConfiguration;

		@Override
		public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
				throws Exception {
			String card = ParamUtil.getString(actionRequest, "card");
			setPreference(actionRequest, "card", card);

			super.processAction(portletConfig, actionRequest, actionResponse);
		}

		@Activate
		@Modified
		protected void activate(Map<Object, Object> properties) {
			countConfiguration = Configurable.createConfigurable(CountOf.class, properties);
		}

	}