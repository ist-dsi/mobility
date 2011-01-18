package module.mobility.domain;

import module.organization.domain.Person;

public class JuryMember extends JuryMember_Base {

    public JuryMember(Person person, boolean isJuryPresident, JobOffer jobOffer) {
	super();
	setPerson(person);
	setJuryPresident(isJuryPresident);
	setJobOffer(jobOffer);
    }

    public void delete() {
	removeJobOffer();
	removePerson();
	deleteDomainObject();
    }

    public boolean getHasPresidentDefined() {
	return true;
    }
}
