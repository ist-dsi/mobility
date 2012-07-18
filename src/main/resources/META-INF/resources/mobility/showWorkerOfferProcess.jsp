<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOfferProcessIdentification"/>
	<span class="processNumber">(<bean:write name="process" property="processIdentification"/>)</span>	 
</h2>

<jsp:include page="../module/mobility/domain/WorkerOfferProcess/workerOfferBody.jsp"/>
