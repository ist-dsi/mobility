package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.BundleUtil;

public class SubmitCandidacyActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", "activity." + getClass().getSimpleName());
    }

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.getIsApproved() && jobOffer.getHasAllNeededInfoForSubmitCancidacy() && !jobOffer.hasCandidacy(user);
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	Person person = UserView.getCurrentUser().getPerson();
	activityInformation.getProcess().getJobOffer().getCandidatePortfolio().add(person.getPersonalPortfolio());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

}
