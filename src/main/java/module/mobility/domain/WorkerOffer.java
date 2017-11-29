/*
 * @(#)WorkerOffer.java
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.security.Authenticate;
import org.joda.time.DateTime;

import module.organization.domain.Person;
import module.workflow.domain.LabelLog;
import module.workflow.domain.ProcessFile;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class WorkerOffer extends WorkerOffer_Base implements Comparable<WorkerOffer> {

    @Override
    public int compareTo(final WorkerOffer offer) {
        final DateTime creationDate = offer.getCreationDate();
        final int c = getCreationDate().compareTo(creationDate);
        return c == 0 ? hashCode() - offer.hashCode() : c;
    }

    public WorkerOffer(final PersonalPortfolio personalPortfolio, final int year, final DateTime begin, final DateTime end,
            List<ProcessFile> processFiles) {
        super();
        setCanceled(Boolean.FALSE);
        setMobilitySystem(MobilitySystem.getInstance());
        setMobilityYear(MobilityYear.findMobilityYear(year));
        setCreationDate(new DateTime());
        final PersonalPortfolioInfo personalPortfolioInfo = personalPortfolio.getLastPersonalPortfolioInfo();
        if (personalPortfolioInfo == null) {
            throw new NullPointerException("no.personalPortfolioInfo");
        }
        setPersonalPortfolioInfo(personalPortfolioInfo);
        WorkerOfferProcess workerOfferProcess = new WorkerOfferProcess(this);
        setBeginDate(begin);
        setEndDate(end);

        setDisplayPhoto(Boolean.FALSE);
        setDisplayName(Boolean.FALSE);
        setDisplayDateOfBirth(Boolean.FALSE);
        setDisplayCarrer(Boolean.FALSE);
        setDisplayCategory(Boolean.FALSE);
        new LabelLog(workerOfferProcess, "activity.CreateWorkerJobOffer", "resources.MobilityResources");
        for (ProcessFile processFile : processFiles) {
            try {
                Constructor<? extends ProcessFile> fileConstructor =
                        processFile.getClass().getConstructor(String.class, String.class, byte[].class);
                ProcessFile file =
                        fileConstructor.newInstance(new Object[] { processFile.getDisplayName(), processFile.getFilename(),
                                processFile.getContent() });
                getWorkerOfferProcess().addFiles(file);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Person getOwner() {
        return super.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson();
    }

    public boolean isActive() {
        return !getCanceled() && (getEndDate() != null && getEndDate().isAfterNow()) && getBeginDate().isBeforeNow();
    }

    public boolean isActiveOrPendingApproval() {
        return !getCanceled() && (getEndDate() == null || getEndDate().isAfterNow()) || isPendingApproval();
    }

    public boolean isPendingApproval() {
        return !getCanceled() && getSubmittedForApprovalDate() != null && getApprovalDate() == null;
    }

    public boolean isUnderConstruction() {
        return !getCanceled() && getSubmittedForApprovalDate() == null;
    }

    public boolean isUnderConstruction(User user) {
        return !getCanceled() && getOwner().equals(user.getPerson()) && getSubmittedForApprovalDate() == null;
    }

    public boolean isPendingApproval(User user) {
        return (MobilitySystem.getInstance().isManagementMember(user) || getOwner().equals(user.getPerson()))
                && isPendingApproval();
    }

    public boolean isApproved(User user) {
        return isApproved() && (MobilitySystem.getInstance().isManagementMember(user));
    }

    public boolean isApproved() {
        return !getCanceled() && getApprovalDate() != null;
    }

    public void approve() {
        setApprovalDate(new DateTime());
        setWorkerOfferApproverPerson(Authenticate.getUser().getPerson());
    }

    @Deprecated
    public boolean hasBeginDate() {
        return getBeginDate() != null;
    }

    @Deprecated
    public boolean hasEndDate() {
        return getEndDate() != null;
    }

    @Deprecated
    public boolean hasCreationDate() {
        return getCreationDate() != null;
    }

    @Deprecated
    public boolean hasSubmittedForApprovalDate() {
        return getSubmittedForApprovalDate() != null;
    }

    @Deprecated
    public boolean hasApprovalDate() {
        return getApprovalDate() != null;
    }

    @Deprecated
    public boolean hasCanceled() {
        return getCanceled() != null;
    }

    @Deprecated
    public boolean hasDisplayPhoto() {
        return getDisplayPhoto() != null;
    }

    @Deprecated
    public boolean hasDisplayName() {
        return getDisplayName() != null;
    }

    @Deprecated
    public boolean hasDisplayDateOfBirth() {
        return getDisplayDateOfBirth() != null;
    }

    @Deprecated
    public boolean hasDisplayCarrer() {
        return getDisplayCarrer() != null;
    }

    @Deprecated
    public boolean hasDisplayCategory() {
        return getDisplayCategory() != null;
    }

    @Deprecated
    public boolean hasDisplayQualifications() {
        return getDisplayQualifications() != null;
    }

    @Deprecated
    public boolean hasDisplayCurriculum() {
        return getDisplayCurriculum() != null;
    }

    @Deprecated
    public boolean hasPersonalPortfolioInfo() {
        return getPersonalPortfolioInfo() != null;
    }

    @Deprecated
    public boolean hasWorkerOfferApproverPerson() {
        return getWorkerOfferApproverPerson() != null;
    }

    @Deprecated
    public boolean hasWorkerOfferProcess() {
        return getWorkerOfferProcess() != null;
    }

    @Deprecated
    public boolean hasMobilityYear() {
        return getMobilityYear() != null;
    }

    @Deprecated
    public boolean hasMobilitySystem() {
        return getMobilitySystem() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.JobOffer> getJobOffer() {
        return getJobOfferSet();
    }

    @Deprecated
    public boolean hasAnyJobOffer() {
        return !getJobOfferSet().isEmpty();
    }

}
