<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>



<h3 class="separator">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.addJuryMember"/>
</h3>


<fr:form action="/mobility.do?method=addPersonToJobOfferJury">
	<fr:edit id="addJury" name="information">
		<fr:schema type="module.mobility.domain.activity.JobOfferJuryInformation" bundle="MOBILITY_RESOURCES">
			<fr:slot name="personToAddToJury" layout="autoComplete" key="label.mobility.name"
						validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredAutoCompleteSelectionValidator">
	        	<fr:property name="labelField" value="name"/>
				<fr:property name="format" value="${partyName} (${user.username})"/>
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
<br/>
	<h3 class="separator">
		<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOffer.selectJuryPresident"/>
	</h3>

	
	
	<fr:view name="information" property="juryMembers" schema="show.juryMember">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />
			<fr:property name="link(remove)" value="/mobility.do?method=removePersonToJobOfferJury" />
			<fr:property name="param(remove)" value="OID" />
			<fr:property name="key(remove)" value="label.mobility.remove" />
			<fr:property name="bundle(remove)" value="MOBILITY_RESOURCES" />
			
			<fr:property name="link(setJuryPresident)" value="/mobility.do?method=setJuryPresident" />
			<fr:property name="param(setJuryPresident)" value="OID" />
			<fr:property name="key(setJuryPresident)" value="label.mobility.setJuryPresident" />
			<fr:property name="bundle(setJuryPresident)" value="MOBILITY_RESOURCES" />
			<fr:property name="vidibleIfNot(setJuryPresident)" value="hasPresidentDefined" />

			<fr:property name="link(removeJuryPresident)" value="/mobility.do?method=removeJuryPresident" />
			<fr:property name="param(removeJuryPresident)" value="OID" />
			<fr:property name="key(removeJuryPresident)" value="label.mobility.removeJuryPresident" />
			<fr:property name="bundle(removeJuryPresident)" value="MOBILITY_RESOURCES" />
			<fr:property name="vidibleIf(removeJuryPresident)" value="juryPresident" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>
<br/>
<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="information" paramProperty="process.externalId">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.backToProcess"/>
</html:link>