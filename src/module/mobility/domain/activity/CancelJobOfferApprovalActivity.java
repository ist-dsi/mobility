package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class CancelJobOfferApprovalActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isApproved(user) && jobOffer.getConclusionDate() == null && !jobOffer.isArchived();
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	activityInformation.getProcess().getJobOffer().setApprovalDate(null);
	activityInformation.getProcess().getJobOffer().setJobOfferApproverPerson(null);
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }
}
