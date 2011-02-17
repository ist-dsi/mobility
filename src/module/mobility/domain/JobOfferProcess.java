package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.activity.CancelJobOfferActivity;
import module.mobility.domain.activity.CancelJobOfferApprovalActivity;
import module.mobility.domain.activity.CancelJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.CancelJobOfferSubmitionForEvaluationActivity;
import module.mobility.domain.activity.CancelJobOfferSubmitionForJuryDefinitionActivity;
import module.mobility.domain.activity.ChooseJobOfferCandidatesActivity;
import module.mobility.domain.activity.EditJobOfferActivity;
import module.mobility.domain.activity.JobOfferApprovalActivity;
import module.mobility.domain.activity.JobOfferArchiveActivity;
import module.mobility.domain.activity.JobOfferConclusionActivity;
import module.mobility.domain.activity.JobOfferJuryDefinitionActivity;
import module.mobility.domain.activity.JobOfferSelectionActivity;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.SubmitJobOfferForApprovalActivity;
import module.mobility.domain.activity.SubmitJobOfferForEvaluationActivity;
import module.mobility.domain.activity.SubmitJobOfferForJuryDefinitionActivity;
import module.mobility.domain.activity.SubmitJobOfferForSelectionActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.mobility.domain.util.MobilityJobOfferProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;
import module.workflow.domain.utils.WorkflowCommentCounter;
import module.workflow.widgets.UnreadCommentsWidget;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/MobilityResources")
public class JobOfferProcess extends JobOfferProcess_Base implements Comparable<JobOfferProcess> {
    private static final String JOB_OFFER_SIGLA = "RCT";
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new EditJobOfferActivity());
	activitiesAux.add(new CancelJobOfferActivity());

	activitiesAux.add(new SubmitJobOfferForSelectionActivity());
	activitiesAux.add(new JobOfferSelectionActivity());
	activitiesAux.add(new SubmitJobOfferForEvaluationActivity());
	activitiesAux.add(new CancelJobOfferSubmitionForEvaluationActivity());
	activitiesAux.add(new JobOfferConclusionActivity());
	activitiesAux.add(new JobOfferArchiveActivity());

	activitiesAux.add(new SubmitJobOfferForJuryDefinitionActivity());
	activitiesAux.add(new CancelJobOfferSubmitionForJuryDefinitionActivity());
	activitiesAux.add(new JobOfferJuryDefinitionActivity());

	activitiesAux.add(new SubmitJobOfferForApprovalActivity());
	activitiesAux.add(new CancelJobOfferSubmitionForApprovalActivity());
	activitiesAux.add(new JobOfferApprovalActivity());
	activitiesAux.add(new CancelJobOfferApprovalActivity());
	activitiesAux.add(new ChooseJobOfferCandidatesActivity());

	activitiesAux.add(new SubmitCandidacyActivity());
	activitiesAux.add(new UnSubmitCandidacyActivity());
	activities = Collections.unmodifiableList(activitiesAux);

	UnreadCommentsWidget.register(new WorkflowCommentCounter(JobOfferProcess.class));
    }

    public JobOfferProcess(final JobOffer jobOffer) {
	super();
	setJobOffer(jobOffer);
	setProcessNumber(jobOffer.getMobilityYear().nextJobOfferNumber().toString());
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
	return getJobOffer().hasAnyJobOfferCandidacy()
		&& (MobilitySystem.getInstance().isManagementMember(user) || (getProcessCreator().equals(user) && getJobOffer()
			.isCandidacyPeriodFinish()));
    }

    public MobilityJobOfferProcessStageView getMobilityProcessStageView() {
	return new MobilityJobOfferProcessStageView(getJobOffer());
    }

    @Override
    public boolean isActive() {
	return getJobOffer().isActive();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
	// TODO Auto-generated method stub
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user) || (getJobOffer().isApproved() && isActive())
		|| (MobilitySystem.getInstance().isManagementMember(user) && !getJobOffer().isUnderConstruction());
    }

    @Override
    public int compareTo(JobOfferProcess otherOfferProcess) {
	return getJobOffer().compareTo(otherOfferProcess.getJobOffer());
    }

    public String getProcessIdentification() {
	return JOB_OFFER_SIGLA + getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    protected MobilityYear getMobilityYear() {
	return getJobOffer().getMobilityYear();
    }

    public boolean getCanManageProcess() {
	final User user = UserView.getCurrentUser();
	return getProcessCreator().equals(user) || (MobilitySystem.getInstance().isManagementMember(user));
    }

    @Override
    public boolean isTicketSupportAvailable() {
	return false;
    }

    @Override
    public List<Class<? extends ProcessFile>> getAvailableFileTypes() {
	final List<Class<? extends ProcessFile>> list = super.getAvailableFileTypes();
	list.add(0, MinutesFile.class);
	return list;
    }

}
