package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class JobOfferSelectionActivity extends WorkflowActivity<JobOfferProcess, JobOfferSelectionInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isPendingSelection() && MobilitySystem.getInstance().isManagementMember(user);
    }

    @Override
    protected void process(JobOfferSelectionInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().getSelectedWorkerOfferCandidateSet().clear();
	activityInformation.getProcess().getJobOffer().getSelectedWorkerOfferCandidateSet()
		.addAll(activityInformation.getSelectedWorkerOffers());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new JobOfferSelectionInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
