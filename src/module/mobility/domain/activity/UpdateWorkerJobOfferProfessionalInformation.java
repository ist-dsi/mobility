package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class UpdateWorkerJobOfferProfessionalInformation extends WorkflowActivity<WorkerOfferProcess, ActivityInformation<WorkerOfferProcess>> {

    @Override
    public boolean isActive(final WorkerOfferProcess process, final User user) {
	final WorkerOffer workerOffer = process.getWorkerOffer();
	final PersonalPortfolioInfo personalPortfolioInfo = workerOffer.getPersonalPortfolioInfo();
	final PersonalPortfolio personalPortfolio = personalPortfolioInfo.getPersonalPortfolio();
	return user == personalPortfolio.getPerson().getUser()
		&& personalPortfolioInfo != personalPortfolio.getLastPersonalPortfolioInfo();
    }

    @Override
    protected void process(final ActivityInformation<WorkerOfferProcess> information) {
	final WorkerOffer workerOffer = information.getProcess().getWorkerOffer();
	final PersonalPortfolioInfo personalPortfolioInfo = workerOffer.getPersonalPortfolioInfo();
	final PersonalPortfolio personalPortfolio = personalPortfolioInfo.getPersonalPortfolio();
	workerOffer.setPersonalPortfolioInfo(personalPortfolio.getLastPersonalPortfolioInfo());
    }

    @Override
    public ActivityInformation<WorkerOfferProcess> getActivityInformation(final WorkerOfferProcess process) {
	return new ActivityInformation<WorkerOfferProcess>(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
