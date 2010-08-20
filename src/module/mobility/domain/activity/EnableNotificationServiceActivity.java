package module.mobility.domain.activity;

import java.util.ResourceBundle;

import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;

import org.apache.commons.lang.StringUtils;

import pt.utl.ist.fenix.tools.util.i18n.Language;

public class EnableNotificationServiceActivity extends
	WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> {
    @Override
    public boolean isActive(final PersonalPortfolioProcess process, User user) {
	return !process.getPersonalPortfolio().getNotificationService();
    }

    @Override
    protected void process(ActivityInformation<PersonalPortfolioProcess> activityInformation) {
	if (StringUtils.isEmpty(activityInformation.getProcess().getPersonalPortfolio().getEmail())) {
	    throw new DomainException("message.mobility.empty.email", ResourceBundle.getBundle(getUsedBundle(), Language
		    .getLocale()));
	}
	activityInformation.getProcess().getPersonalPortfolio().setNotificationService(Boolean.TRUE);

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
