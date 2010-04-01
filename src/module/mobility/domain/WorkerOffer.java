package module.mobility.domain;

import org.joda.time.DateTime;

public class WorkerOffer extends WorkerOffer_Base {
    
    public WorkerOffer(final PersonalPortfolio personalPortfolio, final int year, final DateTime begin, final DateTime end) {
        super();
        setMobilitySystem(MobilitySystem.getInstance());
        setMobilityYear(MobilityYear.findMobilityYear(year));
        setCreationDate(new DateTime());
        setPersonalPortfolio(personalPortfolio);
        new WorkerOfferProcess(this);
        setBeginDate(begin);
        setEndDate(end);
    }
    
}
