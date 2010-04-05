package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class UpdatePersonalPortfolioInfo extends WorkflowActivity<PersonalPortfolioProcess, PersonalPortfolioInfoInformation> {

    @Override
    public boolean isActive(final PersonalPortfolioProcess process, final User user) {
	return user == process.getPersonalPortfolio().getPerson().getUser() && process.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    @Override
    protected void process(final PersonalPortfolioInfoInformation information) {
	final PersonalPortfolioProcess personalPortfolioProcess = information.getProcess();
	final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
	final PersonalPortfolioInfo personalPortfolioInfo = personalPortfolio.getLastPersonalPortfolioInfo();
	if (personalPortfolioInfo.isUsedInAnyClosedWorkerOffers()) {
	    new PersonalPortfolioInfo(personalPortfolio, information.getCarrer(), information.getCategory(), information.getSalary());
	    // TODO : transfer all active offers to the new personal info...
	} else {
	    personalPortfolioInfo.edit(information.getCarrer(), information.getCategory(), information.getSalary());
	}
	information.updateQualifications(personalPortfolioInfo);
    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new PersonalPortfolioInfoInformation(process, this);
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
