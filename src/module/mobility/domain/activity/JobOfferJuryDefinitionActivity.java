package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class JobOfferJuryDefinitionActivity extends WorkflowActivity<JobOfferProcess, JobOfferJuryInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isPendingJuryDefinition() && jobOffer.getOwner().equals(user.getPerson());
    }

    @Override
    protected void process(JobOfferJuryInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().getJuryMemberSet().clear();
	activityInformation.getProcess().getJobOffer().getJuryMemberSet().addAll(activityInformation.getJuryMembers());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new JobOfferJuryInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
	return false;
    }

}
