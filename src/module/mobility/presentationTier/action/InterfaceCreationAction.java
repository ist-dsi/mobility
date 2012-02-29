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
import myorg.domain.RoleType;
import myorg.domain.VirtualHost;
import myorg.domain.contents.ActionNode;
import myorg.domain.contents.Node;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.groups.Role;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/mobilityInterfaceCreationAction")
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
