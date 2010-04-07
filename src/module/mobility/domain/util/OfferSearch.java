package module.mobility.domain.util;

import java.io.Serializable;

import module.mobility.domain.JobOffer;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.WorkerOffer;
import module.workflow.domain.WorkflowProcess;
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

}
