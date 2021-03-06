<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>

<%@page import="module.mobility.domain.util.MobilityProcessStageState"%>

<bean:define id="mobilityProcessStageView" name="process" property="mobilityProcessStageView" type="module.mobility.domain.util.MobilityWorkerOfferProcessStageView"/>

<logic:equal name="mobilityProcessStageView" property="canceled" value="true">
	<div class="highlightBox"><p class="mvert025"><bean:message key="message.mobility.process.canceled" bundle="MOBILITY_RESOURCES"/></p></div>
</logic:equal>

<table style="text-align: center; width: 100%;">
	<tr>
		<td align="center">
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<logic:iterate id="entry" name="mobilityProcessStageView" property="mobilityProcessStageStates">
						<bean:define id="mobilityProcessStage" name="entry" property="key" type="module.mobility.domain.util.MobilityProcessStage"/>
						<bean:define id="mobilityProcessStageState" name="entry" property="value" type="module.mobility.domain.util.MobilityProcessStageState"/>

						<% final String colorStyle = mobilityProcessStageState == MobilityProcessStageState.COMPLETED ? "background-color: #CEF6CE; border-color: #04B404; "
								: (mobilityProcessStageState == MobilityProcessStageState.UNDER_WAY ? "background-color: #F6E3CE; border-color: #B45F04;" : ""); %>
						<td style="<%= colorStyle + "border-style: solid; border-width: thin; width: 120px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;" %>" align="center"
								title="<%= mobilityProcessStage.getLocalizedDescription() %>">
							<%= mobilityProcessStage.getLocalizedName() %>
							
						</td>
					</logic:iterate>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table style="border-collapse: separate; border-spacing: 10px; border-style: dotted; border-width: thin; background-color: #FEFEFE;">
				<tr>
					<td align="center">
						<strong>
							<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.stage.view.legend"/>
						</strong>
					</td>
					<td style="border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.NOT_YET_UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #F6E3CE; border-color: #B45F04; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #CEF6CE; border-color: #04B404; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.COMPLETED.getLocalizedName() %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
