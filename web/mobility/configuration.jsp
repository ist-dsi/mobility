<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

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

<logic:present name="mobilitySystem" property="managementUnit">
<fr:view name="mobilitySystem">
	<fr:layout name="tabular-list">
		<fr:property name="classes" value="plist mtop05 width100pc"/>
	</fr:layout>
	<fr:schema type="module.mobility.domain.MobilitySystem" bundle="MOBILITY_RESOURCES">
		<fr:slot name="managementUnit.presentationName" key="label.mobility.configuration.managementUnit"/>
		<fr:slot name="managementAccountabilityType.name" key="label.mobility.configuration.managementAccountabilityType"/>
	</fr:schema>
</fr:view>
</logic:present>
<html:link action="/mobility.do?method=configureManagers">
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.configure"/>
</html:link>
<br/>
<logic:present name="mobilitySystem" property="managementUnit">
	<br/>
	<h3>
		<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.configuration.managementMembers"/>
	</h3>
	<fr:view name="mobilitySystem" property="managementMembers">
		<fr:schema type="module.organization.domain.Accountability" bundle="ORGANIZATION_RESOURCES">
			<fr:slot name="child.partyName" key="label.name"/>
			<fr:slot name="child.user.username" key="label.username"/>
			<fr:slot name="beginDate" key="label.begin"/>
			<fr:slot name="endDate" key="label.end" />
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>
		</fr:layout>
	</fr:view>
</logic:present>



