package module.mobility.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.JuryMember;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.mobility.domain.activity.JobOfferJuryDefinitionActivity;
import module.mobility.domain.activity.JobOfferJuryInformation;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation.QualificationHolder;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.mobility.domain.activity.WorkerJobOfferInformation;
import module.mobility.domain.util.JobOfferBean;
import module.mobility.domain.util.OfferSearch;
import module.mobility.domain.util.OfferSearch.OfferSearchOwner;
import module.mobility.domain.util.OfferSearch.OfferSearchState;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Person;
import module.organization.presentationTier.actions.OrganizationModelAction;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.presentationTier.actions.ProcessManagement;
import module.workflow.util.WorkflowProcessViewer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.base.BaseAction;
import org.fenixedu.bennu.struts.portal.EntryPoint;
import org.fenixedu.bennu.struts.portal.StrutsApplication;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

@StrutsFunctionality(app = MobilityAction.class, path = "mobilityManagers", titleKey = "label.module.mobility.frontPage", accessGroup = "#MobilityManagers")
@Mapping(path = "/mobilityManagers")

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class MobilityManagersAction extends BaseAction {

    @EntryPoint
    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferSearch offerSearch = getRenderedObject("offerSearch");
        if (offerSearch == null) {
            offerSearch = new OfferSearch();
        }
        JobOfferProcess jobOfferProcess = offerSearch.getJobOfferProcess(Authenticate.getUser());
        if (jobOfferProcess != null) {
            return ProcessManagement.forwardToProcess(jobOfferProcess);
        }
        WorkerOfferProcess workerOfferProcess = offerSearch.getWorkerOfferProcess(Authenticate.getUser());
        if (workerOfferProcess != null) {
            return ProcessManagement.forwardToProcess(workerOfferProcess);
        }
        request.setAttribute("offerSearch", offerSearch);
        return forward("/mobility/frontPage.jsp");
    }

}
