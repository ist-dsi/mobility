<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="title.module.mobility.jobOffers.create"/></h2>

<fr:edit id="jobOfferBean" name="jobOfferBean" action="/mobility.do?method=createJobOffer">
	<fr:schema type="module.mobility.domain.util.JobOfferBean" bundle="MOBILITY_RESOURCES">
		<fr:slot name="year" key="label.mobility.year" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
		<fr:slot name="beginDate" key="label.mobility.beginDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
		<fr:slot name="endDate" key="label.mobility.endDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
		<fr:slot name="title" key="label.mobility.jobOffer.title" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="size" value="50"/>
		</fr:slot>		
		<fr:slot name="jobProfile" key="label.mobility.jobOffer.jobProfile" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="knowledgeRequirements" key="label.mobility.jobOffer.knowledgeRequirements" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="skillRequirements" key="label.mobility.jobOffer.skillRequirements" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="careerRequirements" key="label.mobility.jobOffer.careerRequirements" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="size" value="50"/>
		</fr:slot>
		<fr:slot name="categoryRequirements" key="label.mobility.jobOffer.categoryRequirements" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="size" value="50"/>
		</fr:slot>
		<fr:slot name="salaryPositionRequirements" key="label.mobility.jobOffer.salaryPositionRequirements" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="size" value="50"/>
		</fr:slot>
		<fr:slot name="qualificationRequirements" key="label.mobility.jobOffer.qualificationRequirements" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="formationRequirements" key="label.mobility.jobOffer.formationRequirements" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
		<fr:slot name="professionalExperienceRequirements" key="label.mobility.jobOffer.professionalExperienceRequirements" layout="area" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
			<fr:property name="rows" value="5"/>
			<fr:property name="columns" value="50"/>
		</fr:slot>
	</fr:schema>
</fr:edit>
	

