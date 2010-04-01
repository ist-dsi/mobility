package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class CreateWorkerJobOffer extends WorkflowActivity<PersonalPortfolioProcess, WorkerJobOfferInformation> {

    @Override
    public boolean isActive(final PersonalPortfolioProcess process, final User user) {
	return user == process.getPersonalPortfolio().getPerson().getUser();
    }

    @Override
    protected void process(final WorkerJobOfferInformation workerJobOfferInformation) {
	final PersonalPortfolioProcess personalPortfolioProcess = workerJobOfferInformation.getProcess();
	final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
	new WorkerOffer(personalPortfolio, workerJobOfferInformation.getYear(), workerJobOfferInformation.getBeginDate(), workerJobOfferInformation.getEndDate());
    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new WorkerJobOfferInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
