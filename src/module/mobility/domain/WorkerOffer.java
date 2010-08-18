package module.mobility.domain;

import module.organization.domain.Person;
import module.workflow.domain.LabelLog;

import org.joda.time.DateTime;

public class WorkerOffer extends WorkerOffer_Base {

    public WorkerOffer(final PersonalPortfolio personalPortfolio, final int year, final DateTime begin, final DateTime end) {
	super();
	setMobilitySystem(MobilitySystem.getInstance());
	setMobilityYear(MobilityYear.findMobilityYear(year));
	setCreationDate(new DateTime());
	final PersonalPortfolioInfo personalPortfolioInfo = personalPortfolio.getLastPersonalPortfolioInfo();
	if (personalPortfolioInfo == null) {
	    throw new NullPointerException("no.personalPortfolioInfo");
	}
	setPersonalPortfolioInfo(personalPortfolioInfo);
	WorkerOfferProcess workerOfferProcess = new WorkerOfferProcess(this);
	setBeginDate(begin);
	setEndDate(end);

	setDisplayPhoto(Boolean.FALSE);
	setDisplayName(Boolean.FALSE);
	setDisplayDateOfBirth(Boolean.FALSE);
	setDisplayCarrer(Boolean.FALSE);
	setDisplayCategory(Boolean.FALSE);
	setDisplaySalary(Boolean.FALSE);
	new LabelLog(workerOfferProcess, personalPortfolioInfo.getPersonalPortfolio().getPerson().getUser(),
		"activity.CreateWorkerJobOffer", "resources.MobilityResources");
    }

    @Override
    public Person getOwner() {
	return super.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson();
    }

    @Override
    public OfferProcess getProcess() {
	return getWorkerOfferProcess();
    }

}
