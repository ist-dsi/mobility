package module.mobility.domain.activity;

import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.util.BundleUtil;

public class CancelWorkerOfferActivity extends WorkflowActivity<WorkerOfferProcess, ActivityInformation<WorkerOfferProcess>> {

    @Override
    public boolean isActive(WorkerOfferProcess process, User user) {
	WorkerOffer workerOffer = process.getWorkerOffer();
	return workerOffer.isActiveOrPendingApproval() && workerOffer.getOwner().equals(user.getPerson());
    }

    @Override
    protected void process(ActivityInformation<WorkerOfferProcess> activityInformation) {
	activityInformation.getProcess().getWorkerOffer().setCanceled(true);
    }

    @Override
    public ActivityInformation<WorkerOfferProcess> getActivityInformation(WorkerOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public boolean isConfirmationNeeded(WorkerOfferProcess process) {
	return true;
    }

    @Override
    public String getLocalizedConfirmationMessage() {
	return BundleUtil.getStringFromResourceBundle(getUsedBundle(), "activity.confirmation." + getClass().getSimpleName());
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }
}
