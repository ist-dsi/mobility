package module.mobility.presentationTier.action;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.util.JobOfferBean;
import module.organization.domain.Person;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/mobility")
public class MobilityAction extends ContextBaseAction {

    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	return forward(request, "/mobility/frontPage.jsp");
    }

    public ActionForward jobOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Set<JobOfferProcess> processes = JobOfferProcess.getJobOfferProcessByUser(UserView.getCurrentUser());
	request.setAttribute("processes", processes);
	return forward(request, "/mobility/jobOffers.jsp");
    }

    public ActionForward employeeOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	return forward(request, "/mobility/employeeOffers.jsp");
    }

    public ActionForward prepareToCreateJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferBean jobOfferBean = new JobOfferBean();
	request.setAttribute("jobOfferBean", jobOfferBean);
	return forward(request, "/mobility/createJobOffer.jsp");
    }

    public ActionForward createJobOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferBean jobOfferBean = getRenderedObject();
	jobOfferBean.create();
	return jobOffers(mapping, form, request, response);
    }

    public ActionForward viewJobOfferProcess(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    public ActionForward portfolio(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final User user = UserView.getCurrentUser();
	if (user == null || !user.hasPerson()) {
	    return frontPage(mapping, form, request, response);
	}
	final Person person = user.getPerson();
	if (!person.hasPersonalPortfolio()) {
	    PersonalPortfolio.create(person);
	}
	final PersonalPortfolio personalPortfolio = person.getPersonalPortfolio();
	final PersonalPortfolioProcess personalPortfolioProcess = personalPortfolio.getPersonalPortfolioProcess();
	return ProcessManagement.forwardToProcess(personalPortfolioProcess);
    }

}
