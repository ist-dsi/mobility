/*
 * @(#)PersonalPortfolio.java
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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import module.organization.domain.Accountability;
import module.organization.domain.Party;
import module.organization.domain.Person;
import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class PersonalPortfolio extends PersonalPortfolio_Base {

    public PersonalPortfolio(final Person person) {
        super();
        setMobilitySystem(MobilitySystem.getInstance());
        setPerson(person);
        setNotificationService(isEmployee(person));
        new PersonalPortfolioProcess(this);
    }

    private Boolean isEmployee(Person person) {
        for (Accountability accountability : person.getParentAccountabilities(MobilitySystem.getInstance().getEmployeeAccountabilityType())) {
            if (accountability.isActiveNow()) {
                return true;
            }
        }
        return false;
    }

    @Atomic
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

    public void addCurriculum(final PersonalPortfolioCurriculum personalPortfolioCurriculum) {
        PersonalPortfolioInfo personalPortfolioInfo = getLastPersonalPortfolioInfo();
        if (personalPortfolioInfo == null) {
            throw new NullPointerException("no.personalPortfolioCurriculum");
        }
        if (!personalPortfolioInfo.canBeUpdated()) {
            personalPortfolioInfo = personalPortfolioInfo.duplicate();
        }
        personalPortfolioInfo.setPersonalPortfolioCurriculum(personalPortfolioCurriculum);
    }

    public String getEmail() {
        return getPerson().getUser().getProfile().getEmail();
    }

    public Collection<Party> getWorkingPlaces() {
        return getPerson().getParents(MobilitySystem.getInstance().getWorkerAccountabilityTypeSet());
    }

    public boolean hasAnyActiveWorkerOfferOrPendingApproval() {
        for (WorkerOffer workerOffer : getWorkerOffer()) {
            if (workerOffer.isActiveOrPendingApproval()) {
                return true;
            }
        }
        return false;
    }

    public Set<JobOfferProcess> getJobOffersWithCandidacies() {
        final Set<JobOfferProcess> jobOffersWithCandidacies = new TreeSet<JobOfferProcess>();
        for (final PersonalPortfolioInfo personalPortfolioInfo : getPersonalPortfolioInfoSet()) {
            for (JobOfferCandidacy jobOfferCandidacy : personalPortfolioInfo.getJobOfferCandidacySet()) {
                jobOffersWithCandidacies.add(jobOfferCandidacy.getJobOffer().getJobOfferProcess());
            }
        }
        return jobOffersWithCandidacies;
    }
    @Deprecated
    public boolean hasNotificationService() {
        return getNotificationService() != null;
    }

    @Deprecated
    public boolean hasPersonalPortfolioProcess() {
        return getPersonalPortfolioProcess() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.PersonalPortfolioInfo> getPersonalPortfolioInfo() {
        return getPersonalPortfolioInfoSet();
    }

    @Deprecated
    public boolean hasAnyPersonalPortfolioInfo() {
        return !getPersonalPortfolioInfoSet().isEmpty();
    }

    @Deprecated
    public boolean hasPerson() {
        return getPerson() != null;
    }

    @Deprecated
    public boolean hasMobilitySystem() {
        return getMobilitySystem() != null;
    }

}
