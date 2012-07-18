<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

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


