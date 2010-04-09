<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<logic:empty name="offerList">
	<p>
		<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.process.list.none"/>
	</p>
</logic:empty>
<logic:notEmpty name="offerList">
	<ul class="operations mtop0">
		<logic:iterate id="process" name="offerList">
			<li>
				<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="process" paramProperty="externalId">
					<bean:write name="process" property="processIdentification"/>
				</html:link>
			</li>
		</logic:iterate>
	</ul>
</logic:notEmpty>
