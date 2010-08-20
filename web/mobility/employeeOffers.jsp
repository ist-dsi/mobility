<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.employeeOffers"/>
</h2>

<fr:form action="/mobility.do?method=employeeOffers">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
			<fr:slot name="processNumber" key="label.mobility.processIdentification">
				<fr:property name="size" value="10"/>
			</fr:slot>
			<fr:slot name="offerSearchOwner" key="label.module.mobility.employeeOffers" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="defaultOptionHidden" value="true"/>
				<fr:property name="excludedValues" value="WITH_MY_CANDIDACY"/>
			</fr:slot>
			<fr:slot name="offerSearchState" key="label.mobility.state" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="defaultOptionHidden" value="true"/>
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

<logic:empty name="processes">
	<p>
		<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.none"/>
	</p>
</logic:empty>
<logic:notEmpty name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />

			<fr:property name="link(view)" value="/mobility.do?method=viewWorkerOfferProcess" />
			<fr:property name="key(view)" value="label.mobility.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="MOBILITY_RESOURCES" />
			
			<fr:property name="link(manage)" value="/mobility.do?method=viewWorkerOfferProcessToManage" />
			<fr:property name="key(manage)" value="label.mobility.manage" />
			<fr:property name="param(manage)" value="OID" />
			<fr:property name="bundle(manage)" value="MOBILITY_RESOURCES" />
			<fr:property name="visibleIf(manage)" value="canManageProcess" />
		</fr:layout>
		<fr:schema bundle="MOBILITY_RESOURCES" type="module.mobility.domain.JobOfferProcess">
			<fr:slot name="processIdentification" key="label.mobility.jobOfferProcessIdentification" />
			<fr:slot name="workerOffer.approvalDate" key="label.mobility.approvalDate" layout="null-as-label"/>
			<fr:slot name="workerOffer.personalPortfolioInfo.carrer" key="label.mobility.carrer" />
			<fr:slot name="workerOffer.personalPortfolioInfo.category" key="label.mobility.category" />
			<fr:slot name="workerOffer.personalPortfolioInfo.salary" key="label.mobility.salary" />
		</fr:schema>
	</fr:view>
</logic:notEmpty>
