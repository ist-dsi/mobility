<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.jobOffers" /> </h2>

<html:link action="/mobility.do?method=prepareToCreateJobOffer">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.jobOffers.create" />
</html:link>

<logic:present name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			<fr:property name="classes" value="plist mtop05 width100pc"/>
			<fr:property name="link(view)" value="/mobility.do?method=viewJobOfferProcess" />
			<fr:property name="key(view)" value="label.mobility.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="MOBILITY_RESOURCES" />
		</fr:layout>
		<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.mobility.processIdentification" />
			<fr:slot name="jobOffer.beginDate" key="label.mobility.beginDate" />
			<fr:slot name="jobOffer.endDate" key="label.mobility.endDate" />
			<fr:slot name="jobOffer.title" key="label.mobility.jobOffer.title" />
		</fr:schema>
	</fr:view>
</logic:present>
