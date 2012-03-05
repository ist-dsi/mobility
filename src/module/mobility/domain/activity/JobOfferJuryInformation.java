/*
 * @(#)JobOfferJuryInformation.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.domain.activity;

import java.util.HashSet;
import java.util.Set;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.JuryMember;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Susana Fernandes
 * 
 */
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
