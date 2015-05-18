package module.mobility.domain.activity;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.fenixedu.bennu.core.domain.User;

public class ChooseJobOfferSelectionActivity extends WorkflowActivity<JobOfferProcess, ChooseJobOfferSelectionInformation> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
        JobOffer jobOffer = process.getJobOffer();
        return jobOffer.isUnderSelectionEvaluation() && jobOffer.getOwner().equals(user.getPerson());
    }

    @Override
    protected void process(ChooseJobOfferSelectionInformation activityInformation) {
        activityInformation.getProcess().getJobOffer().getChosenCandidateSet().clear();
        activityInformation.getProcess().getJobOffer().getChosenCandidateSet()
                .addAll(activityInformation.getSelectedPersonalPortfolioInfo());
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
        return new ChooseJobOfferSelectionInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
        return "resources/MobilityResources";
    }

}
