<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<%@ page import="com.liferay.petra.string.StringPool"%>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ page import="com.ignek.intranet.employee.service.EmployeeLocalService"%>
<%@ page import="com.liferay.counter.kernel.service.CounterLocalServiceUtil"%>
<%@ page import="com.liferay.info.pagination.Pagination"%>
<%@ page import="com.ignek.intranet.employee.service.EmployeeLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ignek.intranet.common.employee.model.Employee"%>
<%@ page import="com.ignek.portal.employee.web.crud.portlet.EmployeeWebPortlet"%>
<%@ page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>

<liferay-theme:defineObjects />
	
<portlet:defineObjects />