package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class EditJobOfferActivity extends WorkflowActivity<JobOfferProcess, JobOfferInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isUnderConstruction(user);
    }

    @Override
    protected void process(JobOfferInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().edit(activityInformation.getJobOfferBean());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new JobOfferInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
