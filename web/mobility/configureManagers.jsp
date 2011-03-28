<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.configuration"/></h2>

<fr:edit id="mobilitySystem" name="mobilitySystem" action="/mobility.do?method=configuration">
	<fr:schema type="module.mobility.domain.MobilitySystem" bundle="ORGANIZATION_RESOURCES">
		<fr:slot name="managersQueue" layout="menu-select" key="label.mobility.configuration.queue" bundle="ORGANIZATION_RESOURCES">
        	<fr:property name="providerClass" value="module.workflow.presentationTier.provider.WorkflowUserGroupQueueProvider" />
        	<fr:property name="format" value="${name}"/>
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


