<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>

<fr:edit id="information" name="information" schema="activityInformation.SubmitCandidacyActivity" action="/mobility.do?method=submitCandidacy">
	<fr:destination name="cancel" path="/mobility.do?method=returnToJobOfferProcess"/>
</fr:edit>
