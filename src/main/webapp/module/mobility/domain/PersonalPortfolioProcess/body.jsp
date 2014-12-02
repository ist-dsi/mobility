<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="org.fenixedu.bennu.core.domain.Bennu"%>

<bean:define id="personalPortfolio" name="process" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>



<h3 class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.information"/>
</h3>

<div class="infobox mvert05">
	<table>
		<tr>
			<td valign="middle" style="padding: 10px">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="person" property="user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td valign="top" style="padding: 10px">
				<table class="width100pc">
					<tr>
						<td>
							<%
								final OrganizationalModel organizationalModel = MyOrg.getInstance().getOrganizationalModelsSet().size()>0 ?
										MyOrg.getInstance().getOrganizationalModelsSet().iterator().next() : null;
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
							<fr:view name="personalPortfolio" >
								<fr:schema type="module.mobility.domain.PersonalPortfolio" bundle="MOBILITY_RESOURCES">
									<fr:slot name="email" key="label.mobility.email"/>
									<fr:slot name="workingPlaces" key="label.mobility.workingPlace" layout="tabular-list">
										<fr:property name="subLayout" value="values-with-br" />
										<fr:property name="subSchema" value="module.organization.domain.Party.view.presentationName" />
									</fr:slot>
								</fr:schema>
								<fr:layout>
									<fr:property name="classes" value="structural-vertical mtop1 thleft"/>
								</fr:layout>
							</fr:view>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>



<h3 class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information"/>
</h3>

<div class="infobox mvert05">
	<logic:notPresent name="personalPortfolio" property="lastPersonalPortfolioInfo">
		<p>
			<em><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.none"/></em>
		</p>
	</logic:notPresent>
	<logic:present name="personalPortfolio" property="lastPersonalPortfolioInfo">
		<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfo" bundle="MOBILITY_RESOURCES">
				<fr:slot name="carrer" key="label.mobility.carrer"/>
				<fr:slot name="category" key="label.mobility.category"/>
				<fr:slot name="modificationDate" key="label.mobility.personalPortfolioInfo.modificationDate"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="structural-vertical mvert05 thleft"/>
			</fr:layout>
		</fr:view>
	</logic:present>
</div>

<h3 class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.professional.information.qualifications"/>
</h3>

<logic:present name="personalPortfolio" property="lastPersonalPortfolioInfo">
	<logic:notEmpty name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
		<fr:view name="personalPortfolio" property="lastPersonalPortfolioInfo.personalPortfolioInfoQualification">
			<fr:schema type="module.mobility.domain.PersonalPortfolioInfoQualification" bundle="MOBILITY_RESOURCES">
				<fr:slot name="qualificationType" key="label.mobility.professional.information.qualification.qualificationType"/>
				<fr:slot name="name" key="label.mobility.professional.information.qualification.name"/>
				<fr:slot name="institution" key="label.mobility.professional.information.qualification.institution"/>
				<fr:slot name="date" key="label.mobility.professional.information.qualification.date"/>
				<fr:slot name="classification" layout="null-as-label" key="label.mobility.professional.information.qualification.classification"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle3 mvert05 width100pc" />
			</fr:layout>
		</fr:view>
	</logic:notEmpty>
</logic:present>




<h3 class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.personal.worker.offers"/>
</h3>



<logic:empty name="personalPortfolio" property="workerOffer">
	<p>
		<em><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.none"/></em>
	</p>
</logic:empty>

<logic:notEmpty name="personalPortfolio" property="workerOffer">
	<table class="tstyle3 mvert05 width100pc">
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
</logic:notEmpty>


<h3 class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.myJobOffersCandidacies"/>
</h3>

<logic:empty name="personalPortfolio" property="jobOffersWithCandidacies">
	<p>
		<em><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.myJobOffersCandidacies.none"/></em>
	</p>
</logic:empty>

<logic:notEmpty name="personalPortfolio" property="jobOffersWithCandidacies">
	<fr:view name="personalPortfolio" property="jobOffersWithCandidacies">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />
				<fr:property name="link(view)" value="/mobility.do?method=viewJobOfferProcess" />
				<fr:property name="key(view)" value="label.mobility.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="MOBILITY_RESOURCES" />
			</fr:layout>
			<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
				<fr:slot name="processIdentification" key="label.mobility.jobOfferProcessIdentification" />
				<fr:slot name="jobOffer.workplacePath" key="label.mobility.jobOffer.workplace" />
				<fr:slot name="jobOffer.careerRequirements" key="label.mobility.jobOffer.careerRequirements" />
				<fr:slot name="jobOffer.publicationEndDate" key="label.mobility.candicaciesDeadline" />
			</fr:schema>
		</fr:view>
</logic:notEmpty>
