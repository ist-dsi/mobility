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

import org.fenixedu.bennu.scheduler.CronTask;

import module.mobility.domain.MobilitySystem;
import module.mobility.domain.PersonalPortfolio;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Person;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class CreatePersonalPortfolios extends CronTask {

    @Override
    public void runTask() throws Exception {
        final AccountabilityType type = MobilitySystem.getInstance().getEmployeeAccountabilityType();
        type.getAccountabilityStream().filter(a -> a.isActiveNow()).map(a -> a.getChild()).filter(p -> p.isPerson())
                .map(p -> (Person) p).filter(p -> p.getPersonalPortfolio() == null).forEach(p -> PersonalPortfolio.create(p));
    }
}
