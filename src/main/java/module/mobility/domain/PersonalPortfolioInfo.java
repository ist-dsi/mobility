/*
 * @(#)PersonalPortfolioInfo.java
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
        final PersonalPortfolioInfo personalPortfolioInfo =
                new PersonalPortfolioInfo(getPersonalPortfolio(), getCarrer(), getCategory());
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
