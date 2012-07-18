/*
 * @(#)InterfaceCreationAction.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.MobilitySystem;
import module.mobility.domain.groups.MobilityGroup;
import module.organization.domain.AccountabilityType;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Unit;
import module.organization.domain.groups.PersonGroup;
import module.organization.domain.groups.UnitGroup;
import module.organizationIst.domain.IstAccountabilityType;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.domain.contents.ActionNode;
import pt.ist.bennu.core.domain.contents.Node;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.domain.groups.Role;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/mobilityInterfaceCreationAction")
/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class InterfaceCreationAction extends ContextBaseAction {

    @CreateNodeAction(bundle = "MOBILITY_RESOURCES", key = "add.node.mobility.interface", groupKey = "label.module.mobility")
    public final ActionForward createAnnouncmentNodes(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");

	OrganizationalModel organizationalModel = MobilitySystem.getInstance().getOrganizationalModel();
	if (organizationalModel != null) {
	    UnitGroup employeesGroup = UnitGroup.getOrCreateGroup((Unit) organizationalModel.getPartiesIterator().next(),
		    new AccountabilityType[] { IstAccountabilityType.PERSONNEL.readAccountabilityType() },
		    new AccountabilityType[] { IstAccountabilityType.ORGANIZATIONAL.readAccountabilityType() });

	    final PersistentGroup managementsGroup = MobilityGroup.getInstance();

	    ActionNode homeNode = ActionNode.createActionNode(virtualHost, node, "/mobility", "jobOffers",
		    "resources.MobilityResources", "link.sideBar.mobility", PersonGroup.getInstance());

	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "frontPage", "resources.MobilityResources",
		    "link.sideBar.mobility.frontPage", managementsGroup);

	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "portfolio", "resources.MobilityResources",
		    "link.sideBar.mobility.personalProfile", employeesGroup);

	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "jobOffers", "resources.MobilityResources",
		    "link.sideBar.mobility.jobOffers", PersonGroup.getInstance());

	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "employeeOffers", "resources.MobilityResources",
		    "link.sideBar.mobility.employeeOffers", PersonGroup.getInstance());

	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "configuration", "resources.MobilityResources",
		    "link.sideBar.mobility.configuration", Role.getRole(RoleType.MANAGER));

	} else {
	    ActionNode homeNode = ActionNode.createActionNode(virtualHost, node, "/mobility", "configuration",
		    "resources.MobilityResources", "link.sideBar.mobility", Role.getRole(RoleType.MANAGER));
	    ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "configuration", "resources.MobilityResources",
		    "link.sideBar.mobility.configuration", Role.getRole(RoleType.MANAGER));
	}
	return forwardToMuneConfiguration(request, virtualHost, node);
    }
}
