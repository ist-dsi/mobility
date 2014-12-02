/*
 * @(#)UpdateWorkerJobOfferProfessionalInformation.java
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
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.fenixedu.bennu.core.domain.User;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class UpdateWorkerJobOfferProfessionalInformation extends
        WorkflowActivity<WorkerOfferProcess, ActivityInformation<WorkerOfferProcess>> {

    @Override
    public boolean isActive(final WorkerOfferProcess process, final User user) {
        final WorkerOffer workerOffer = process.getWorkerOffer();
        final PersonalPortfolioInfo personalPortfolioInfo = workerOffer.getPersonalPortfolioInfo();
        final PersonalPortfolio personalPortfolio = personalPortfolioInfo.getPersonalPortfolio();
        return user == personalPortfolio.getPerson().getUser()
                && personalPortfolioInfo != personalPortfolio.getLastPersonalPortfolioInfo();
    }

    @Override
    protected void process(final ActivityInformation<WorkerOfferProcess> information) {
        final WorkerOffer workerOffer = information.getProcess().getWorkerOffer();
        final PersonalPortfolioInfo personalPortfolioInfo = workerOffer.getPersonalPortfolioInfo();
        final PersonalPortfolio personalPortfolio = personalPortfolioInfo.getPersonalPortfolio();
        workerOffer.setPersonalPortfolioInfo(personalPortfolio.getLastPersonalPortfolioInfo());
    }

    @Override
    public ActivityInformation<WorkerOfferProcess> getActivityInformation(final WorkerOfferProcess process) {
        return new ActivityInformation<WorkerOfferProcess>(process, this);
    }

    @Override
    public String getUsedBundle() {
        return "resources/MobilityResources";
    }

}
