package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.mobility.domain.activity.ApprovalActivity;
import module.mobility.domain.activity.EditJobOfferActivity;
import module.mobility.domain.activity.SubmitForApprovalActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

public class JobOfferProcess extends JobOfferProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new SubmitForApprovalActivity());
	activitiesAux.add(new ApprovalActivity());
	activitiesAux.add(new EditJobOfferActivity());
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
	return true;
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user);
	// ou publicado
	// ou pendeste de autorizacao do DRH e eu for do DRH
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
}
