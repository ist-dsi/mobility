package module.mobility.domain.activity;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.util.JobOfferBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class JobOfferInformation extends ActivityInformation<JobOfferProcess> {
    private JobOfferBean jobOfferBean;

    public JobOfferInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	setJobOfferBean(new JobOfferBean(jobOfferProcess.getJobOffer()));
    }

    @Override
    public boolean hasAllneededInfo() {
	return getProcess().getJobOffer() != null && isForwardedFromInput();
    }

    public JobOfferBean getJobOfferBean() {
	return jobOfferBean;
    }

    public void setJobOfferBean(JobOfferBean jobOfferBean) {
	this.jobOfferBean = jobOfferBean;
    }

}
