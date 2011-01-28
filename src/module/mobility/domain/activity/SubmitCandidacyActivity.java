package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferCandidacy;
import module.mobility.domain.JobOfferProcess;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class SubmitCandidacyActivity extends WorkflowActivity<JobOfferProcess, SubmitCandidacyInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isApproved() && jobOffer.isInCandidacyEvaluationPeriod()
		&& jobOffer.getHasAllNeededInfoForSubmitCancidacy() && !jobOffer.hasCandidacy(user);
    }

    @Override
    protected void process(SubmitCandidacyInformation activityInformation) {
	Person person = UserView.getCurrentUser().getPerson();
	JobOfferCandidacy jobOfferCandidacy = new JobOfferCandidacy(person.getPersonalPortfolio().getLastPersonalPortfolioInfo(),
		activityInformation.getFiles());
	activityInformation.getProcess().getJobOffer().addJobOfferCandidacy(jobOfferCandidacy);
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new SubmitCandidacyInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
