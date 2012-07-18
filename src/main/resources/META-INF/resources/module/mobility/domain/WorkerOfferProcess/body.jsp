<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>


<bean:define id="workerOffer" name="process" property="workerOffer" type="module.mobility.domain.WorkerOffer"/>


<jsp:include page="processStageView.jsp"/>


<h4>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerOffer.options"/>
</h4>


<div class="infobox mtop1 mbottom1">
	<fr:view name="workerOffer">
		<fr:schema type="module.mobility.domain.WorkerOffer" bundle="MOBILITY_RESOURCES">
			<fr:slot name="beginDate" key="label.mobility.beginDate" layout="picker" required="true"/>
			<fr:slot name="endDate" key="label.mobility.endDate" layout="picker"/>
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="mvert05 thleft"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>

<jsp:include page="workerOfferBody.jsp"/>
