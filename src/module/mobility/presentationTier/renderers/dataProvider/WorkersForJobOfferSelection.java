/*
 * @(#)WorkersForJobOfferSelection.java
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
package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferCandidacy;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.activity.ChooseJobOfferCandidatesInformation;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class WorkersForJobOfferSelection implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	List<PersonalPortfolioInfo> result = new ArrayList<PersonalPortfolioInfo>();

	ChooseJobOfferCandidatesInformation activityInformation = (ChooseJobOfferCandidatesInformation) arg0;

	JobOffer jobOffer = activityInformation.getProcess().getJobOffer();
	if (jobOffer.isInInternalRecruitment()) {
	    for (JobOfferCandidacy jobOfferCandidacy : jobOffer.getJobOfferCandidacy()) {
		result.add(jobOfferCandidacy.getPersonalPortfolioInfo());
	    }
	} else {
	    for (WorkerOffer workerOffer : jobOffer.getSelectedWorkerOfferCandidateSet()) {
		result.add(workerOffer.getPersonalPortfolioInfo());
	    }
	}

	return result;
    }

}
