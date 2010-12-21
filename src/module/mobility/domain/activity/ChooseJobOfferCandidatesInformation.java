package module.mobility.domain.activity;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class ChooseJobOfferCandidatesInformation extends ActivityInformation<JobOfferProcess> {
    private List<WorkerOffer> selectedWorkerOffers;

    public ChooseJobOfferCandidatesInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	setSelectedWorkerOffers(new ArrayList<WorkerOffer>(jobOfferProcess.getJobOffer().getSelectedWorkerOffer()));
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public List<WorkerOffer> getSelectedWorkerOffers() {
	return selectedWorkerOffers;
    }

    public void setSelectedWorkerOffers(List<WorkerOffer> selectedWorkerOffers) {
	this.selectedWorkerOffers = selectedWorkerOffers;
    }

}
