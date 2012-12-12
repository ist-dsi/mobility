<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>

<jsp:include page="../module/mobility/domain/JobOfferProcess/header.jsp"/>

<h3>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.candidacies.view"/>
</h3>

<logic:empty name="jobOffer" property="jobOfferCandidacy">
	<bean:message bundle="MOBILITY_RESOURCES" key="message.mobility.empty.candidateSet"/>
</logic:empty>

<fr:view name="jobOffer" property="jobOfferCandidacy" schema="show.jobOfferCandidacy">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle3 width100pc mtop15"/>
	</fr:layout>
</fr:view>

<p class="mtop1 mbottom0">
	<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="process" paramProperty="externalId">
		<bean:message bundle="MOBILITY_RESOURCES" key="label.backToProcess"/>
	</html:link>
</p>
