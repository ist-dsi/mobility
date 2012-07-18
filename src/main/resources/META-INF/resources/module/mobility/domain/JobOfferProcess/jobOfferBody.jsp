<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>


<logic:equal name="jobOffer" property="approved" value="true">
	<logic:notEqual name="jobOffer" property="hasAllNeededInfoForSubmitCancidacy" value="true">
		<p class="mbottom15"><em><bean:message key="message.mobility.submitCandidacy.missingPersonalPortfolio" bundle="MOBILITY_RESOURCES"/></em></p>
	</logic:notEqual>
</logic:equal>


<div class="infobox mvert1">
	<fr:view name="jobOffer">
		<fr:schema type="module.mobility.domain.JobOffer" bundle="MOBILITY_RESOURCES">
			<fr:slot name="jobOfferProcess.processIdentification" key="label.mobility.jobOfferProcessIdentification"/>
			<fr:slot name="mobilityYear.year" key="label.mobility.year"/>
			<fr:slot name="vacanciesNumber" key="label.mobility.jobOffer.vacanciesNumber"/>
			<fr:slot name="workplacePath" key="label.mobility.jobOffer.workplace"/>
			<fr:slot name="careerRequirements" key="label.mobility.jobOffer.careerRequirements" />
			<fr:slot name="skillRequirements" key="label.mobility.jobOffer.skillRequirements" />
			<fr:slot name="jobProfile" key="label.mobility.jobOffer.jobProfile" />
			<fr:slot name="knowledgeRequirements" key="label.mobility.jobOffer.knowledgeRequirements" />
			<fr:slot name="qualificationRequirements" key="label.mobility.jobOffer.qualificationRequirements" />
			<fr:slot name="formationRequirements" key="label.mobility.jobOffer.formationRequirements" />
			<fr:slot name="professionalExperienceRequirements" key="label.mobility.jobOffer.professionalExperienceRequirements" />
			<fr:slot name="requiredDocumentsForCandidacy" key="label.mobility.jobOffer.requiredDocumentsForCandidacy" />
			<fr:slot name="additionalRemarks" key="label.mobility.jobOffer.additionalRemarks" />
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="tstyle5 tdtop thtop mvert15 thleft thnowrap"/>
				<fr:property name="rowClasses" value=",,,bold,bold,,,,,,,,,,,,,,,"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>


