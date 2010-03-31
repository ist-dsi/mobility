package module.mobility.domain;

import org.joda.time.DateTime;

public class MobilityYear extends MobilityYear_Base {
    
    private MobilityYear(final int year) {
        super();
        if (findMobilityYearByYearAux(year) != null) {
            throw new Error("There can only be one! (MissionYear object for each year)");
        }
        setMobilitySystem(MobilitySystem.getInstance());
        setYear(new Integer(year));
        setCounter(Integer.valueOf(0));
    }

    private static MobilityYear findMobilityYearByYearAux(final int year) {
	final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
	for (final MobilityYear mobilityYear : mobilitySystem.getMobilityYearSet()) {
	    if (mobilityYear.getYear().intValue() == year) {
		return mobilityYear;
	    }
	}
	return null;
    }

    public static MobilityYear findMobilityYear(final int year) {
	final MobilityYear mobilityYear = findMobilityYearByYearAux(year);
	return mobilityYear == null ? new MobilityYear(year) : mobilityYear;
    }

    public Integer nextNumber() {
	return getNextNumber();
    }

    private Integer getNextNumber() {
	setCounter(getCounter().intValue() + 1);
	return getCounter();
    }

    public static MobilityYear getCurrentYear() {
	final int year = new DateTime().getYear();
	return findMobilityYear(year);
    }
    
}
