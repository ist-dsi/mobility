/*
 * @(#)OfferSearch.java
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
package module.mobility.domain.util;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.organization.domain.Person;

import org.apache.commons.lang.StringUtils;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class OfferSearch implements Serializable {

    public enum OfferSearchOwner implements IPresentableEnum {
        ALL, MINE, WITH_MY_CANDIDACY;

        public String getQualifiedName() {
            return this.getClass().getName() + "." + this.name();
        }

        @Override
        public String getLocalizedName() {
            return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", getQualifiedName());
        }

    }

    public enum OfferSearchState implements IPresentableEnum {
        ALL, ACTIVE, INACTIVE;

        public String getQualifiedName() {
            return this.getClass().getName() + "." + this.name();
        }

        @Override
        public String getLocalizedName() {
            return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", getQualifiedName());
        }

    }

    private OfferSearchOwner offerSearchOwner;

    private OfferSearchState offerSearchState;

    private MobilityProcessStage workerOfferProcessStage;

    private MobilityProcessStage mobilityProcessStage;

    private String processNumber;

    public OfferSearch() {
        setOfferSearchOwner(OfferSearchOwner.ALL);
        setOfferSearchState(OfferSearchState.ALL);
        if (MobilitySystem.getInstance().isManagementMember()) {
            setMobilityProcessStage(MobilityProcessStage.PUBLISHED);
        }
    }

    public OfferSearch(OfferSearchOwner offerSearchOwner, OfferSearchState offerSearchState) {
        setOfferSearchOwner(offerSearchOwner);
        setOfferSearchState(offerSearchState);
        if (MobilitySystem.getInstance().isManagementMember()) {
            setMobilityProcessStage(MobilityProcessStage.PUBLISHED);
        }
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public OfferSearchOwner getOfferSearchOwner() {
        return offerSearchOwner;
    }

    public void setOfferSearchOwner(OfferSearchOwner offerSearchOwner) {
        this.offerSearchOwner = offerSearchOwner;
    }

    public MobilityProcessStage getMobilityProcessStage() {
        return mobilityProcessStage;
    }

    public void setMobilityProcessStage(MobilityProcessStage mobilityProcessStage) {
        this.mobilityProcessStage = mobilityProcessStage;
    }

    public MobilityProcessStage getWorkerOfferProcessStage() {
        return workerOfferProcessStage;
    }

    public void setWorkerOfferProcessStage(MobilityProcessStage workerOfferProcessStage) {
        this.workerOfferProcessStage = workerOfferProcessStage;
    }

    public OfferSearchState getOfferSearchState() {
        return offerSearchState;
    }

    public void setOfferSearchState(OfferSearchState offerSearchState) {
        this.offerSearchState = offerSearchState;
    }

    public JobOfferProcess getJobOfferProcess(User user) {
        if (!StringUtils.isEmpty(getProcessNumber())) {
            for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
                if (jobOffer.getJobOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
                    return jobOffer.getJobOfferProcess().isAccessible(user) ? jobOffer.getJobOfferProcess() : null;
                }
            }
        }
        return null;
    }

    public WorkerOfferProcess getWorkerOfferProcess(User user) {
        if (!StringUtils.isEmpty(getProcessNumber())) {
            for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
                if (workerOffer.getWorkerOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
                    return workerOffer.getWorkerOfferProcess();
                }
            }
        }
        return null;
    }

    public Set<JobOfferProcess> getJobOfferSet() {
        Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
        User user = UserView.getCurrentUser();
        for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
            if (jobOffer.getCreator().equals(user.getPerson())) {
                result.add(jobOffer.getJobOfferProcess());
            }
        }
        return result;
    }

    public Set<WorkerOfferProcess> getWorkerOfferSet() {
        Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
        User user = UserView.getCurrentUser();
        for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
            if (workerOffer.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().equals(user.getPerson())) {
                result.add(workerOffer.getWorkerOfferProcess());
            }
        }
        return result;
    }

    public Set<JobOfferProcess> getPendingApprovalJobOfferSet() {
        Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
        User user = UserView.getCurrentUser();
        for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
            if (jobOffer.getJobOfferProcess().hasAnyAvailableActivity(user, false) && jobOffer.isActive()) {
                result.add(jobOffer.getJobOfferProcess());
            }
        }
        return result;
    }

    public Set<WorkerOfferProcess> getPendingApprovalWorkerJobOfferSet() {
        Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
        User user = UserView.getCurrentUser();
        for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
            if (workerOffer.isPendingApproval(user)) {
                result.add(workerOffer.getWorkerOfferProcess());
            }
        }
        return result;
    }

    public Set<JobOfferProcess> doJobOfferSearch() {
        Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
        User user = UserView.getCurrentUser();
        for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
            if ((jobOffer.getJobOfferProcess().isAccessible(user) || MobilitySystem.getInstance().isManagementMember(user))
                    && isSatisfiedJobOfferOwner(jobOffer, user) && isSatisfiedState(jobOffer)
                    && isSatisfiedState(jobOffer.isActive())
                    && isSatisfiedProcessNumber(jobOffer.getJobOfferProcess().getProcessIdentification())) {
                result.add(jobOffer.getJobOfferProcess());
            }
        }
        return result;
    }

    public Set<WorkerOfferProcess> doWorkerOfferSearch() {
        Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
        User user = UserView.getCurrentUser();
        for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOfferSet()) {
            if ((workerOffer.getWorkerOfferProcess().isAccessible(user) || MobilitySystem.getInstance().isManagementMember(user))
                    && isSatisfiedOwner(workerOffer.getOwner(), user) && isSatisfiedState(workerOffer)
                    && isSatisfiedProcessNumber(workerOffer.getWorkerOfferProcess().getProcessIdentification())) {
                result.add(workerOffer.getWorkerOfferProcess());
            }
        }
        return result;
    }

    private boolean isSatisfiedProcessNumber(String processIdentification) {
        return StringUtils.isEmpty(getProcessNumber()) || processIdentification.contains(getProcessNumber());
    }

    private boolean isSatisfiedState(Boolean isActive) {
        return getOfferSearchState().equals(OfferSearchState.ALL)
                || (getOfferSearchState().equals(OfferSearchState.ACTIVE) && isActive)
                || (getOfferSearchState().equals(OfferSearchState.INACTIVE) && !isActive);
    }

    private boolean isSatisfiedState(JobOffer jobOffer) {
        if (getMobilityProcessStage() == null) {
            return true;
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.UNDER_CONSTRUCTION)) {
            return jobOffer.isUnderConstruction();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.SELECTION)) {
            return jobOffer.isPendingSelection();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.EVALUATION)) {
            return jobOffer.isUnderSelectionEvaluation();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.CONCLUDED)) {
            return jobOffer.isConcluded() && !jobOffer.isArchived();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.JURY_DEFINITION)) {
            return jobOffer.isPendingJuryDefinition();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.PENDING_PUBLISHMENT)) {
            return jobOffer.isPendingApproval();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.PUBLISHED)) {
            return jobOffer.isInCandidacyPeriod();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.CANDIDACY_EVALUATION)) {
            return jobOffer.isUnderCandidacyEvaluation();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.ARCHIVED)) {
            return jobOffer.isArchived();
        }
        return false;
    }

    private boolean isSatisfiedState(WorkerOffer workerOffer) {
        if (getMobilityProcessStage() == null) {
            return true;
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.UNDER_CONSTRUCTION)) {
            return workerOffer.isUnderConstruction();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.PENDING_PUBLISHMENT)) {
            return workerOffer.isPendingApproval();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.PUBLISHED)) {
            return workerOffer.isApproved() && workerOffer.isActive();
        } else if (getMobilityProcessStage().equals(MobilityProcessStage.CONCLUDED)) {
            return workerOffer.isApproved() && !workerOffer.isActive();
        }
        return false;
    }

    private boolean isSatisfiedJobOfferOwner(JobOffer jobOffer, User user) {
        return isSatisfiedOwner(jobOffer.getOwner(), user)
                || (getOfferSearchOwner().equals(OfferSearchOwner.WITH_MY_CANDIDACY) && jobOffer.hasCandidacy(user));
    }

    private boolean isSatisfiedOwner(Person offerPerson, User user) {
        return getOfferSearchOwner().equals(OfferSearchOwner.ALL)
                || (getOfferSearchOwner().equals(OfferSearchOwner.MINE) && offerPerson.equals(user.getPerson()));
    }

}
