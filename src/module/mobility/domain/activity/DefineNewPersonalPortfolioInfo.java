package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class DefineNewPersonalPortfolioInfo extends WorkflowActivity<PersonalPortfolioProcess, PersonalPortfolioInfoInformation> {

    @Override
    public boolean isActive(final PersonalPortfolioProcess process, final User user) {
	return user == process.getPersonalPortfolio().getPerson().getUser()
		&& !process.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    @Override
    protected void process(final PersonalPortfolioInfoInformation information) {
	final PersonalPortfolioProcess personalPortfolioProcess = information.getProcess();
	final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
	new PersonalPortfolioInfo(personalPortfolio, information.getCarrer(), information.getCategory());
    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new PersonalPortfolioInfoInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
