<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>


<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="pt.ist.bennu.core.domain.MyOrg"%>
<%@page import="pt.utl.ist.fenix.tools.util.i18n.Language"%>

<bean:define id="workerOffer" name="process" property="workerOffer" type="module.mobility.domain.WorkerOffer"/>
<bean:define id="personalPortfolioInfo" name="workerOffer" property="personalPortfolioInfo"/>
<bean:define id="personalPortfolio" name="personalPortfolioInfo" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>

<!--
<h3 class="separator mtop15">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.employeeOffer"/>
</h3>
-->

<h4 class="mtop1">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.information"/>
</h4>


<div class="infobox mvert1">
	<table>
		<tr>
			<td valign="middle" style="padding: 10px">
				<logic:equal name="workerOffer" property="displayPhoto" value="true">
					<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="person" property="user.username"/></bean:define>
					<img src="<%= urlPhoto %>">
				</logic:equal>
				<logic:notEqual name="workerOffer" property="displayPhoto" value="true">
					<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt//images/photo_placer01_<%= Language.getUserLanguage().name() %>.gif</bean:define>
					<img src="<%= urlPhoto %>">
				</logic:notEqual>
			</td>
			<td valign="top" style="padding: 10px">
				<table width="100%">
					<tr>
						<td>
							<%
								final OrganizationalModel organizationalModel = MyOrg.getInstance().getOrganizationalModelsSet().size()>0 ?
									MyOrg.getInstance().getOrganizationalModelsSet().iterator().next() : null;
							%>

							<%
								if (organizationalModel == null) {
							%>
								<logic:equal name="workerOffer" property="displayName" value="true">
									<fr:view name="person" property="name"/> (<fr:view name="person" property="user.username"/>)
								</logic:equal>
							<%
								} else {
							%>
									<logic:equal name="workerOffer" property="displayName" value="true">
										<bean:define id="urlOrg2" type="java.lang.String">/organizationModel.do?method=viewModel&amp;viewName=default&amp;organizationalModelOid=<%= organizationalModel.getExternalId() %></bean:define>
										<html:link styleClass="secondaryLink" page="<%= urlOrg2 %>" paramId="partyOid" paramName="person" paramProperty="externalId">
											<fr:view name="person" property="name"/> (<fr:view name="person" property="user.username"/>)
										</html:link>
									</logic:equal>
							<%
								}
							%>
						</td>
					</tr>
					<tr style="border: none;">
						<td style="border: none;">
							<br/>
							<br/>
							<logic:equal name="workerOffer" property="displayDateOfBirth" value="true">
								Place more information here... (email, phone, address, ...).
							</logic:equal>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<h4 class="mtop1">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
</h4>

<div class="infobox mvert1">

	<logic:notPresent name="workerOffer" property="personalPortfolioInfo">
		<p><em><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.none"/></em></p>
	</logic:notPresent>
	
	<logic:present name="workerOffer" property="personalPortfolioInfo">
		<fr:view name="workerOffer" property="personalPortfolioInfo">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfo" bundle="MOBILITY_RESOURCES">
				<logic:equal name="workerOffer" property="displayCarrer" value="true">
					<fr:slot name="carrer" key="label.mobility.carrer"/>
				</logic:equal>
				<logic:equal name="workerOffer" property="displayCategory" value="true">
					<fr:slot name="category" key="label.mobility.category"/>
				</logic:equal>
				<fr:slot name="modificationDate" key="label.mobility.personalPortfolioInfo.modificationDate"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="thleft mvert05" />
			</fr:layout>
		</fr:view>
	</logic:present>
</div>



<logic:equal name="workerOffer" property="displayQualifications" value="true">

	<h4>
		<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/>
	</h4>

	<logic:present name="workerOffer" property="personalPortfolioInfo">
		<logic:notEmpty name="workerOffer" property="personalPortfolioInfo.personalPortfolioInfoQualification">
			<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
				<fr:schema type="module.mobility.domain.PersonalPortfolioInfoQualification" bundle="MOBILITY_RESOURCES">
					<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType"/>
					<fr:slot name="name" key="label.mobility.professional.information.qualification.name"/>
					<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution"/>
					<fr:slot name="date" key="label.mobility.professional.information.qualification.date"/>
					<fr:slot name="classification" layout="null-as-label" key="label.mobility.professional.information.qualification.classification"/>
				</fr:schema>
				<fr:layout name="tabular">
					<fr:property name="classes" value="tstyle3 mvert1 width100pc" />
				</fr:layout>
			</fr:view>
		</logic:notEmpty>
	</logic:present>
</logic:equal>
