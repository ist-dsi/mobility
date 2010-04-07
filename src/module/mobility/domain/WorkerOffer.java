package module.mobility.domain;

import org.joda.time.DateTime;

public class WorkerOffer extends WorkerOffer_Base implements Comparable<WorkerOffer> {

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
    public int compareTo(final WorkerOffer workerOffer) {
	final DateTime creationDate = workerOffer.getCreationDate();
	final int c = getCreationDate().compareTo(creationDate);
	return c == 0 ? hashCode() - workerOffer.hashCode() : c;
    }

}
