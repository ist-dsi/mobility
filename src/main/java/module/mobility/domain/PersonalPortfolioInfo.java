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

    @Deprecated
    public boolean hasCreationDate() {
        return getCreationDate() != null;
    }

    @Deprecated
    public boolean hasModificationDate() {
        return getModificationDate() != null;
    }

    @Deprecated
    public boolean hasCarrer() {
        return getCarrer() != null;
    }

    @Deprecated
    public boolean hasCategory() {
        return getCategory() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.PersonalPortfolioInfoQualification> getPersonalPortfolioInfoQualification() {
        return getPersonalPortfolioInfoQualificationSet();
    }

    @Deprecated
    public boolean hasAnyPersonalPortfolioInfoQualification() {
        return !getPersonalPortfolioInfoQualificationSet().isEmpty();
    }

    @Deprecated
    public boolean hasPersonalPortfolioCurriculum() {
        return getPersonalPortfolioCurriculum() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.WorkerOffer> getWorkerOffer() {
        return getWorkerOfferSet();
    }

    @Deprecated
    public boolean hasAnyWorkerOffer() {
        return !getWorkerOfferSet().isEmpty();
    }

    @Deprecated
    public boolean hasPersonalPortfolio() {
        return getPersonalPortfolio() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.JobOffer> getSelectedJobOffer() {
        return getSelectedJobOfferSet();
    }

    @Deprecated
    public boolean hasAnySelectedJobOffer() {
        return !getSelectedJobOfferSet().isEmpty();
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.JobOfferCandidacy> getJobOfferCandidacy() {
        return getJobOfferCandidacySet();
    }

    @Deprecated
    public boolean hasAnyJobOfferCandidacy() {
        return !getJobOfferCandidacySet().isEmpty();
    }

    @Deprecated
    public boolean hasMobilitySystem() {
        return getMobilitySystem() != null;
    }

}
