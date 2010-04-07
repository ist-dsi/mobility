package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.mobility.domain.activity.ApprovalActivity;
import module.mobility.domain.activity.CancelApprovalActivity;
import module.mobility.domain.activity.CancelJobOfferActivity;
import module.mobility.domain.activity.CancelSubmitionForApprovalActivity;
import module.mobility.domain.activity.EditJobOfferActivity;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.SubmitForApprovalActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.joda.time.DateTime;

public class JobOfferProcess extends JobOfferProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new SubmitForApprovalActivity());
	activitiesAux.add(new EditJobOfferActivity());
	activitiesAux.add(new CancelSubmitionForApprovalActivity());
	activitiesAux.add(new ApprovalActivity());
	activitiesAux.add(new CancelApprovalActivity());
	activitiesAux.add(new CancelJobOfferActivity());
	activitiesAux.add(new SubmitCandidacyActivity());
	activitiesAux.add(new UnSubmitCandidacyActivity());
	activities = Collections.unmodifiableList(activitiesAux);
    }

    public JobOfferProcess(final JobOffer jobOffer) {
	super();
	setJobOffer(jobOffer);
	setProcessNumber(jobOffer.getMobilityYear().nextNumber().toString());
    }

    public String getProcessIdentification() {
	return getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    private MobilityYear getMobilityYear() {
	return getJobOffer().getMobilityYear();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	return (List) activities;
    }

    @Override
    public User getProcessCreator() {
	return getJobOffer().getCreator().getUser();
    }

    @Override
    public boolean isActive() {
	return !getJobOffer().getCanceled() && getJobOffer().getEndDate().isAfter(new DateTime());
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user) || (getJobOffer().isApproved() && isActive())
		|| (MobilitySystem.getInstance().isManagementMember(user));
    }

    public static Set<JobOfferProcess> getJobOfferProcessByUser(User user) {
	Set<JobOfferProcess> processes = new HashSet<JobOfferProcess>();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOfferSet()) {
	    if (jobOffer.getJobOfferProcess().isAccessible(user)) {
		processes.add(jobOffer.getJobOfferProcess());
	    }
	}
	return processes;
    }

    public boolean getCanManageJobProcess() {
	final User user = UserView.getCurrentUser();
	return getProcessCreator().equals(user) || (MobilitySystem.getInstance().isManagementMember(user));
    }

    public boolean getCanManageJobOfferCandidacies() {
	final User user = UserView.getCurrentUser();
	return MobilitySystem.getInstance().isManagementMember(user) && !getJobOffer().getCandidatePortfolioInfoSet().isEmpty();
    }
}
