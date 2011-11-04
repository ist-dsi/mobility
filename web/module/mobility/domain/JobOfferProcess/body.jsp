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
		<p class="mbottom15">
			<html:link action="<%= "/mobility.do?method=viewJobOfferCandidacies&OID="+OID%>">
				<bean:message key="label.mobility.jobOffer.candidacies.view" bundle="MOBILITY_RESOURCES"/>
			</html:link>
		</p>
	</logic:equal>
</logic:equal>

<logic:equal name="process" property="canManageProcess" value="true">
	<logic:notEqual name="jobOffer" property="juryMemberCount" value="0">
		<h3 class="mtop1">
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jury"/>
		</h3>
		<fr:view name="jobOffer" property="juryMember" schema="show.juryMember">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle3 mvert1 width100pc" />
			</fr:layout>
		</fr:view>
	</logic:notEqual>
</logic:equal>

<jsp:include page="jobOfferBody.jsp"/>