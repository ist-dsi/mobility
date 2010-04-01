<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.employeeOffers"/>
</h2>

<table class="tstyle3 mvert1 width100pc tdmiddle punits">
	<tr>	
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerProcessIdentification"/>
		</th>
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.beginDate"/>
		</th>
		<th>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.endDate"/>
		</th>
	</tr>
	<logic:empty name="workerOffers">
		<tr>
			<td class="aleft" colspan="3">
				<i><strong>
					<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.none"/>
				</strong></i>
			</td>
		</td>
	</logic:empty>
	<logic:iterate id="workerOffer" name="workerOffers">
		<tr>
			<td>
				<fr:view name="workerOffer" property="workerOfferProcess.processIdentification"/>
			</td>
			<td>
				<fr:view name="workerOffer" property="beginDate"/>
			</td>
			<td>
				<fr:view name="workerOffer" property="endDate"/>
			</td>
		</tr>
	</logic:iterate>
</table>
