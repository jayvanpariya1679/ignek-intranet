package Count.Of.config;

import Count.Of.constants.CountOfPortletKeys;
import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = CountOfPortletKeys.CONFIGURATION_ID)
public interface CountOf {
	@Meta.AD(deflt ="Employees",
			name = "card",
			optionLabels = { "%Employees", "%Technologies", "%Images","%Users" },
			optionValues = { "Employees", "Technologies","Images", "Users"},
			required = false
		)
		public String card();
}
