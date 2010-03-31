package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

import org.joda.time.DateTime;

public class CreateJobOfferActivity extends WorkflowActivity<JobOfferProcess, JobOfferInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.getIsPendingConfirmation(user);
    }

    @Override
    protected void process(JobOfferInformation activityInformation) {
	activityInformation.getJobOffer().setConfirmationDate(new DateTime());
    }

}
