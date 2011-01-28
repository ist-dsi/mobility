<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="title.module.mobility.jobOffers.create"/></h2>

<fr:edit id="jobOfferBean" name="jobOfferBean" action="/mobility.do?method=createJobOffer">
	<fr:schema type="module.mobility.domain.util.JobOfferBean" bundle="MOBILITY_RESOURCES">
		<fr:slot name="vacanciesNumber" key="label.mobility.jobOffer.vacanciesNumber" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="size" value="2"/>
		</fr:slot>	
		<fr:slot name="workplace" key="label.mobility.jobOffer.workplace" layout="autoComplete" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredAutoCompleteSelectionValidator">
        	<fr:property name="labelField" value="presentationName"/>
			<fr:property name="format" value="${presentationName}"/>
			<fr:property name="minChars" value="3"/>
			<fr:property name="args" value="provider=module.mobility.presentationTier.renderers.dataProvider.UnitCostCenterAutoCompleteProvider" />
			<fr:property name="size" value="60"/>
		</fr:slot>
		<fr:slot name="careerRequirements" layout="option-select" key="label.mobility.jobOffer.careerRequirements" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="providerClass" value="module.mobility.presentationTier.renderers.dataProvider.CareerTypeProvider"/>
		</fr:slot>	
		<fr:slot name="skillRequirements" key="label.mobility.jobOffer.skillRequirements" layout="longText" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>	
		<fr:slot name="jobProfile" key="label.mobility.jobOffer.jobProfile" layout="longText" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="knowledgeRequirements" key="label.mobility.jobOffer.knowledgeRequirements" layout="longText" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="qualificationRequirements" key="label.mobility.jobOffer.qualificationRequirements" layout="longText">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="formationRequirements" key="label.mobility.jobOffer.formationRequirements" layout="longText">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="professionalExperienceRequirements" key="label.mobility.jobOffer.professionalExperienceRequirements" layout="longText">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="requiredDocumentsForCandidacy" key="label.mobility.jobOffer.requiredDocumentsForCandidacy" layout="longText" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
	</fr:schema>
</fr:edit>