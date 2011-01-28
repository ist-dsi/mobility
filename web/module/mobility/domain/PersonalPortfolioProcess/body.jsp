<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>

<bean:define id="personalPortfolio" name="process" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>


<h3 class="separator">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.information"/>
</h3>

<div class="infobox mvert1">
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
							<fr:view name="personalPortfolio" >
								<fr:schema type="module.mobility.domain.PersonalPortfolio" bundle="MOBILITY_RESOURCES">
									<fr:slot name="email" key="label.mobility.email"/>
									<fr:slot name="workingPlaces" key="label.mobility.workingPlace" layout="tabular-list">
										<fr:property name="subLayout" value="values-with-br" />
										<fr:property name="subSchema" value="module.organization.domain.Party.view.presentationName" />
									</fr:slot>
								</fr:schema>
								<fr:layout>
									<fr:property name="classes" value="mvert05 thleft"/>
								</fr:layout>
							</fr:view>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>




<h3 class="separator mtop2">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
</h3>

<div class="infobox mvert1">
	<logic:notPresent name="personalPortfolio" property="lastPersonalPortfolioInfo">
		<i>
			<strong>
				<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.none"/>
			</strong>
		</i>
	</logic:notPresent>
	<logic:present name="personalPortfolio" property="lastPersonalPortfolioInfo">
		<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfo" bundle="MOBILITY_RESOURCES">
				<fr:slot name="carrer" key="label.mobility.carrer"/>
				<fr:slot name="category" key="label.mobility.category"/>
				<fr:slot name="modificationDate" key="label.mobility.personalPortfolioInfo.modificationDate"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="mvert05 thleft"/>
				<%--
				<fr:property name="classes" value="tstyle3 mvert05 thleft tdleft width100pc" />
				<fr:property name="columnClasses" value="width100px,,," />
				--%>
			</fr:layout>
		</fr:view>
	</logic:present>
</div>

<p class="mtop15 mbottom0">
	<strong><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/></strong>
</p>

<logic:present name="personalPortfolio" property="lastPersonalPortfolioInfo">
	<logic:notEmpty name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
		<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfoQualification" bundle="MOBILITY_RESOURCES">
				<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="name" key="label.mobility.professional.information.qualification.name" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="date" key="label.mobility.professional.information.qualification.date" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:slot name="classification" layout="null-as-label" key="label.mobility.professional.information.qualification.classification"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle3 mvert1 width100pc" />
			</fr:layout>
		</fr:view>
	</logic:notEmpty>
</logic:present>




<h3 class="separator mtop15">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.worker.offers"/>
</h3>

<table class="tstyle3 mvert1 width100pc tdmiddle punits">
	<tr>	
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerProcessIdentification"/>
		</th>
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.active"/>
		</th>
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.beginDate"/>
		</th>
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.endDate"/>
		</th>
		<th>
		</th>
	</tr>
	<logic:empty name="personalPortfolio" property="workerOffer">
		<tr>
			<td class="aleft" colspan="3">
				<i><strong>
					<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.none"/>
				</strong></i>
			</td>
		</td>
	</logic:empty>
	<logic:iterate id="workerOffer" name="personalPortfolio" property="workerOffer">
		<tr>
			<td>
				<fr:view name="workerOffer" property="workerOfferProcess.processIdentification"/>
			</td>
			<td>
				<fr:view name="workerOffer" property="activeOrPendingApproval"/>
			</td>
			<td>
				<fr:view name="workerOffer" property="beginDate"/>
			</td>
			<td>
				<logic:present name="workerOffer" property="endDate">
					<fr:view name="workerOffer" property="endDate"/>
				</logic:present>
			</td>
			<td>
				<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="workerOffer" paramProperty="workerOfferProcess.externalId">
					<bean:message bundle="MOBILITY_RESOURCES" key="label.view"/>
				</html:link>
			</td>
		</tr>
	</logic:iterate>
</table>
