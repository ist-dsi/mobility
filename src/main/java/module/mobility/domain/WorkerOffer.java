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

import java.util.List;

import module.organization.domain.Person;
import module.workflow.domain.LabelLog;
import module.workflow.domain.ProcessFile;

import org.joda.time.DateTime;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;

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
		new LabelLog(workerOfferProcess, personalPortfolioInfo.getPersonalPortfolio().getPerson().getUser(),
				"activity.CreateWorkerJobOffer", "resources.MobilityResources");
		getWorkerOfferProcess().getFiles().addAll(processFiles);
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
		setWorkerOfferApproverPerson(UserView.getCurrentUser().getPerson());
	}

}
