package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.util.BundleUtil;

import org.joda.time.DateTime;

public class SubmitForApprovalActivity extends WorkflowActivity<JobOfferProcess, JobOfferInformation> {

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", "activity." + getClass().getSimpleName());
    }

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.getIsPendingApproval(user);
    }

    @Override
    protected void process(JobOfferInformation activityInformation) {
	activityInformation.getProcess().getJobOffer().setSubmittedForApprovalDate(new DateTime());
    }

}
