<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.configuration"/></h2>

<fr:edit id="mobilitySystem" name="mobilitySystem" action="/mobility.do?method=configuration">
	<fr:schema type="module.mobility.domain.MobilitySystem" bundle="ORGANIZATION_RESOURCES">
		<fr:slot name="managementUnit" layout="autoComplete" key="label.party" bundle="ORGANIZATION_RESOURCES" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
        	<fr:property name="labelField" value="partyName.content"/>
			<fr:property name="format" value="${presentationName}"/>
			<fr:property name="minChars" value="3"/>
			<fr:property name="args" value="provider=module.organization.presentationTier.renderers.providers.UnitAutoCompleteProvider"/>
			<fr:property name="size" value="60"/>
		</fr:slot>
    	<fr:slot name="managementAccountabilityType" layout="menu-select" key="label.accountability.type" bundle="ORGANIZATION_RESOURCES">
        	<fr:property name="providerClass" value="module.organization.presentationTier.renderers.providers.AccountabilityTypesProvider"/>
        	<fr:property name="eachSchema" value="accountabilityType-name"/>
        	<fr:property name="eachLayout" value="values"/>
        	<fr:property name="classes" value="nobullet noindent"/>
        	<fr:property name="sortBy" value="name"/>
			<fr:property name="saveOptions" value="true"/>
    	</fr:slot>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="form listInsideClear" />
		<fr:property name="columnClasses" value="width100px,,tderror" />
	</fr:layout>
</fr:edit>


