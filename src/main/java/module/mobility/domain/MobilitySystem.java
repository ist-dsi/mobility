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

import org.apache.commons.lang.StringUtils;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.fenixWebFramework.services.Service;

import module.organization.domain.OrganizationalModel;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class MobilitySystem extends MobilitySystem_Base {

    public static MobilitySystem getInstance() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasMobilitySystem()) {
	    initialize();
	}
	return myOrg.getMobilitySystem();
    }

    @Service
    public synchronized static void initialize() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasMobilitySystem()) {
	    new MobilitySystem(myOrg);
	}
    }

    private MobilitySystem(final MyOrg myOrg) {
	super();
	setMyOrg(myOrg);
    }

    @Service
    @Override
    public void setOrganizationalModel(OrganizationalModel organizationalModel) {
	super.setOrganizationalModel(organizationalModel);
    }

    public boolean isManagementMember() {
	return isManagementMember(UserView.getCurrentUser());
    }

    public boolean isManagementMember(final User user) {
	if (getManagersQueue() != null) {
	    return getManagersQueue().isUserAbleToAccessQueue(user);
	}
	return false;
    }

    public Set<User> getManagementUsers() {
	return getManagersQueue() != null ? getManagersQueue().getUsersSet() : new HashSet<User>();
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
