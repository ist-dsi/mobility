<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="person" name="USER_SESSION_ATTRIBUTE"	property="user.person"/>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.frontPage"/>
</h2>

<fr:edit id="offerSearch" name="offerSearch">
	<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
		<fr:slot name="processNumber" key="label.mobility.processIdentification" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="size" value="10"/>
		</fr:slot>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="form" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:edit>

<logic:notEmpty name="offerSearch" property="processNumber">
	<bean:message bundle="MOBILITY_RESOURCES" key="message.mobility.invalid.processIdentification"/>
</logic:notEmpty>


<div style="float: left; width: 100%">
	<table style="width: 100%; margin: 1em 0;">
		<tr>
			<td style="border: 1px dotted #aaa; padding: 10px 15px; width: 48%; vertical-align: top;">
				<p class="mtop0 mbottom05">
					<b>
						<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.myJobOffers"/>
					</b>
				</p>
				<bean:define id="offerList" name="offerSearch" property="jobOfferSet" toScope="request"/>
				<jsp:include page="offerList.jsp"/>
			<td style="border: none; width: 2%; padding: 0;"></td>
			<td style="border: 1px dotted #aaa; padding: 10px 15px; width: 48%; vertical-align: top;">
				<p class="mtop0 mbottom05">
					<b>
						<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.myWorkerOffers"/>
					</b>
				</p>
				<bean:define id="offerList" name="offerSearch" property="workerOfferSet" toScope="request"/>
				<jsp:include page="offerList.jsp"/>
			</td>
		</tr>
	</table>

	<%  module.mobility.domain.MobilitySystem mobilitySystem = module.mobility.domain.MobilitySystem.getInstance();
	if (mobilitySystem.isManagementMember()) { %>
	<table style="width: 100%; margin: 1em 0;">
			<tr>
				<td style="border: 1px dotted #aaa; padding: 10px 15px; width: 48%; vertical-align: top;">
					<p class="mtop0 mbottom05">
						<b>
							<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.pendingApproval.jobOffers"/>
						</b>
					</p>
					<bean:define id="offerList" name="offerSearch" property="pendingApprovalJobOfferSet" toScope="request"/>
					<jsp:include page="offerList.jsp"/>
				<td style="border: none; width: 2%; padding: 0;"></td>
				<td style="border: 1px dotted #aaa; padding: 10px 15px; width: 48%; vertical-align: top;">
					<p class="mtop0 mbottom05">
						<b>
							<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.pendingApproval.workerOffers"/>
						</b>
					</p>
					<bean:define id="offerList" name="offerSearch" property="pendingApprovalWorkerJobOfferSet" toScope="request"/>
					<jsp:include page="offerList.jsp"/>
				</td>
			</tr>
		</table>
	<%}%>
</div>


