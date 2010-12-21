package module.mobility.domain.activity;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class JobOfferSelectionInformation extends ActivityInformation<JobOfferProcess> {
    private List<WorkerOffer> candidateWorkerOffers;

    public JobOfferSelectionInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	setCandidateWorkerOffers(new ArrayList<WorkerOffer>(jobOfferProcess.getJobOffer().getCandidateWorkerOffer()));
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public List<WorkerOffer> getCandidateWorkerOffers() {
	return candidateWorkerOffers;
    }

    public void setCandidateWorkerOffers(List<WorkerOffer> candidateWorkerOffers) {
	this.candidateWorkerOffers = candidateWorkerOffers;
    }

}
