package module.mobility.domain.util;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public class OfferSearch implements Serializable {

    public enum OfferSearchOwner implements IPresentableEnum {
	ALL, MINE, WITH_MY_CANDIDACY;

	public String getQualifiedName() {
	    return this.getClass().getName() + "." + this.name();
	}

	@Override
	public String getLocalizedName() {
	    return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", getQualifiedName());
	}

    }

    public enum OfferSearchState implements IPresentableEnum {
	ALL, ACTIVE, INACTIVE;

	public String getQualifiedName() {
	    return this.getClass().getName() + "." + this.name();
	}

	@Override
	public String getLocalizedName() {
	    return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", getQualifiedName());
	}

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

    public JobOfferProcess getJobOfferProcess(User user) {
	if (!StringUtils.isEmpty(getProcessNumber())) {
	    for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
		if (jobOffer.getJobOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
		    return jobOffer.getJobOfferProcess().isAccessible(user) ? jobOffer.getJobOfferProcess() : null;
		}
	    }
	}
	return null;
    }

    public WorkerOfferProcess getWorkerOfferProcess(User user) {
	if (!StringUtils.isEmpty(getProcessNumber())) {
	    for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOffer()) {
		if (workerOffer.getWorkerOfferProcess().getProcessIdentification().equalsIgnoreCase(getProcessNumber())) {
		    return workerOffer.getWorkerOfferProcess();
		}
	    }
	}
	return null;
    }

    //
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
	    if (jobOffer.getJobOfferProcess().hasAnyAvailableActivity(user, false) && jobOffer.isActive()) {
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

    public Set<JobOfferProcess> doJobOfferSearch() {
	Set<JobOfferProcess> result = new TreeSet<JobOfferProcess>();
	User user = UserView.getCurrentUser();
	for (JobOffer jobOffer : MobilitySystem.getInstance().getJobOffer()) {
	    if (jobOffer.getJobOfferProcess().isAccessible(user) && isSatisfiedJobOfferOwner(jobOffer, user)
		    && isSatisfiedState(jobOffer.isActive(), user)
		    && isSatisfiedProcessNumber(jobOffer.getJobOfferProcess().getProcessIdentification())) {
		result.add(jobOffer.getJobOfferProcess());
	    }
	}
	return result;
    }

    public Set<WorkerOfferProcess> doWorkerOfferSearch() {
	Set<WorkerOfferProcess> result = new TreeSet<WorkerOfferProcess>();
	User user = UserView.getCurrentUser();
	for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOfferSet()) {
	    if (workerOffer.getWorkerOfferProcess().isAccessible(user) && isSatisfiedOwner(workerOffer.getOwner(), user)
		    && isSatisfiedState(workerOffer.isActive(), user)
		    && isSatisfiedProcessNumber(workerOffer.getWorkerOfferProcess().getProcessIdentification())) {
		result.add(workerOffer.getWorkerOfferProcess());
	    }
	}
	return result;
    }

    private boolean isSatisfiedProcessNumber(String processIdentification) {
	return StringUtils.isEmpty(getProcessNumber()) || processIdentification.contains(getProcessNumber());
    }

    private boolean isSatisfiedState(Boolean isActive, User user) {
	return getOfferSearchState().equals(OfferSearchState.ALL)
		|| (getOfferSearchState().equals(OfferSearchState.ACTIVE) && isActive)
		|| (getOfferSearchState().equals(OfferSearchState.INACTIVE) && !isActive);
    }

    private boolean isSatisfiedJobOfferOwner(JobOffer jobOffer, User user) {
	return isSatisfiedOwner(jobOffer.getOwner(), user)
		|| (getOfferSearchOwner().equals(OfferSearchOwner.WITH_MY_CANDIDACY) && jobOffer.hasCandidacy(user));
    }

    private boolean isSatisfiedOwner(Person offerPerson, User user) {
	return getOfferSearchOwner().equals(OfferSearchOwner.ALL)
		|| (getOfferSearchOwner().equals(OfferSearchOwner.MINE) && offerPerson.equals(user.getPerson()));
    }
}
