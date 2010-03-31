<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>
<fr:view name="jobOffer">
	<fr:schema type="module.mobility.domain.JobOffer" bundle="MOBILITY_RESOURCES">
		<fr:slot name="jobOfferProcess.processIdentification" key="label.mobility.processIdentification"/>
		<fr:slot name="year" key="label.mobility.year"/>
		<fr:slot name="beginDate" key="label.mobility.beginDate"/>
		<fr:slot name="endDate" key="label.mobility.endDate"/>
		<fr:slot name="title" key="label.mobility.jobOffer.title"/>
		<fr:layout name="tabular">
			<fr:property name="classes" value="plist mtop05 width100pc"/>
		</fr:layout>
	</fr:schema>
</fr:view>
