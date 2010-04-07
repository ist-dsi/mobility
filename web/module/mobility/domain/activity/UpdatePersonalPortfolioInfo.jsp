<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>

<bean:define id="process" name="information" property="process"/>
<bean:define id="processId" name="process" property="externalId" type="java.lang.String"/>
<bean:define id="name" name="information" property="activityName"/>

<div class="dinline forminline">	

	<fr:form id="activityForm" action='<%= "/mobility.do?processId=" + processId + "&activity=" + name %>'>
		<html:hidden property="method" value="saveProfessionalInformation"/>
		<html:hidden property="qualificationIndex" value="-1"/>

		<fr:edit id="activityBean" name="information" visible="false"/>

		<h4>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
		</h4>

		<fr:edit id="UpdatePersonalPortfolioInfo" name="information">
			<fr:schema type="module.mobility.domain.activity.PersonalPortfolioInfoInformation" bundle="MOBILITY_RESOURCES">
				<fr:slot name="carrer" key="label.mobility.carrer" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="category" key="label.mobility.category" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="salary" key="label.mobility.salary" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="form listInsideClear" />
				<fr:property name="columnClasses" value="width100px,,tderror" />
			</fr:layout>
		</fr:edit>

		<h4>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/>
		</h4>

		<logic:empty name="information" property="qualificationHolders">
			<p>
				<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.none"/>
			</p>
		</logic:empty>
		<logic:notEmpty name="information" property="qualificationHolders">
			<logic:iterate id="qualificationHolder" indexId="i" name="information" property="qualificationHolders">
				<a href="#" onclick="<%= "javascript:getElementById('activityForm').qualificationIndex.value='" + i + "';getElementById('activityForm').method.value='removeQualification';getElementById('activityForm').submit();return" %>">
					<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.remove"/>
				</a>
				<fr:edit id="<%= "qualificationHolder" + i %>" name="qualificationHolder">
					<fr:schema type="module.mobility.domain.activity.PersonalPortfolioInfoInformation$QualificationHolder" bundle="MOBILITY_RESOURCES">
						<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="name" key="label.mobility.professional.information.qualification.name" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="date" key="label.mobility.professional.information.qualification.date" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="classification" key="label.mobility.professional.information.qualification.classification"/>
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value="form listInsideClear" />
						<fr:property name="columnClasses" value="width100px,,tderror" />
					</fr:layout>
				</fr:edit>
			</logic:iterate>
		</logic:notEmpty>

		<a href="#" onclick="<%= "javascript:getElementById('activityForm').method.value='addNewQualification';getElementById('activityForm').submit();return" %>">
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.add"/>
		</a>

		<br/>
		<br/>

		<html:submit styleClass="inputbutton" onclick="getElementById('activityForm').method.value='saveProfessionalInformation'"><bean:message key="label.save" bundle="MOBILITY_RESOURCES"/></html:submit>
	</fr:form>

	<fr:form action='<%= "/workflowProcessManagement.do?method=viewProcess&processId=" + processId %>'>
		<html:submit styleClass="inputbutton"><bean:message key="renderers.form.cancel.name" bundle="RENDERER_RESOURCES"/> </html:submit>
	</fr:form>

</div>