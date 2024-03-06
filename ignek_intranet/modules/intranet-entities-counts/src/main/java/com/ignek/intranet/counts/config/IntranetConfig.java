package com.ignek.intranet.counts.config;

import com.ignek.intranet.counts.constants.IntranetConstant;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = IntranetConstant.CONFIGURATION_ID)
public interface IntranetConfig {
	@Meta.AD(
		deflt = "Employees",
		name = "card",
		optionLabels = { "%Employees", "%Technologies", "%Images", "%Users" },
		optionValues = { "Employees", "Technologies", "Images", "Users" },
		required = false
	)
	public String card();
}
