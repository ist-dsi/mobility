<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.jobOffers" /> </h2>

<p class="mtop05">
	<html:link action="/mobility.do?method=prepareToCreateJobOffer">
		<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.jobOffers.create" />
	</html:link>
</p>

<fr:form action="/mobility.do?method=jobOffers">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
			<fr:slot name="processNumber" key="label.mobility.processIdentification">
				<fr:property name="size" value="10"/>
			</fr:slot>
			<fr:slot name="offerSearchOwner" key="label.mobility.processIdentification" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:layout name="menu-select">
					<fr:property name="choiceType" value="module.mobility.domain.util.OfferSearch$OfferSearchOwner"/>
					<fr:property name="nullOptionHidden" value="true"/>
				</fr:layout>
			</fr:slot>
			<fr:slot name="offerSearchState" key="label.mobility.processIdentification" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:layout name="menu-select">
					<fr:property name="choiceType" value="module.mobility.domain.util.OfferSearch$OfferSearchState"/>
					<fr:property name="nullOptionHidden" value="true"/>
				</fr:layout>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="MOBILITY_RESOURCES" key="label.mobility.submit"/>
	</html:submit>
</fr:form>
<br/>

<logic:present name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(manage)" value="/mobility.do?method=viewJobOfferProcessToManage" />
			<fr:property name="key(manage)" value="label.mobility.view" />
			<fr:property name="param(manage)" value="OID" />
			<fr:property name="bundle(manage)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIf(manage)" value="canManageJobProcess" />
			
			<fr:property name="link(candidacies)" value="/mobility.do?method=viewJobOfferCandidacies" />
			<fr:property name="key(candidacies)" value="label.mobility.jobOffer.candidacies" />
			<fr:property name="param(candidacies)" value="OID" />
			<fr:property name="bundle(candidacies)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIf(candidacies)" value="canManageJobOfferCandidacies" />
			
			<fr:property name="link(view)" value="/mobility.do?method=viewJobOfferProcess" />
			<fr:property name="key(view)" value="label.mobility.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIfNot(view)" value="canManageJobProcess" />
		</fr:layout>
		<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.mobility.jobOfferProcessIdentification" />
			<fr:slot name="jobOffer.publicationDate" key="label.mobility.approvalDate" layout="null-as-label"/>
			<fr:slot name="jobOffer.endDate" key="label.mobility.endDate" />
			<fr:slot name="jobOffer.title" key="label.mobility.jobOffer.title" />
			<fr:slot name="jobOffer.jobProfile" key="label.mobility.jobOffer.jobProfile" />
		</fr:schema>
	</fr:view>
</logic:present>
