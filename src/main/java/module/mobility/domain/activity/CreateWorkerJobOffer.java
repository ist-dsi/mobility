/*
 * @(#)CreateWorkerJobOffer.java
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
package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class CreateWorkerJobOffer extends WorkflowActivity<PersonalPortfolioProcess, WorkerJobOfferInformation> {

	@Override
	public boolean isActive(final PersonalPortfolioProcess process, final User user) {
		final PersonalPortfolio personalPortfolio = process.getPersonalPortfolio();
		return user == personalPortfolio.getPerson().getUser() && personalPortfolio.hasAnyPersonalPortfolioInfo()
				&& !personalPortfolio.hasAnyActiveWorkerOfferOrPendingApproval();
	}

	@Override
	protected void process(final WorkerJobOfferInformation information) {
		final PersonalPortfolioProcess personalPortfolioProcess = information.getProcess();
		final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
		final WorkerOffer workerOffer =
				new WorkerOffer(personalPortfolio, information.getYear(), information.getBeginDate().toDateTimeAtStartOfDay(),
						information.getEndDate().toDateTimeAtStartOfDay(), information.getFiles());

		// workerOffer.setDisplayPhoto(information.getDisplayPhoto());
		// workerOffer.setDisplayName(information.getDisplayName());
		// workerOffer.setDisplayDateOfBirth(information.getDisplayName());
		//
		// workerOffer.setDisplayCarrer(information.getDisplayCarrer());
		// workerOffer.setDisplayCategory(information.getDisplayCategory());

		workerOffer.setDisplayQualifications(information.getDisplayQualifications());
		// workerOffer.setDisplayCurriculum(information.getDisplayCurriculum());

		information.setWorkerOffer(workerOffer);
	}

	@Override
	public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
		return new WorkerJobOfferInformation(process, this);
	}

	@Override
	public String getUsedBundle() {
		return "resources/MobilityResources";
	}

	@Override
	public boolean isDefaultInputInterfaceUsed() {
		return false;
	}

}
