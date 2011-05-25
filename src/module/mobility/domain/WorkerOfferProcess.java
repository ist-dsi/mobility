package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.ist.emailNotifier.domain.Email;

import module.mobility.domain.activity.CancelWorkerJobOfferApprovalActivity;
import module.mobility.domain.activity.CancelWorkerJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.CancelWorkerOfferActivity;
import module.mobility.domain.activity.EditWorkerJobOffer;
import module.mobility.domain.activity.SubmitWorkerJobOfferForApprovalActivity;
import module.mobility.domain.activity.UpdateWorkerJobOfferProfessionalInformation;
import module.mobility.domain.activity.WorkerJobOfferApprovalActivity;
import module.mobility.domain.util.MobilityWorkerOfferProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import module.workflow.domain.utils.WorkflowCommentCounter;
import module.workflow.widgets.UnreadCommentsWidget;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.BundleUtil;
import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/MobilityResources")
public class WorkerOfferProcess extends WorkerOfferProcess_Base implements Comparable<WorkerOfferProcess> {

    private static final String WORKER_OFFER_SIGLA = "PRO";
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new EditWorkerJobOffer());
	activitiesAux.add(new UpdateWorkerJobOfferProfessionalInformation());
	activitiesAux.add(new SubmitWorkerJobOfferForApprovalActivity());
	activitiesAux.add(new CancelWorkerJobOfferSubmitionForApprovalActivity());
	activitiesAux.add(new WorkerJobOfferApprovalActivity());
	activitiesAux.add(new CancelWorkerJobOfferApprovalActivity());
	activitiesAux.add(new CancelWorkerOfferActivity());
	activities = Collections.unmodifiableList(activitiesAux);

	UnreadCommentsWidget.register(new WorkflowCommentCounter(WorkerOfferProcess.class));
    }

    public WorkerOfferProcess(final WorkerOffer workerOffer) {
	super();
	setWorkerOffer(workerOffer);
	setProcessNumber(workerOffer.getMobilityYear().nextWorkerOfferNumber().toString());
    }

    protected MobilityYear getMobilityYear() {
	return getWorkerOffer().getMobilityYear();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	return (List) activities;
    }

    @Override
    public User getProcessCreator() {
	return getWorkerOffer().getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().getUser();
    }

    public static Set<WorkerOfferProcess> getWorkerJobOfferProcessByUser(User user) {
	Set<WorkerOfferProcess> processes = new TreeSet<WorkerOfferProcess>();
	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOfferSet()) {
	    if (workerOffer.getWorkerOfferProcess().isAccessible(user)) {
		processes.add(workerOffer.getWorkerOfferProcess());
	    }
	}
	return processes;
    }

    @Override
    public boolean isActive() {
	return getWorkerOffer().isActive();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
	List<String> toAddress = new ArrayList<String>();
	final String email = user.getPerson().getRemotePerson().getEmailForSendingEmails();
	if (email != null) {
	    toAddress.add(email);

	    final User loggedUser = UserView.getCurrentUser();
	    new Email("Aplicações Centrais do IST", "noreply@ist.utl.pt", new String[] {}, toAddress, Collections.EMPTY_LIST,
		    Collections.EMPTY_LIST,
		    BundleUtil.getFormattedStringFromResourceBundle("resources/MobilityResources", "label.email.commentCreated.subject", getProcessIdentification()),
		    BundleUtil.getFormattedStringFromResourceBundle("resources/MobilityResources", "label.email.commentCreated.body", loggedUser.getPerson().getName(), getProcessIdentification(), comment));
	}
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user) || (getWorkerOffer().isApproved() && isActive())
		|| (MobilitySystem.getInstance().isManagementMember(user) && !getWorkerOffer().isUnderConstruction());
    }

    @Override
    public int compareTo(WorkerOfferProcess otherOfferProcess) {
	return getWorkerOffer().compareTo(otherOfferProcess.getWorkerOffer());
    }

    public String getProcessIdentification() {
	return WORKER_OFFER_SIGLA + getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    public MobilityWorkerOfferProcessStageView getMobilityProcessStageView() {
	return new MobilityWorkerOfferProcessStageView(getWorkerOffer());
    }

    public boolean getCanManageProcess() {
	final User user = UserView.getCurrentUser();
	return getProcessCreator().equals(user) || (MobilitySystem.getInstance().isManagementMember(user));
    }

    @Override
    public boolean isTicketSupportAvailable() {
	return false;
    }

}
