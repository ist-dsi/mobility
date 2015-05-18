package module.mobility.domain.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class ChooseJobOfferSelectionInformation extends ActivityInformation<JobOfferProcess> {
    private List<WorkerOffer> selectedWorkers = new ArrayList<WorkerOffer>();

    public ChooseJobOfferSelectionInformation(final JobOfferProcess jobOfferProcess,
            WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
        super(jobOfferProcess, activity);
        JobOffer jobOffer = jobOfferProcess.getJobOffer();
        selectedWorkers.addAll(jobOffer.getSelectedWorkerOfferCandidateSet().stream()
                .filter(wo -> jobOffer.getChosenCandidateSet().contains(wo.getPersonalPortfolioInfo()))
                .collect(Collectors.toList()));

    }

    @Override
    public boolean hasAllneededInfo() {
        return isForwardedFromInput();
    }

    public List<WorkerOffer> getSelectedWorkers() {
        return selectedWorkers;
    }

    public void setSelectedWorkers(List<WorkerOffer> selectedWorkers) {
        this.selectedWorkers = selectedWorkers;
    }

    public void setSelectedWorkers(Set<WorkerOffer> selectedWorkers) {
        this.selectedWorkers = new ArrayList<WorkerOffer>(selectedWorkers);
    }

    public List<PersonalPortfolioInfo> getSelectedPersonalPortfolioInfo() {
        return getSelectedWorkers().stream().map(WorkerOffer::getPersonalPortfolioInfo).collect(Collectors.toList());
    }
}
