/*
 * @(#)JobOfferApprovalActivity.java
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

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class JobOfferApprovalActivity extends WorkflowActivity<JobOfferProcess, JobOfferApprovalInformation> {

	@Override
	public boolean isActive(JobOfferProcess process, User user) {
		JobOffer jobOffer = process.getJobOffer();
		return jobOffer.isPendingApproval(user) && MobilitySystem.getInstance().isManagementMember(user);
	}

	@Override
	protected void process(JobOfferApprovalInformation activityInformation) {
		activityInformation.getProcess().getJobOffer()
				.approve(activityInformation.getPublicationBeginDate(), activityInformation.getPublicationEndDate());
	}

	@Override
	public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
		return new JobOfferApprovalInformation(process, this);
	}

	@Override
	public String getUsedBundle() {
		return "resources/MobilityResources";
	}
}
