package module.mobility.domain;

import module.organization.domain.Person;
import pt.ist.fenixWebFramework.services.Service;

public class PersonalPortfolio extends PersonalPortfolio_Base {
    
    public PersonalPortfolio(final Person person) {
        super();
        setMobilitySystem(MobilitySystem.getInstance());
        setPerson(person);
        new PersonalPortfolioProcess(this);
    }

    @Service
    public static PersonalPortfolio create(final Person person) {
	return new PersonalPortfolio(person);
    }
    
}
