/*
 * @(#)SubmitCandidacyInformation.java
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
package module.mobility.domain.activity;

import java.util.List;

import module.mobility.domain.JobOfferCandidacy;
import module.mobility.domain.JobOfferProcess;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class SubmitCandidacyInformation extends ActivityInformation<JobOfferProcess> {
    private List<ProcessFile> files;

    public SubmitCandidacyInformation(final JobOfferProcess jobOfferProcess,
            WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
        super(jobOfferProcess, activity);
        Person person = UserView.getCurrentUser().getPerson();
        JobOfferCandidacy jobOfferCandidacy = getProcess().getJobOffer().getCandidacy(person);
        if (jobOfferCandidacy != null) {
            setFiles(jobOfferCandidacy.getCandidacyFile());
        }
    }

    @Override
    public boolean hasAllneededInfo() {
        return isForwardedFromInput() && (getProcess().getJobOffer().getOptionalDocuments() || !getFiles().isEmpty());
    }

    public List<ProcessFile> getFiles() {
        return files;
    }

    public void setFiles(List<ProcessFile> files) {
        this.files = files;
    }

}
