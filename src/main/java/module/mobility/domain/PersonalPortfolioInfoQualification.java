/*
 * @(#)PersonalPortfolioInfoQualification.java
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

/**
 * 
 * @author Luis Cruz
 * 
 */
public class PersonalPortfolioInfoQualification extends PersonalPortfolioInfoQualification_Base {

    public PersonalPortfolioInfoQualification() {
        super();
        setMobilitySystem(MobilitySystem.getInstance());
    }

    public PersonalPortfolioInfoQualification(final PersonalPortfolioInfo personalPortfolioInfo) {
        this();
        setPersonalPortfolioInfo(personalPortfolioInfo);
    }

    public void delete() {
        setPersonalPortfolioInfo(null);
        setMobilitySystem(null);
        deleteDomainObject();
    }

    @Deprecated
    public boolean hasQualificationType() {
        return getQualificationType() != null;
    }

    @Deprecated
    public boolean hasName() {
        return getName() != null;
    }

    @Deprecated
    public boolean hasInstitution() {
        return getInstitution() != null;
    }

    @Deprecated
    public boolean hasDate() {
        return getDate() != null;
    }

    @Deprecated
    public boolean hasClassification() {
        return getClassification() != null;
    }

    @Deprecated
    public boolean hasPersonalPortfolioInfo() {
        return getPersonalPortfolioInfo() != null;
    }

    @Deprecated
    public boolean hasMobilitySystem() {
        return getMobilitySystem() != null;
    }

}
