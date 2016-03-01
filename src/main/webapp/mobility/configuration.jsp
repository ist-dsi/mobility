<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.configuration"/></h2>


<logic:notPresent name="mobilitySystem" property="organizationalModel">
	<p>
		<bean:message key="label.mobility.organizationalModel.not.defined" bundle="MOBILITY_RESOURCES"/>
		<br/>
		<html:link action="/mobility.do?method=prepareSelectOrganizationalModel">
			<bean:message key="label.mobility.select.organizationalModel" bundle="MOBILITY_RESOURCES"/>
		</html:link>
	</p>
</logic:notPresent>
<logic:present name="mobilitySystem" property="organizationalModel">
	<br/>
	<div class="orgTBox orgTBoxLight">
		<html:link action="/organizationModel.do?method=viewModel" paramId="organizationalModelOid" paramName="mobilitySystem" paramProperty="organizationalModel.externalId">
			<bean:write name="mobilitySystem" property="organizationalModel.name.content"/>
		</html:link>
	</div>	
	<p>
		<html:link action="/mobility.do?method=prepareSelectOrganizationalModel">
			<bean:message key="label.mobility.change.organizationalModel" bundle="MOBILITY_RESOURCES"/>
		</html:link>
	</p>
	<br/>
</logic:present>



