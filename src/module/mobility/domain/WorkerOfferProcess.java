package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.mobility.domain.activity.EditWorkerJobOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

public class WorkerOfferProcess extends WorkerOfferProcess_Base {
    
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new EditWorkerJobOffer());
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
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	return (List) activities;
    }

    @Override
    public User getProcessCreator() {
	return getWorkerOffer().getPersonalPortfolio().getPerson().getUser();
    }

    @Override
    public boolean isActive() {
	return true;
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
	// do nothing.
    }

}
