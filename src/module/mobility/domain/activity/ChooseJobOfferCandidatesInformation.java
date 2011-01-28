package module.mobility.domain.activity;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.PersonalPortfolioInfo;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class ChooseJobOfferCandidatesInformation extends ActivityInformation<JobOfferProcess> {
    private List<PersonalPortfolioInfo> selectedWorkers;

    public ChooseJobOfferCandidatesInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);

	JobOffer jobOffer = jobOfferProcess.getJobOffer();
	setSelectedWorkers(new ArrayList<PersonalPortfolioInfo>(jobOffer.getChosenCandidateSet()));
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public List<PersonalPortfolioInfo> getSelectedWorkers() {
	return selectedWorkers;
    }

    public void setSelectedWorkers(List<PersonalPortfolioInfo> selectedWorkers) {
	this.selectedWorkers = selectedWorkers;
    }
}
