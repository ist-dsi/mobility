package module.mobility.domain;

import org.joda.time.DateTime;

public class PersonalPortfolioInfo extends PersonalPortfolioInfo_Base implements Comparable<PersonalPortfolioInfo> {

    public PersonalPortfolioInfo(final PersonalPortfolio personalPortfolio) {
	super();
	setMobilitySystem(MobilitySystem.getInstance());
	setPersonalPortfolio(personalPortfolio);
	final DateTime now = new DateTime();
	setCreationDate(now);
	setModificationDate(now);
    }

    public PersonalPortfolioInfo(final PersonalPortfolio personalPortfolio, final CareerType carrer, final String category) {
	this(personalPortfolio);
	edit(carrer, category);
    }

    public void edit(final CareerType carrer, final String category) {
	setModificationDate(new DateTime());
	setCarrer(carrer);
	setCategory(category);
    }

    @Override
    public int compareTo(final PersonalPortfolioInfo personalPortfolioInfo) {
	return personalPortfolioInfo == null ? 1 : getCreationDate().compareTo(personalPortfolioInfo.getCreationDate());
    }

    public boolean canBeUpdated() {
	return !hasAnyWorkerOffer() && !hasAnyJobOfferCandidacy();
    }

    public PersonalPortfolioInfo duplicate() {
	final PersonalPortfolioInfo personalPortfolioInfo = new PersonalPortfolioInfo(getPersonalPortfolio(), getCarrer(),
		getCategory());
	personalPortfolioInfo.setPersonalPortfolioCurriculum(getPersonalPortfolioCurriculum());
	return personalPortfolioInfo;
    }

    @Override
    public void setPersonalPortfolioCurriculum(PersonalPortfolioCurriculum personalPortfolioCurriculum) {
	super.setPersonalPortfolioCurriculum(personalPortfolioCurriculum);
	if (personalPortfolioCurriculum != null) {
	    setModificationDate(new DateTime());
	}
    }

}
