/*
 * @(#)EditWorkerJobOffer.java
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

import pt.ist.bennu.core.domain.User;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class EditWorkerJobOffer extends WorkflowActivity<WorkerOfferProcess, EditWorkerJobOfferInformation> {

    @Override
    public boolean isActive(final WorkerOfferProcess process, final User user) {
	final PersonalPortfolio personalPortfolio = process.getWorkerOffer().getPersonalPortfolioInfo().getPersonalPortfolio();
	return process.getWorkerOffer().isUnderConstruction(user) && personalPortfolio.hasAnyPersonalPortfolioInfo();
    }

    @Override
    protected void process(final EditWorkerJobOfferInformation information) {
	final WorkerOffer workerOffer = information.getProcess().getWorkerOffer();
	workerOffer.setBeginDate(information.getBeginDate().toDateTimeAtStartOfDay());
	workerOffer.setEndDate(information.getEndDate().toDateTimeAtStartOfDay());

	workerOffer.setDisplayPhoto(information.getDisplayPhoto());
	workerOffer.setDisplayName(information.getDisplayName());
	workerOffer.setDisplayDateOfBirth(information.getDisplayDateOfBirth());

	workerOffer.setDisplayCarrer(information.getDisplayCarrer());
	workerOffer.setDisplayCategory(information.getDisplayCategory());
	workerOffer.setDisplayQualifications(information.getDisplayQualifications());
	workerOffer.setDisplayCurriculum(information.getDisplayCurriculum());
    }

    @Override
    public ActivityInformation<WorkerOfferProcess> getActivityInformation(final WorkerOfferProcess process) {
	return new EditWorkerJobOfferInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
