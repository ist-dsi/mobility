package module.mobility.domain.activity;

import java.util.HashSet;
import java.util.Set;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.JuryMember;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.fenixWebFramework.services.Service;

public class JobOfferJuryInformation extends ActivityInformation<JobOfferProcess> {
    private Set<JuryMember> juryMembers = new HashSet<JuryMember>();

    private Person personToAddToJury;

    public JobOfferJuryInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	getJuryMembers().addAll(jobOfferProcess.getJobOffer().getJuryMemberSet());
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public Set<JuryMember> getJuryMembers() {
	return juryMembers;
    }

    public void setJuryMembers(Set<JuryMember> juryMembers) {
	this.juryMembers = juryMembers;
    }

    public Person getPersonToAddToJury() {
	return personToAddToJury;
    }

    public void setPersonToAddToJury(Person personToAddToJury) {
	this.personToAddToJury = personToAddToJury;
    }

    @Service
    public void addJuryMember() {
	if (getPersonToAddToJury() != null && !containsJuryMember(getPersonToAddToJury())) {
	    juryMembers.add(new JuryMember(getPersonToAddToJury(), false, getProcess().getJobOffer()));
	}
	setPersonToAddToJury(null);
    }

    @Service
    public void removeJuryMember(JuryMember juryMember) {
	juryMembers.remove(juryMember);
	juryMember.delete();
    }

    private boolean containsJuryMember(Person person) {
	for (JuryMember juryMember : getJuryMembers()) {
	    if (juryMember.getPerson().equals(person)) {
		return true;
	    }
	}
	return false;
    }

    @Service
    public void setJuryPresident(JuryMember presidentJuryMember) {
	for (JuryMember juryMember : juryMembers) {
	    juryMember.setJuryPresident(juryMember.equals(presidentJuryMember));
	}
    }

    public boolean getHasPresidentDefined() {
	return true;
    }
}
