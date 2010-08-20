package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class DisableNotificationServiceActivity extends
	WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> {
    @Override
    public boolean isActive(final PersonalPortfolioProcess process, User user) {
	return process.getPersonalPortfolio().getNotificationService();
    }

    @Override
    protected void process(ActivityInformation<PersonalPortfolioProcess> activityInformation) {
	activityInformation.getProcess().getPersonalPortfolio().setNotificationService(Boolean.FALSE);

    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
