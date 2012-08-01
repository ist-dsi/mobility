/*
 * @(#)DefineNewPersonalPortfolioInfo.java
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
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class DefineNewPersonalPortfolioInfo extends WorkflowActivity<PersonalPortfolioProcess, PersonalPortfolioInfoInformation> {

    @Override
    public boolean isActive(final PersonalPortfolioProcess process, final User user) {
	return user == process.getPersonalPortfolio().getPerson().getUser()
		&& !process.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    @Override
    protected void process(final PersonalPortfolioInfoInformation information) {
	final PersonalPortfolioProcess personalPortfolioProcess = information.getProcess();
	final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
	new PersonalPortfolioInfo(personalPortfolio, information.getCarrer(), information.getCategory());
    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new PersonalPortfolioInfoInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
