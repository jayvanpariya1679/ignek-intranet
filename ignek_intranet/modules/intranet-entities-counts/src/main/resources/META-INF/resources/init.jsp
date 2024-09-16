<%@page import="com.ignek.intranet.counts.config.IntranetConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.ignek.intranet.counts.constants.IntranetConstant"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	IntranetConfig intranetConfiguration = (IntranetConfig) renderRequest.getAttribute(IntranetConfig.class.getName());
	String card = StringPool.BLANK;
	if (Validator.isNotNull(intranetConfiguration)) {
		card = portletPreferences.getValue("card", intranetConfiguration.card());
	}
%>