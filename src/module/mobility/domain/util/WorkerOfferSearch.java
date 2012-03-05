/*
 * @(#)WorkerOfferSearch.java
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

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class WorkerOfferSearch implements Serializable {

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

    private String processNumber;

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

    public OfferSearchState getOfferSearchState() {
	return offerSearchState;
    }

    public void setOfferSearchState(OfferSearchState offerSearchState) {
	this.offerSearchState = offerSearchState;
    }

    public void init() {
	setOfferSearchOwner(OfferSearchOwner.ALL);
	setOfferSearchState(OfferSearchState.ALL);
    }
//
//    public OfferProcess getOfferProcess(User user) {
//	if (!StringUtils.isEmpty(getProcessNumber())) {
//	    for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
//		if (jobOffer.getJobOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
//		    return jobOffer.getJobOfferProcess().isAccessible(user) ? jobOffer.getJobOfferProcess() : null;
//		}
//	    }
//	    for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
//		if (workerOffer.getWorkerOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
//		    return workerOffer.getWorkerOfferProcess();
//		}
//	    }
//	}
//	return null;
//    }
//
//    public Set<JobOfferProcess> getJobOfferSet() {
//	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
//	    if (jobOffer.getCreator().equals(user.getPerson())) {
//		result.add(jobOffer.getJobOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    public Set<WorkerOfferProcess> getWorkerOfferSet() {
//	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
//	    if (workerOffer.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().equals(user.getPerson())) {
//		result.add(workerOffer.getWorkerOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    public Set<JobOfferProcess> getPendingApprovalJobOfferSet() {
//	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
//	    if (jobOffer.isPendingApproval(user)) {
//		result.add(jobOffer.getJobOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    public Set<WorkerOfferProcess> getPendingApprovalWorkerJobOfferSet() {
//	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
//	    if (workerOffer.isPendingApproval(user)) {
//		result.add(workerOffer.getWorkerOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    public Set<JobOfferProcess> doJobOfferSearch() {
//	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
//	    if (jobOffer.getJobOfferProcess().isAccessible(user) && isSatisfiedOwner(jobOffer, user)
//		    && isSatisfiedState(jobOffer, user) && isSatisfiedProcessNumber(jobOffer)) {
//		result.add(jobOffer.getJobOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    public Set<WorkerOfferProcess> doWorkerOfferSearch() {
//	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
//	User user = UserView.getCurrentUser();
//	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOfferSet()) {
//	    if (workerOffer.getWorkerOfferProcess().isAccessible(user) && isSatisfiedOwner(workerOffer, user)
//		    && isSatisfiedState(workerOffer, user) && isSatisfiedProcessNumber(workerOffer)) {
//		result.add(workerOffer.getWorkerOfferProcess());
//	    }
//	}
//	return result;
//    }
//
//    private boolean isSatisfiedProcessNumber(Offer offer) {
//	return StringUtils.isEmpty(getProcessNumber())
//		|| offer.getProcess().getProcessIdentification().contains(getProcessNumber());
//    }
//
//    private boolean isSatisfiedState(Offer offer, User user) {
//	return getOfferSearchState().equals(OfferSearchState.ALL)
//		|| (getOfferSearchState().equals(OfferSearchState.ACTIVE) && offer.isActive())
//		|| (getOfferSearchState().equals(OfferSearchState.INACTIVE) && !offer.isActive());
//    }
//
//    private boolean isSatisfiedOwner(Offer offer, User user) {
//	return getOfferSearchOwner().equals(OfferSearchOwner.ALL)
//		|| (getOfferSearchOwner().equals(OfferSearchOwner.MINE) && offer.getOwner().equals(user.getPerson()))
//		|| (offer instanceof JobOffer && getOfferSearchOwner().equals(OfferSearchOwner.WITH_MY_CANDIDACY) && ((JobOffer) offer)
//			.hasCandidacy(user));
//    }
}
