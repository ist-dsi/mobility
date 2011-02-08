<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOfferProcessIdentification"/>
	<span class="processNumber">(<bean:write name="process" property="processIdentification"/>)</span>	 
</h2>

<bean:define id="OID" name="process" property="OID"/>
<wf:isActive processName="process" activityName="SubmitCandidacyActivity" scope="request">
	<p class="mbottom15">				
		<html:link action="<%= "/mobility.do?method=submitCandidacy&OID="+OID%>">
			<bean:message key="activity.SubmitCandidacyActivity" bundle="MOBILITY_RESOURCES"/>
		</html:link>
	</p>
</wf:isActive>
<wf:isActive processName="process" activityName="UnSubmitCandidacyActivity" scope="request">
	<p class="mbottom15">
		<html:link action="<%= "/mobility.do?method=unSubmitCandidacy&OID="+OID%>">
			<bean:message key="activity.UnSubmitCandidacyActivity" bundle="MOBILITY_RESOURCES"/>
		</html:link>
	</p>
</wf:isActive>

<jsp:include page="../module/mobility/domain/JobOfferProcess/jobOfferBody.jsp"/>



