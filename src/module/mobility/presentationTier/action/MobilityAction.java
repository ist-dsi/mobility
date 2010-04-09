package module.mobility.presentationTier.action;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOfferProcess;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation.QualificationHolder;
import module.mobility.domain.util.JobOfferBean;
import module.mobility.domain.util.OfferSearch;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import module.workflow.presentationTier.WorkflowLayoutContext;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.presentationTier.Context;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/mobility")
public class MobilityAction extends ContextBaseAction {

    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	OfferSearch offerSearch = getRenderedObject("offerSearch");
	if (offerSearch == null) {
	    offerSearch = new OfferSearch();
	}
	WorkflowProcess process = offerSearch.getOfferProcess(UserView.getCurrentUser());
	if (process != null) {
	    return ProcessManagement.forwardToProcess(process);
	}
	request.setAttribute("offerSearch", offerSearch);
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
	Set<WorkerOfferProcess> workerOffers = WorkerOfferProcess.getWorkerJobOfferProcessByUser(UserView.getCurrentUser());
	request.setAttribute("workerOffers", workerOffers);
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

    public ActionForward viewJobOfferProcessToManage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    public ActionForward viewJobOfferProcess(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward submitCandidacy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	SubmitCandidacyActivity submitCandidacyActivity = new SubmitCandidacyActivity();
	if (submitCandidacyActivity.isActive(jobOfferProcess)) {
	    ActivityInformation<JobOfferProcess> activityInformation = submitCandidacyActivity
		    .getActivityInformation(jobOfferProcess);
	    activityInformation.execute();
	}
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward unSubmitCandidacy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	UnSubmitCandidacyActivity unSubmitCandidacyActivity = new UnSubmitCandidacyActivity();
	if (unSubmitCandidacyActivity.isActive(jobOfferProcess)) {
	    ActivityInformation<JobOfferProcess> activityInformation = unSubmitCandidacyActivity
		    .getActivityInformation(jobOfferProcess);
	    activityInformation.execute();
	}
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward viewJobOfferCandidacies(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/mobility/showJobOfferCandidacies.jsp");
    }

    public ActionForward portfolio(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
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

    public ActionForward configuration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
	request.setAttribute("mobilitySystem", mobilitySystem);
	return forward(request, "/mobility/configuration.jsp");
    }

    public ActionForward configureManagers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
	request.setAttribute("mobilitySystem", mobilitySystem);
	return forward(request, "/mobility/configureManagers.jsp");
    }

    private ActionForward editProfessionalInfoPostback(final HttpServletRequest request,
	    final PersonalPortfolioInfoInformation activityInformation, final PersonalPortfolioProcess personalPortfolioProcess) {
	final Context context = getContext(request);
	final WorkflowLayoutContext workflowLayoutContext = personalPortfolioProcess.getLayout();
	workflowLayoutContext.setElements(context.getPath());
	setContext(request, workflowLayoutContext);

	return ProcessManagement.performActivityPostback(activityInformation, request);
    }

    public ActionForward addNewQualification(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final PersonalPortfolioInfoInformation activityInformation = getRenderedObject("activityBean");
	final PersonalPortfolioProcess personalPortfolioProcess = activityInformation.getProcess();
	final WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> activity = activityInformation
		.getActivity();

	RenderUtils.invalidateViewState();

	activityInformation.getQualificationHolders().add(new QualificationHolder());

	return editProfessionalInfoPostback(request, activityInformation, personalPortfolioProcess);
    }

    public ActionForward removeQualification(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final PersonalPortfolioInfoInformation activityInformation = getRenderedObject("activityBean");
	final PersonalPortfolioProcess personalPortfolioProcess = activityInformation.getProcess();
	final WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> activity = activityInformation
		.getActivity();
	String indexToRemove = (String) request.getAttribute("qualificationIndex");
	System.out.println("indexToRemove: " + indexToRemove);
	System.out.println("activityForm: " + request.getAttribute("activityForm"));
	if (indexToRemove == null) {
	    indexToRemove = request.getParameter("qualificationIndex");
	}
	System.out.println("indexToRemove: " + indexToRemove);

	RenderUtils.invalidateViewState();

	activityInformation.getQualificationHolders().remove(Integer.parseInt(indexToRemove));

	return editProfessionalInfoPostback(request, activityInformation, personalPortfolioProcess);
    }

    public ActionForward saveProfessionalInformation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final PersonalPortfolioInfoInformation activityInformation = getRenderedObject("activityBean");
	final PersonalPortfolioProcess personalPortfolioProcess = activityInformation.getProcess();
	final WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> activity = activityInformation
		.getActivity();
	activity.execute(activityInformation);
	return ProcessManagement.forwardToProcess(personalPortfolioProcess);
    }

}
