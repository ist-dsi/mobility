package module.mobility.domain;

public class PersonalPortfolioInfoQualification extends PersonalPortfolioInfoQualification_Base {
    
    public PersonalPortfolioInfoQualification() {
        super();
        setMobilitySystem(MobilitySystem.getInstance());
    }

    public PersonalPortfolioInfoQualification(final PersonalPortfolioInfo personalPortfolioInfo) {
	this();
	setPersonalPortfolioInfo(personalPortfolioInfo);
    }

    public void delete() {
	removePersonalPortfolioInfo();
	removeMobilitySystem();
	deleteDomainObject();
    }
    
}
