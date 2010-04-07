package module.mobility.domain;

import myorg.domain.util.Money;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class PersonalPortfolioInfo extends PersonalPortfolioInfo_Base implements Comparable<PersonalPortfolioInfo> {

    public PersonalPortfolioInfo(final PersonalPortfolio personalPortfolio) {
	super();
	setMobilitySystem(MobilitySystem.getInstance());
	setPersonalPortfolio(personalPortfolio);
	final DateTime now = new DateTime();
	setCreationDate(now);
	setModificationDate(now);
    }

    public PersonalPortfolioInfo(final PersonalPortfolio personalPortfolio, final MultiLanguageString carrer,
	    final MultiLanguageString category, final Money salary) {
	this(personalPortfolio);
	edit(carrer, category, salary);
    }

    public void edit(final MultiLanguageString carrer, final MultiLanguageString category, final Money salary) {
	setModificationDate(new DateTime());
	setCarrer(carrer);
	setCategory(category);
	setSalary(salary);
    }

    @Override
    public int compareTo(final PersonalPortfolioInfo personalPortfolioInfo) {
	return personalPortfolioInfo == null ? 1 : getCreationDate().compareTo(personalPortfolioInfo.getCreationDate());
    }

    public boolean canBeUpdated() {
	return !hasAnyWorkerOffer() && !hasAnyJobOffer();
    }

    public PersonalPortfolioInfo duplicate() {
	final PersonalPortfolioInfo personalPortfolioInfo = new PersonalPortfolioInfo(getPersonalPortfolio(), getCarrer(), getCategory(), getSalary());
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
