package module.mobility.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.organization.domain.OrganizationalModel;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.MyOrg;
import myorg.domain.User;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.services.Service;

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
		String email = personalPortfolio.getEmail();
		if (!StringUtils.isEmpty(email)) {
		    emails.add(email);
		}
	    }
	}
	return emails;
    }

}
