/*
 * @(#)JuryMember.java
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
package module.mobility.domain;

import module.organization.domain.Person;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class JuryMember extends JuryMember_Base {

    public JuryMember(Person person, boolean isJuryPresident, JobOffer jobOffer) {
        super();
        setPerson(person);
        setJuryPresident(isJuryPresident);
        setJobOffer(jobOffer);
    }

    public void delete() {
        setJobOffer(null);
        setPerson(null);
        deleteDomainObject();
    }

    public boolean getHasPresidentDefined() {
        return true;
    }

    @Deprecated
    public boolean hasJuryPresident() {
        return getJuryPresident() != null;
    }

    @Deprecated
    public boolean hasJobOffer() {
        return getJobOffer() != null;
    }

    @Deprecated
    public boolean hasPerson() {
        return getPerson() != null;
    }

}
