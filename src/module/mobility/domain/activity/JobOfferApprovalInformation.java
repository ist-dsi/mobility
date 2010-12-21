package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

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
