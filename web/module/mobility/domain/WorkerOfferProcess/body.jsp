<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>

<bean:define id="workerOffer" name="process" property="workerOffer"/>
<bean:define id="personalPortfolio" name="workerOffer" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>

<div class="infobox mtop1 mbottom1">
	<fr:view name="workerOffer">
		<fr:schema type="module.mobility.domain.WorkerOffer" bundle="MOBILITY_RESOURCES">
			<fr:slot name="workerOfferProcess.processIdentification" key="label.mobility.workerProcessIdentification" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:slot name="beginDate" key="label.mobility.beginDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:slot name="endDate" key="label.mobility.endDate" layout="picker"/>
			<fr:slot name="displayName" key="label.mobility.workerJobOffer.displayName"/>
			<fr:slot name="displayDateOfBirth" key="label.mobility.workerJobOffer.displayDateOfBirth"/>
			<fr:slot name="displayCarrer" key="label.mobility.workerJobOffer.displayCarrer"/>
			<fr:slot name="displayCategory" key="label.mobility.workerJobOffer.displayCategory"/>
			<fr:slot name="displaySalary" key="label.mobility.workerJobOffer.displaySalary"/>
		</fr:schema>
	</fr:view>
</div>
