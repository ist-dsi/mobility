package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.activity.CancelJobOfferActivity;
import module.mobility.domain.activity.CancelJobOfferApprovalActivity;
import module.mobility.domain.activity.CancelJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.EditJobOfferActivity;
import module.mobility.domain.activity.JobOfferApprovalActivity;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.SubmitJobOfferForApprovalActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class JobOfferProcess extends JobOfferProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new SubmitJobOfferForApprovalActivity());
	activitiesAux.add(new EditJobOfferActivity());
	activitiesAux.add(new CancelJobOfferSubmitionForApprovalActivity());
	activitiesAux.add(new JobOfferApprovalActivity());
	activitiesAux.add(new CancelJobOfferApprovalActivity());
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

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	return (List) activities;
    }

    @Override
    public User getProcessCreator() {
	return getJobOffer().getCreator().getUser();
    }

    public static Set<JobOfferProcess> getJobOfferProcessByUser(User user) {
	Set<JobOfferProcess> processes = new TreeSet<JobOfferProcess>();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOfferSet()) {
	    if (jobOffer.getJobOfferProcess().isAccessible(user)) {
		processes.add(jobOffer.getJobOfferProcess());
	    }
	}
	return processes;
    }

    public boolean getCanManageJobOfferCandidacies() {
	final User user = UserView.getCurrentUser();
	return MobilitySystem.getInstance().isManagementMember(user) && !getJobOffer().getCandidatePortfolioInfoSet().isEmpty();
    }

    @Override
    protected Offer getOffer() {
	return getJobOffer();
    }

}
