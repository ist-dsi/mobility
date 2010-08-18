<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>


<bean:define id="workerOffer" name="process" property="workerOffer" type="module.mobility.domain.WorkerOffer"/>



<jsp:include page="../processStageView.jsp"/>

<h3 class="separator">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.options"/>
</h3>

<div class="infobox mtop1 mbottom1">
	<fr:view name="workerOffer">
		<fr:schema type="module.mobility.domain.WorkerOffer" bundle="MOBILITY_RESOURCES">
			<fr:slot name="beginDate" key="label.mobility.beginDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:slot name="endDate" key="label.mobility.endDate" layout="picker"/>
			<fr:slot name="displayPhoto" key="label.mobility.workerJobOffer.displayPhoto"/>
			<fr:slot name="displayName" key="label.mobility.workerJobOffer.displayName"/>
			<fr:slot name="displayDateOfBirth" key="label.mobility.workerJobOffer.displayDateOfBirth"/>
			<fr:slot name="displayCarrer" key="label.mobility.workerJobOffer.displayCarrer"/>
			<fr:slot name="displayCategory" key="label.mobility.workerJobOffer.displayCategory"/>
			<fr:slot name="displaySalary" key="label.mobility.workerJobOffer.displaySalary"/>
			<fr:slot name="displayQualifications" key="label.mobility.workerJobOffer.displayQualifications"/>
			<fr:slot name="displayCurriculum" key="label.mobility.workerJobOffer.displayCurriculum"/>
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="mvert05 thleft"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>

<jsp:include page="workerOfferBody.jsp"/>
