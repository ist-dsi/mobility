package module.mobility.domain.activity;

import module.mobility.domain.MobilitySystem;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class WorkerJobOfferApprovalActivity extends WorkflowActivity<WorkerOfferProcess, ActivityInformation<WorkerOfferProcess>> {

    @Override
    public boolean isActive(WorkerOfferProcess process, User user) {
	WorkerOffer workerOffer = process.getWorkerOffer();
	return workerOffer.isPendingApproval(user) && MobilitySystem.getInstance().isManagementMember(user);
    }

    @Override
    protected void process(ActivityInformation<WorkerOfferProcess> activityInformation) {
	activityInformation.getProcess().getWorkerOffer().approve();
    }

    @Override
    public ActivityInformation<WorkerOfferProcess> getActivityInformation(WorkerOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }
}
