<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>

<%--
<h4>
	<bean:message bundle="MOBILITY_RESOURCES" key="activity.CreateWorkerJobOffer"/>
</h4>
--%>

<fr:edit id="CreateWorkerJobOffer" name="information" schema="activityInformation.CreateWorkerJobOffer"
	action="/mobility.do?method=updatePersonalPortfolioInfo">
	<fr:layout name="tabular">
		<fr:property name="classes" value="form listInsideClear" />
		<fr:property name="columnClasses" value="width100px,,tderror" />
	</fr:layout>
</fr:edit>
