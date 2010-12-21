<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>

<jsp:include page="../module/mobility/domain/JobOfferProcess/header.jsp"/>

<logic:empty name="jobOffer" property="candidatePortfolioInfoSet">
	<bean:message bundle="MOBILITY_RESOURCES" key="message.mobility.empty.candidateSet"/>
</logic:empty>

<fr:view name="jobOffer" property="candidatePortfolioInfoSet">
	<fr:layout name="tabular">
		<fr:property name="classes" value="plist mtop05 width100pc"/>
	</fr:layout>
	<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
		<fr:slot name="personalPortfolio.person.user.username" key="label.username" bundle="ORGANIZATION_RESOURCES"/>
		<fr:slot name="personalPortfolio.person.name" key="label.name" bundle="ORGANIZATION_RESOURCES"/>
		<fr:slot name="carrer" key="label.mobility.carrer" />
		<fr:slot name="category" key="label.mobility.category" />
	</fr:schema>
</fr:view>
