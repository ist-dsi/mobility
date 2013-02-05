/*
 * @(#)MobilityYear.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.domain;

import org.joda.time.DateTime;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class MobilityYear extends MobilityYear_Base {

    private MobilityYear(final int year) {
        super();
        if (findMobilityYearByYearAux(year) != null) {
            throw new Error("There can only be one! (MissionYear object for each year)");
        }
        setMobilitySystem(MobilitySystem.getInstance());
        setYear(new Integer(year));
        setJobOfferCounter(Integer.valueOf(0));
        setWorkerOfferCounter(Integer.valueOf(0));
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

    public Integer nextJobOfferNumber() {
        return getNextJobOfferNumber();
    }

    private Integer getNextJobOfferNumber() {
        setJobOfferCounter(getJobOfferCounter().intValue() + 1);
        return getJobOfferCounter();
    }

    public Integer nextWorkerOfferNumber() {
        return getNextWorkerOfferNumber();
    }

    private Integer getNextWorkerOfferNumber() {
        setWorkerOfferCounter(getWorkerOfferCounter().intValue() + 1);
        return getWorkerOfferCounter();
    }

    public static MobilityYear getCurrentYear() {
        final int year = new DateTime().getYear();
        return findMobilityYear(year);
    }

}
