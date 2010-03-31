package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class JobOfferInformation extends ActivityInformation<JobOfferProcess> {
    private JobOffer jobOffer;

    public JobOfferInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
    }

    public JobOffer getJobOffer() {
	return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
	this.jobOffer = jobOffer;
    }

    @Override
    public boolean hasAllneededInfo() {
	return getJobOffer() != null;
    }
}
