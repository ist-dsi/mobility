<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<logic:present name="offerList">
	<logic:empty name="offerList">
		<p>
			<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.process.list.none"/>
		</p>
	</logic:empty>
	<logic:notEmpty name="offerList">
		<fr:view name="offerList">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />
				<fr:property name="link(view)" value="/mobility.do?method=viewJobOfferProcessToManage" />
				<fr:property name="key(view)" value="label.mobility.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="MOBILITY_RESOURCES" />
				<fr:property name="visibleIf(view)" value="canManageProcess" />
			</fr:layout>
			<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
				<fr:slot name="processIdentification" key="label.mobility.jobOfferProcessIdentification" />
				<fr:slot name="jobOffer.workplacePath" key="label.mobility.jobOffer.workplace" />
				<fr:slot name="jobOffer.careerRequirements" key="label.mobility.jobOffer.careerRequirements" />
				<fr:slot name="jobOffer.jobProfile" key="label.mobility.jobOffer.jobProfile" />
				<fr:slot name="jobOffer.skillRequirements" key="label.mobility.jobOffer.skillRequirements" />
				<fr:slot name="jobOffer.publicationEndDate" key="label.mobility.candicaciesDeadline" />
				<fr:slot name="jobOffer.jobOfferCandidacyCount" key="label.mobility.jobOffer.candidacies" />
			</fr:schema>
		</fr:view>
	</logic:notEmpty>
</logic:present>
