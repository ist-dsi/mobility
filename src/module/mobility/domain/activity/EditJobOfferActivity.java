package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.util.BundleUtil;

public class EditJobOfferActivity extends WorkflowActivity<JobOfferProcess, JobOfferInformation> {

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", "activity." + getClass().getSimpleName());
    }

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.getIsUnderConstruction(user);
    }

    @Override
    protected void process(JobOfferInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().edit(activityInformation.getJobOfferBean());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new JobOfferInformation(process, this);
    }

}
