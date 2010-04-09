package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.util.BundleUtil;

public class CancelJobOfferActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isUnderConstruction(user);
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	activityInformation.getProcess().getJobOffer().setCanceled(true);
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public boolean isConfirmationNeeded(JobOfferProcess process) {
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
