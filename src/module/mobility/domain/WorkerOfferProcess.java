package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.activity.CancelWorkerJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.EditWorkerJobOffer;
import module.mobility.domain.activity.SubmitWorkerJobOfferForApprovalActivity;
import module.mobility.domain.activity.UpdateWorkerJobOfferProfessionalInformation;
import module.mobility.domain.activity.WorkerJobOfferApprovalActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

import org.joda.time.DateTime;

public class WorkerOfferProcess extends WorkerOfferProcess_Base implements Comparable<WorkerOfferProcess> {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new EditWorkerJobOffer());
	activitiesAux.add(new UpdateWorkerJobOfferProfessionalInformation());
	activitiesAux.add(new SubmitWorkerJobOfferForApprovalActivity());
	activitiesAux.add(new CancelWorkerJobOfferSubmitionForApprovalActivity());
	activitiesAux.add(new WorkerJobOfferApprovalActivity());
	activities = Collections.unmodifiableList(activitiesAux);
    }

    public WorkerOfferProcess(final WorkerOffer workerOffer) {
	super();
	setWorkerOffer(workerOffer);
	setProcessNumber(workerOffer.getMobilityYear().nextNumber().toString());
    }

    public String getProcessIdentification() {
	return getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    private MobilityYear getMobilityYear() {
	return getWorkerOffer().getMobilityYear();
    }

    @Override
    public int compareTo(WorkerOfferProcess otherWorkerOfferProcess) {
	return getWorkerOffer().compareTo(otherWorkerOfferProcess.getWorkerOffer());
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	return (List) activities;
    }

    @Override
    public User getProcessCreator() {
	return getWorkerOffer().getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().getUser();
    }

    @Override
    public boolean isActive() {
	return !getWorkerOffer().getCanceled()
		&& (getWorkerOffer().getEndDate() != null && getWorkerOffer().getEndDate().isAfter(new DateTime()));
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
	// do nothing.
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user) || (getWorkerOffer().isApproved() && isActive())
		|| (MobilitySystem.getInstance().isManagementMember(user) && getWorkerOffer().isUnderConstruction(user));
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

}
