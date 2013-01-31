/*
 * @(#)JobOfferCandidacy.java
 *
 * Copyright 2011 Instituto Superior Tecnico
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

import module.workflow.domain.ProcessFile;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class JobOfferCandidacy extends JobOfferCandidacy_Base {

	public JobOfferCandidacy(PersonalPortfolioInfo personalPortfolioInfo, List<ProcessFile> candidacyFiles) {
		super();
		setPersonalPortfolioInfo(personalPortfolioInfo);
		if (candidacyFiles != null) {
			getCandidacyFileSet().addAll(candidacyFiles);
		}
	}

	public boolean isSelectedCandidacy() {
		return getJobOffer().getChosenCandidate().contains(getPersonalPortfolioInfo());
	}

	public void delete() {
		removePersonalPortfolioInfo();
		removeJobOffer();
		for (; getCandidacyFileCount() != 0; getCandidacyFileIterator().next().removeJobOfferCandidacy(this)) {
			;
		}

		deleteDomainObject();
	}

}
