<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

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