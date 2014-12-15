<%@page import="module.mobility.domain.MobilitySystem"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.employeeOffers"/>
</h2>

<% if (MobilitySystem.getInstance().isManagementMember()) {%>
<fr:form action="/mobility.do?method=employeeOffers">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
			<fr:slot name="processNumber" key="label.mobility.processIdentification">
				<fr:property name="size" value="10"/>
			</fr:slot>
			<%-- 
			<fr:slot name="offerSearchOwner" key="label.module.mobility.employeeOffers" required="true">
				<fr:property name="defaultOptionHidden" value="true"/>
				<fr:property name="excludedValues" value="WITH_MY_CANDIDACY"/>
			</fr:slot>
			--%>
			<fr:slot name="mobilityProcessStage" key="label.mobility.state" required="true">
				<fr:property name="excludedValues" value="SELECTION,EVALUATION,JURY_DEFINITION,CANDIDACY_EVALUATION,CONCLUDED_CANDIDACY,ARCHIVED"/>
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="MOBILITY_RESOURCES" key="label.mobility.submit"/>
	</html:submit>
</fr:form>
<br/>
<%} %>
<logic:empty name="processes">
	<p>
		<em><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.none"/></em>
	</p>
</logic:empty>
<logic:notEmpty name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />

			<fr:property name="link(manage)" value="/mobility.do?method=viewWorkerOfferProcessToManage" />
			<fr:property name="key(manage)" value="label.mobility.manage" />
			<fr:property name="param(manage)" value="OID" />
			<fr:property name="bundle(manage)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIf(manage)" value="canManageProcess" />
		</fr:layout>
		<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
			<fr:slot name="processIdentification" key="label.mobility.jobOfferProcessIdentification" />
			<fr:slot name="workerOffer.approvalDate" key="label.mobility.approvalDate" layout="null-as-label"/>
			<fr:slot name="workerOffer.personalPortfolioInfo.carrer" key="label.mobility.carrer" />
			<fr:slot name="workerOffer.personalPortfolioInfo.category" key="label.mobility.category" />
		</fr:schema>
	</fr:view>
</logic:notEmpty>
