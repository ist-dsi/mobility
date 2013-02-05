/*
 * @(#)JobOfferApprovalInformation.java
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
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class JobOfferApprovalInformation extends ActivityInformation<JobOfferProcess> {

    private DateTime publicationBeginDate;
    private DateTime publicationEndDate;

    public JobOfferApprovalInformation(final JobOfferProcess process,
            WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
        super(process, activity);
        final JobOffer jobOffer = process.getJobOffer();
        DateTime beginDate = jobOffer.getPublicationBeginDate();
        if (beginDate == null) {
            beginDate = new DateTime();
        }
        setPublicationBeginDate(beginDate);
        setPublicationEndDate(jobOffer.getPublicationEndDate());
    }

    @Override
    public boolean hasAllneededInfo() {
        return isForwardedFromInput() && publicationBeginDate != null && publicationEndDate != null
                && publicationBeginDate.isBefore(publicationEndDate);
    }

    public DateTime getPublicationBeginDate() {
        return publicationBeginDate;
    }

    public void setPublicationBeginDate(DateTime publicationBeginDate) {
        this.publicationBeginDate = publicationBeginDate;
    }

    public DateTime getPublicationEndDate() {
        return publicationEndDate;
    }

    public void setPublicationEndDate(DateTime publicationEndDate) {
        this.publicationEndDate = publicationEndDate;
    }

}
