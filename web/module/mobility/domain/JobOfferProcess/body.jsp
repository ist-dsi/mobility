<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<jsp:include page="processStageView.jsp"/>

<bean:define id="jobOffer" name="process" property="jobOffer"/>
<logic:equal name="jobOffer" property="approved" value="true">
	<logic:equal name="process" property="canManageJobOfferCandidacies" value="true">
		<bean:define id="OID" name="process" property="OID"/>
		<p>
		<html:link action="<%= "/mobility.do?method=viewJobOfferCandidacies&OID="+OID%>">
			<bean:message key="label.mobility.jobOffer.candidacies.view" bundle="MOBILITY_RESOURCES"/>
		</html:link>
		</p>
	</logic:equal>
</logic:equal>

<jsp:include page="jobOfferBody.jsp"/>