package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.activity.CancelWorkerJobOfferApprovalActivity;
import module.mobility.domain.activity.CancelWorkerJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.CancelWorkerOfferActivity;
import module.mobility.domain.activity.EditWorkerJobOffer;
import module.mobility.domain.activity.SubmitWorkerJobOfferForApprovalActivity;
import module.mobility.domain.activity.UpdateWorkerJobOfferProfessionalInformation;
import module.mobility.domain.activity.WorkerJobOfferApprovalActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

public class WorkerOfferProcess extends WorkerOfferProcess_Base {

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
    }

    public WorkerOfferProcess(final WorkerOffer workerOffer) {
	super();
	setWorkerOffer(workerOffer);
	setProcessNumber(workerOffer.getMobilityYear().nextNumber().toString());
    }

    @Override
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
    protected Offer getOffer() {
	return getWorkerOffer();
    }
}
