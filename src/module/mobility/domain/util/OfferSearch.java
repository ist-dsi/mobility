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

    private String processNumber;

    public String getProcessNumber() {
	return processNumber;
    }

    public void setProcessNumber(String processNumber) {
	this.processNumber = processNumber;
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

}
