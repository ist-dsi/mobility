package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

import org.joda.time.DateTime;

public class SubmitJobOfferForJuryDefinitionActivity extends
	WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return !jobOffer.isInInternalRecruitment()
		&& ((jobOffer.getCandidateWorkerOffer().isEmpty() && MobilitySystem.getInstance().isManagementMember(user)) || jobOffer
			.isPendingEvaluation()
			&& jobOffer.getSelectedWorkerOfferSet().isEmpty()
			&& jobOffer.getOwner().getUser().equals(user));
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	activityInformation.getProcess().getJobOffer().setSubmittedForJuryDefinitionDate(new DateTime());
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
