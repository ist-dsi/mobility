package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MinutesFile;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

import org.joda.time.DateTime;

public class JobOfferConclusionActivity extends WorkflowActivity<JobOfferProcess, JobOfferConclusionInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isPendingConclusion() && jobOffer.getOwner().equals(user.getPerson());
    }

    @Override
    protected void process(JobOfferConclusionInformation activityInformation) {
	byte[] fileContent = activityInformation.getBytes();
	JobOffer jobOffer = activityInformation.getProcess().getJobOffer();
	if (fileContent != null) {
	    new MinutesFile(jobOffer, activityInformation.getDisplayName(), activityInformation.getFilename(), fileContent);
	}
	jobOffer.setConclusionDate(new DateTime());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new JobOfferConclusionInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
