<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>

<bean:define id="process" name="information" property="process"/>
<bean:define id="processId" name="process" property="externalId" type="java.lang.String"/>
<bean:define id="name" name="information" property="activityName"/>

<div class="dinline forminline">	

	<fr:form id="activityForm" action='<%= "/mobility.do?processId=" + processId + "&activity=" + name %>'>
		<html:hidden property="method" value="saveProfessionalInformation"/>
		<html:hidden property="qualificationIndex" value="-1"/>

		<fr:edit id="activityBean" name="information" visible="false"/>


		<h3 class="separator mtop15">
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
		</h3>

		<fr:edit id="UpdatePersonalPortfolioInfo" name="information">
			<fr:schema type="module.mobility.domain.activity.PersonalPortfolioInfoInformation" bundle="MOBILITY_RESOURCES">
				<fr:slot name="carrer" key="label.mobility.carrer" required="true">
					<fr:property name="providerClass" value="module.mobility.presentationTier.renderers.dataProvider.CareerTypeProvider"/>
				</fr:slot>
				<fr:slot name="category" key="label.mobility.category" required="true">
					<fr:property name="size" value="60" />
				</fr:slot>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="form listInsideClear" />
				<fr:property name="columnClasses" value="width100px,,tderror" />
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="false" />

			</fr:layout>
		</fr:edit>



		<h3 class="separator mtop2">
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/>
		</h3>

		<logic:empty name="information" property="qualificationHolders">
			<p class="mvert15">
				<em><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.none"/></em>
			</p>
		</logic:empty>
		<logic:notEmpty name="information" property="qualificationHolders">
			<logic:iterate id="qualificationHolder" indexId="i" name="information" property="qualificationHolders">

				<fr:edit id="<%= "qualificationHolder" + i %>" name="qualificationHolder">
					<fr:schema type="module.mobility.domain.activity.PersonalPortfolioInfoInformation$QualificationHolder" bundle="MOBILITY_RESOURCES">
						<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType" required="true">
							<fr:property name="size" value="60" />
						</fr:slot>
						<fr:slot name="name" key="label.mobility.professional.information.qualification.name" required="true">
							<fr:property name="size" value="60" />
						</fr:slot>
						<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution" required="true">
							<fr:property name="size" value="60" />
						</fr:slot>
						<fr:slot name="date" key="label.mobility.professional.information.qualification.date" required="true">
							<fr:property name="size" value="20" />
						</fr:slot>
						<fr:slot name="classification" key="label.mobility.professional.information.qualification.classification">
							<fr:property name="size" value="20" />
						</fr:slot>
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value="form mbottom05" />
						<fr:property name="columnClasses" value="width100px,,tderror" />
						<fr:property name="requiredMarkShown" value="true" />
						<fr:property name="requiredMessageShown" value="false" />

					</fr:layout>
				</fr:edit>
				<p class="mtop0 mbottom15">
					<a href="#" onclick="<%= "javascript:getElementById('activityForm').qualificationIndex.value='" + i + "';getElementById('activityForm').method.value='removeQualification';getElementById('activityForm').submit();return" %>">
						<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.remove"/>
					</a>
				</p>

			</logic:iterate>
		</logic:notEmpty>

		<p class="mbottom2">
			<a href="#" onclick="<%= "javascript:getElementById('activityForm').method.value='addNewQualification';getElementById('activityForm').submit();return" %>">
				+ <bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications.add"/>
			</a>
		</p>


		<html:submit styleClass="inputbutton" onclick="getElementById('activityForm').method.value='saveProfessionalInformation'"><bean:message key="label.save" bundle="MOBILITY_RESOURCES"/></html:submit>
	</fr:form>

	<fr:form action='<%= "/workflowProcessManagement.do?method=viewProcess&processId=" + processId %>'>
		<html:submit styleClass="inputbutton"><bean:message key="renderers.form.cancel.name" bundle="RENDERER_RESOURCES"/> </html:submit>
	</fr:form>

</div>
