/*
 * @(#)CreatePersonalPortfolios.java
 *
 * Copyright 2011 Instituto Superior Tecnico
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
package module.mobility.domain.task;

import module.mobility.domain.PersonalPortfolio;
import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.Person;
import module.organizationIst.domain.IstAccountabilityType;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class CreatePersonalPortfolios extends CreatePersonalPortfolios_Base {

	public CreatePersonalPortfolios() {
		super();
	}

	@Override
	public void executeTask() {
		AccountabilityType employeesAccountabilityType = IstAccountabilityType.PERSONNEL.readAccountabilityType();
		for (Accountability accountability : employeesAccountabilityType.getAccountabilitiesSet()) {
			if (accountability.isActiveNow()) {
				final Party party = accountability.getChild();
				if (party.isPerson()) {
					Person person = (Person) party;
					if (!person.hasPersonalPortfolio()) {
						PersonalPortfolio.create(person);
					}
				}
			}
		}
	}

	@Override
	public String getLocalizedName() {
		return getClass().getName();
	}
}
