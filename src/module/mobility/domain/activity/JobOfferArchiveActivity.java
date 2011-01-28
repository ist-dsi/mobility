package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

import org.joda.time.DateTime;

public class JobOfferArchiveActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isConcluded() && jobOffer.getArquivedDate() == null
		&& MobilitySystem.getInstance().isManagementMember(user);
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	JobOffer jobOffer = activityInformation.getProcess().getJobOffer();
	DateTime arquivedDate = new DateTime();
	jobOffer.setArquivedDate(arquivedDate);
	for (PersonalPortfolioInfo personalPortfolioInfo : jobOffer.getChosenCandidateSet()) {
	    for (WorkerOffer workerOffer : personalPortfolioInfo.getPersonalPortfolio().getWorkerOffer()) {
		if (workerOffer.isActive()) {
		    workerOffer.setCanceled(true);
		}
	    }

	}
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return "resources/MobilityResources";
    }

}
