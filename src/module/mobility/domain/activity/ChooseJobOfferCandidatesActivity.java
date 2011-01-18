package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class ChooseJobOfferCandidatesActivity extends WorkflowActivity<JobOfferProcess, ChooseJobOfferCandidatesInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return (jobOffer.isUnderSelectionEvaluation() || jobOffer.isUnderCandidacyEvaluation())
		&& jobOffer.getOwner().equals(user.getPerson());
    }

    @Override
    protected void process(ChooseJobOfferCandidatesInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().getSelectedWorkerPortfolioInfo().clear();
	activityInformation.getProcess().getJobOffer().getSelectedWorkerPortfolioInfo()
		.addAll(activityInformation.getSelectedWorkers());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ChooseJobOfferCandidatesInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
