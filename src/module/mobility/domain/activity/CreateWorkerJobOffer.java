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
	final PersonalPortfolio personalPortfolio = process.getPersonalPortfolio();
	return user == personalPortfolio.getPerson().getUser() && personalPortfolio.hasAnyPersonalPortfolioInfo();
    }

    @Override
    protected void process(final WorkerJobOfferInformation information) {
	final PersonalPortfolioProcess personalPortfolioProcess = information.getProcess();
	final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
	final WorkerOffer workerOffer = new WorkerOffer(personalPortfolio, information.getYear(),
		information.getBeginDate(), information.getEndDate());

	workerOffer.setDisplayPhoto(information.getDisplayPhoto());
	workerOffer.setDisplayName(information.getDisplayName());
	workerOffer.setDisplayDateOfBirth(information.getDisplayName());

	workerOffer.setDisplayCarrer(information.getDisplayCarrer());
	workerOffer.setDisplayCategory(information.getDisplayCategory());
	workerOffer.setDisplaySalary(information.getDisplaySalary());

	workerOffer.setDisplayQualifications(information.getDisplayQualifications());
	workerOffer.setDisplayCurriculum(information.getDisplayCurriculum());
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
