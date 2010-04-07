<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>

<bean:define id="workerOffer" name="process" property="workerOffer"/>
<bean:define id="personalPortfolioInfo" name="workerOffer" property="personalPortfolioInfo"/>
<bean:define id="personalPortfolio" name="personalPortfolioInfo" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>

<h3>
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
		</fr:schema>
	</fr:view>
</div>

<h3>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.employeeOffer"/>
</h3>

<h4>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.information"/>
</h4>

<div class="infobox mtop1 mbottom1">
	<table>
		<tr>
			<td valign="middle" style="padding: 10px">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="person" property="user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td valign="top" style="padding: 10px">
				<table width="100%">
					<tr>
						<td>
							<%
								final OrganizationalModel organizationalModel = MyOrg.getInstance().hasAnyOrganizationalModels() ?
										MyOrg.getInstance().getOrganizationalModelsIterator().next() : null;
							%>

							<%
								if (organizationalModel == null) {
							%>
									<fr:view name="person" property="name"/> (<fr:view name="person" property="user.username"/>)
							<%
								} else {
							%>
									<bean:define id="urlOrg2" type="java.lang.String">/organizationModel.do?method=viewModel&amp;viewName=default&amp;organizationalModelOid=<%= organizationalModel.getExternalId() %></bean:define>
									<html:link styleClass="secondaryLink" page="<%= urlOrg2 %>" paramId="partyOid" paramName="person" paramProperty="externalId">
										<fr:view name="person" property="name"/> (<fr:view name="person" property="user.username"/>)
									</html:link>
							<%
								}
							%>
						</td>
					</tr>
					<tr style="border: none;">
						<td style="border: none;">
							<br/>
							<br/>
							Place more information here... (email, phone, address, ...).
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<h4>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
</h4>

<div class="infobox mtop1 mbottom1">
	<logic:notPresent name="workerOffer" property="personalPortfolioInfo">
		<i><strong>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.none"/>
		</strong></i>
	</logic:notPresent>
	<logic:present name="workerOffer" property="personalPortfolioInfo">
		<fr:view name="workerOffer" property="personalPortfolioInfo">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfo" bundle="MOBILITY_RESOURCES">
				<fr:slot name="carrer" key="label.mobility.carrer"/>
				<fr:slot name="category" key="label.mobility.category"/>
				<fr:slot name="salary" key="label.mobility.salary"/>
				<fr:slot name="modificationDate" key="label.mobility.personalPortfolioInfo.modificationDate"/>
			</fr:schema>
		</fr:view>
	</logic:present>
</div>

<h5>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/>
</h5>
<logic:present name="workerOffer" property="personalPortfolioInfo">
	<logic:notEmpty name="workerOffer" property="personalPortfolioInfo.personalPortfolioInfoQualification">
		<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
					<fr:schema type="module.mobility.domain.PersonalPortfolioInfoQualification" bundle="MOBILITY_RESOURCES">
						<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="name" key="label.mobility.professional.information.qualification.name" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="date" key="label.mobility.professional.information.qualification.date" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						<fr:slot name="classification" layout="null-as-label" key="label.mobility.professional.information.qualification.classification"/>
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />
						<fr:property name="columnClasses" value="width100px,,tderror" />
					</fr:layout>
		</fr:view>
	</logic:notEmpty>
</logic:present>
