/*
 * @(#)MobilitySystem.java
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.organization.domain.OrganizationalModel;

import org.apache.commons.lang.StringUtils;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.groups.Group;
import org.fenixedu.bennu.core.security.Authenticate;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class MobilitySystem extends MobilitySystem_Base {

    public static MobilitySystem getInstance() {
        final Bennu bennu = Bennu.getInstance();
        if (bennu.getMobilitySystem() == null) {
            initialize();
        }
        return bennu.getMobilitySystem();
    }

    @Atomic
    public synchronized static void initialize() {
        final Bennu bennu = Bennu.getInstance();
        if (bennu.getMobilitySystem() == null) {
            new MobilitySystem(bennu);
        }
    }

    private MobilitySystem(final Bennu bennu) {
        super();
        setBennu(bennu);
    }

    @Atomic
    @Override
    public void setOrganizationalModel(OrganizationalModel organizationalModel) {
        super.setOrganizationalModel(organizationalModel);
    }

    public boolean isManagementMember() {
        return isManagementMember(Authenticate.getUser());
    }

    public boolean isManagementMember(final User user) {
        return Group.parse("#MobilityManagers").isMember(user);
    }

    public Set<User> getManagementUsers() {
        return Group.parse("#MobilityManagers").getMembers();
    }

    public Collection<String> getServiceNotificationEmails() {
        Collection<String> emails = new HashSet<String>();
        for (PersonalPortfolio personalPortfolio : getPersonalPortfolioSet()) {
            final Boolean notificationService = personalPortfolio.getNotificationService();
            if (notificationService != null && notificationService.booleanValue()) {
                String email = personalPortfolio.getPerson().getUser().getEmail();
                if (!StringUtils.isEmpty(email)) {
                    emails.add(email);
                }
            }
        }
        return emails;
    }

}
