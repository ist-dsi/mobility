package module.mobility.domain.activity;

import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class CancelWorkerJobOfferSubmitionForApprovalActivity extends
	WorkflowActivity<WorkerOfferProcess, ActivityInformation<WorkerOfferProcess>> {

    @Override
    public boolean isActive(WorkerOfferProcess process, User user) {
	WorkerOffer workerOffer = process.getWorkerOffer();
	return workerOffer.isPendingApproval(user);
    }

    @Override
    protected void process(ActivityInformation<WorkerOfferProcess> activityInformation) {
	activityInformation.getProcess().getWorkerOffer().setSubmittedForApprovalDate(null);
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
