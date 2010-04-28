package module.mobility.domain.util;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.apache.commons.lang.StringUtils;

public class OfferSearch implements Serializable {

    public enum OfferSearchOwner {
	ALL, MINE, WITH_MY_CANDIDACY;
    }

    public enum OfferSearchState {
	ALL, ACTIVE, INACTIVE;
    }

    private OfferSearchOwner offerSearchOwner;

    private OfferSearchState offerSearchState;

    private String processNumber;

    public String getProcessNumber() {
	return processNumber;
    }

    public void setProcessNumber(String processNumber) {
	this.processNumber = processNumber;
    }

    public OfferSearchOwner getOfferSearchOwner() {
	return offerSearchOwner;
    }

    public void setOfferSearchOwner(OfferSearchOwner offerSearchOwner) {
	this.offerSearchOwner = offerSearchOwner;
    }

    public OfferSearchState getOfferSearchState() {
	return offerSearchState;
    }

    public void setOfferSearchState(OfferSearchState offerSearchState) {
	this.offerSearchState = offerSearchState;
    }

    public void init() {
	setOfferSearchOwner(OfferSearchOwner.ALL);
	setOfferSearchState(OfferSearchState.ALL);
    }

    public WorkflowProcess getOfferProcess(User user) {
	if (!StringUtils.isEmpty(getProcessNumber())) {
	    for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
		if (jobOffer.getJobOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
		    return jobOffer.getJobOfferProcess().isAccessible(user) ? jobOffer.getJobOfferProcess() : null;
		}
	    }
	    for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
		if (workerOffer.getWorkerOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
		    return workerOffer.getWorkerOfferProcess();
		}
	    }
	}
	return null;
    }

    public Set<JobOfferProcess> getJobOfferSet() {
	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
	User user = UserView.getCurrentUser();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
	    if (jobOffer.getCreator().equals(user.getPerson())) {
		result.add(jobOffer.getJobOfferProcess());
	    }
	}
	return result;
    }

    public Set<WorkerOfferProcess> getWorkerOfferSet() {
	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
	User user = UserView.getCurrentUser();
	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
	    if (workerOffer.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().equals(user.getPerson())) {
		result.add(workerOffer.getWorkerOfferProcess());
	    }
	}
	return result;
    }

    public Set<JobOfferProcess> getPendingApprovalJobOfferSet() {
	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
	User user = UserView.getCurrentUser();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
	    if (jobOffer.isPendingApproval(user)) {
		result.add(jobOffer.getJobOfferProcess());
	    }
	}
	return result;
    }

    public Set<WorkerOfferProcess> getPendingApprovalWorkerJobOfferSet() {
	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
	User user = UserView.getCurrentUser();
	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
	    if (workerOffer.isPendingApproval(user)) {
		result.add(workerOffer.getWorkerOfferProcess());
	    }
	}
	return result;
    }

    public Set<JobOfferProcess> doSearch() {
	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
	User user = UserView.getCurrentUser();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
	    if (jobOffer.getJobOfferProcess().isAccessible(user) && isSatisfiedOwner(jobOffer, user)
		    && isSatisfiedState(jobOffer, user) && isSatisfiedProcessNumber(jobOffer)) {
		result.add(jobOffer.getJobOfferProcess());
	    }
	}
	return result;
    }

    private boolean isSatisfiedProcessNumber(JobOffer jobOffer) {
	return StringUtils.isEmpty(getProcessNumber())
		|| jobOffer.getJobOfferProcess().getProcessIdentification().contains(getProcessNumber());
    }

    private boolean isSatisfiedState(JobOffer jobOffer, User user) {
	return getOfferSearchState().equals(OfferSearchState.ALL)
		|| (getOfferSearchState().equals(OfferSearchState.ACTIVE) && jobOffer.getJobOfferProcess().isActive())
		|| (getOfferSearchState().equals(OfferSearchState.INACTIVE) && !jobOffer.getJobOfferProcess().isActive());
    }

    private boolean isSatisfiedOwner(JobOffer jobOffer, User user) {
	return getOfferSearchOwner().equals(OfferSearchOwner.ALL)
		|| (getOfferSearchOwner().equals(OfferSearchOwner.MINE) && jobOffer.getCreator().equals(user.getPerson()))
		|| (getOfferSearchOwner().equals(OfferSearchOwner.WITH_MY_CANDIDACY) && jobOffer.hasCandidacy(user));
    }
}
