<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

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
				<fr:slot name="jobOffer.title" key="label.mobility.jobOffer.title" />
				<fr:slot name="jobOffer.careerRequirements" key="label.mobility.jobOffer.title" />
				<fr:slot name="jobOffer.jobProfile" key="label.mobility.jobOffer.jobProfile" />
				<fr:slot name="jobOffer.skillRequirements" key="label.mobility.jobOffer.jobProfile" />
				<fr:slot name="jobOffer.publicationEndDate" key="label.mobility.candicaciesDeadline" />
				<fr:slot name="jobOffer.candidatePortfolioInfoCount" key="label.mobility.jobOffer.candidacies" />
			</fr:schema>
		</fr:view>
	</logic:notEmpty>
</logic:present>