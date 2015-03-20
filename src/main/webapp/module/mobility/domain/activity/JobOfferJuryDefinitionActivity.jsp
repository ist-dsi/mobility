<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>


<p class="mtop15 mbottom0">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.addJuryMember"/>
</p>


<bean:define id="pid" name="information" property="process.externalId" />
<fr:form action="<%= "/mobility.do?method=addPersonToJobOfferJury&processId=" + pid %>">
	<fr:edit id="addJury" name="information">
		<fr:schema type="module.mobility.domain.activity.JobOfferJuryInformation" bundle="MOBILITY_RESOURCES">
			<fr:slot name="personToAddToJury" layout="autoComplete" key="label.mobility.name"
						validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredAutoCompleteSelectionValidator">
	        	<fr:property name="labelField" value="name"/>
				<fr:property name="format" value="\${partyName.content} (\${user.username})"/>
				<fr:property name="minChars" value="3"/>
				<fr:property name="args" value="provider=module.organization.presentationTier.renderers.providers.PersonAutoCompleteProvider" />
				<fr:property name="size" value="60"/>
			</fr:slot>
		</fr:schema>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="MOBILITY_RESOURCES" key="label.mobility.add"/>
	</html:submit>
</fr:form>


<logic:notEmpty name="information" property="juryMembers">

	<h3 class="mtop1">
		<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.selectJuryPresident"/>
	</h3>

	<div class="warning3 mtop1">
		<p class="mvert05"><bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.juryMembers.message"/></p>
	</div>
	
	<fr:view name="information" property="juryMembers" schema="show.juryMember">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc" />
			<fr:property name="link(remove)" value="/mobility.do?method=removePersonToJobOfferJury" />
			<fr:property name="param(remove)" value="OID" />
			<fr:property name="key(remove)" value="label.mobility.remove" />
			<fr:property name="bundle(remove)" value="MOBILITY_RESOURCES" />
			<fr:property name="order(remove)" value="1" />
			
			<fr:property name="link(setJuryPresident)" value="/mobility.do?method=setJuryPresident" />
			<fr:property name="param(setJuryPresident)" value="OID" />
			<fr:property name="key(setJuryPresident)" value="label.mobility.setJuryPresident" />
			<fr:property name="bundle(setJuryPresident)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIfNot(setJuryPresident)" value="juryPresident" />
			<fr:property name="order(setJuryPresident)" value="2" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>


<p>
	<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="information" paramProperty="process.externalId">
		<bean:message bundle="MOBILITY_RESOURCES" key="label.backToProcess"/>
	</html:link>
</p>
