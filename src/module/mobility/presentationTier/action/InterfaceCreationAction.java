package module.mobility.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.groups.MobilityGroup;
import module.organization.domain.groups.PersonGroup;
import myorg.domain.RoleType;
import myorg.domain.VirtualHost;
import myorg.domain.contents.ActionNode;
import myorg.domain.contents.Node;
import myorg.domain.groups.IntersectionGroup;
import myorg.domain.groups.NegationGroup;
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

	final PersistentGroup managementsGroup = MobilityGroup.getInstance();

	ActionNode homeNode = ActionNode.createActionNode(virtualHost, node, "/mobility", "frontPage",
		"resources.MobilityResources", "link.sideBar.mobility", managementsGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "frontPage", "resources.MobilityResources",
		"link.sideBar.mobility.frontPage", managementsGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "portfolio", "resources.MobilityResources",
		"link.sideBar.mobility.personalProfile", managementsGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "jobOffers", "resources.MobilityResources",
		"link.sideBar.mobility.jobOffers", managementsGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "employeeOffers", "resources.MobilityResources",
		"link.sideBar.mobility.employeeOffers", managementsGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "configuration", "resources.MobilityResources",
		"link.sideBar.mobility.configuration", Role.getRole(RoleType.MANAGER));

	final NegationGroup nonManagementsGroup = NegationGroup.createNegationGroup(managementsGroup);
	final IntersectionGroup otherPeopleGroup = IntersectionGroup.createIntersectionGroup(PersonGroup.getInstance(),
		nonManagementsGroup);

	homeNode = ActionNode.createActionNode(virtualHost, node, "/mobility", "portfolio", "resources.MobilityResources",
		"link.sideBar.mobility", otherPeopleGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "portfolio", "resources.MobilityResources",
		"link.sideBar.mobility.personalProfile", otherPeopleGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "jobOffers", "resources.MobilityResources",
		"link.sideBar.mobility.jobOffers", otherPeopleGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "employeeOffers", "resources.MobilityResources",
		"link.sideBar.mobility.employeeOffers", otherPeopleGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "configuration", "resources.MobilityResources",
		"link.sideBar.mobility.configuration", Role.getRole(RoleType.MANAGER));

	return forwardToMuneConfiguration(request, virtualHost, node);
    }
}
