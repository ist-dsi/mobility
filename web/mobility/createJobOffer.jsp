<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.jobOffers.create"/></h2>
	
<fr:edit id="jobOfferBean" name="jobOfferBean" action="/mobility.do?method=createJobOffer">
	<fr:schema type="module.mobility.domain.util.JobOfferBean" bundle="MOBILITY_RESOURCES">
		<fr:slot name="beginDate" key="label.mobility.beginDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
		<fr:slot name="endDate" key="label.mobility.endDate" layout="picker" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
		<fr:slot name="title" key="label.mobility.jobOffer.title" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
			<fr:property name="size" value="50"/>
		</fr:slot>
	</fr:schema>
</fr:edit>
	

