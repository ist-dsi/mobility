package module.mobility.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myorg.domain.VirtualHost;
import myorg.domain.contents.ActionNode;
import myorg.domain.contents.Node;
import myorg.domain.groups.UserGroup;
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

	ActionNode homeNode = ActionNode.createActionNode(virtualHost, node, "/mobility", "frontPage",
		"resources.MobilityResources", "link.sideBar.mobility", UserGroup.getInstance());

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "frontPage", "resources.MobilityResources",
		"link.sideBar.mobility.frontPage", UserGroup.getInstance());

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "jobOffers", "resources.MobilityResources",
		"link.sideBar.mobility.jobOffers", UserGroup.getInstance());

	ActionNode.createActionNode(virtualHost, homeNode, "/mobility", "employeeOffers", "resources.MobilityResources",
		"link.sideBar.mobility.employeeOffers", UserGroup.getInstance());

	return forwardToMuneConfiguration(request, virtualHost, node);
    }
}
