package module.mobility.domain;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

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

    public PersonalPortfolioInfo getLastPersonalPortfolioInfo() {
	return hasAnyPersonalPortfolioInfo() ? Collections.max(getPersonalPortfolioInfoSet()) : null;
    }

    public Set<WorkerOffer> getWorkerOffer() {
	final Set<WorkerOffer> workerOffers = new TreeSet<WorkerOffer>();
	for (final PersonalPortfolioInfo personalPortfolioInfo : getPersonalPortfolioInfoSet()) {
	    workerOffers.addAll(personalPortfolioInfo.getWorkerOfferSet());
	}
	return workerOffers;
    }

}
