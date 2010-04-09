package module.mobility.domain;

import module.organization.domain.Person;

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
	new WorkerOfferProcess(this);
	setBeginDate(begin);
	setEndDate(end);

	setDisplayPhoto(Boolean.FALSE);
	setDisplayName(Boolean.FALSE);
	setDisplayDateOfBirth(Boolean.FALSE);
	setDisplayCarrer(Boolean.FALSE);
	setDisplayCategory(Boolean.FALSE);
	setDisplaySalary(Boolean.FALSE);
    }

    @Override
    protected Person getOwner() {
	return super.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson();
    }

    public WorkerOfferProcess getOfferProcess() {
	return super.getWorkerOfferProcess();
    }

}
