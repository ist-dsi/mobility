/*
 * @(#)DisableNotificationServiceActivity.java
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

import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class DisableNotificationServiceActivity extends
	WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> {
    @Override
    public boolean isActive(final PersonalPortfolioProcess process, User user) {
	return process.getPersonalPortfolio().getNotificationService();
    }

    @Override
    protected void process(ActivityInformation<PersonalPortfolioProcess> activityInformation) {
	activityInformation.getProcess().getPersonalPortfolio().setNotificationService(Boolean.FALSE);

    }

    @Override
    public ActivityInformation<PersonalPortfolioProcess> getActivityInformation(final PersonalPortfolioProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
