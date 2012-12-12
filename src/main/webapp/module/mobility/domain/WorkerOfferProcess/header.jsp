<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="workerOffer" name="process" property="workerOffer"/>
<bean:define id="personalPortfolioInfo" name="workerOffer" property="personalPortfolioInfo"/>
<bean:define id="personalPortfolio" name="personalPortfolioInfo" property="personalPortfolio"/>
<bean:define id="person" name="personalPortfolio" property="person"/>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.workerProcessIdentification"/>
	<span class="processNumber">(<fr:view name="workerOffer" property="workerOfferProcess.processIdentification"/>)</span>	
</h2>
