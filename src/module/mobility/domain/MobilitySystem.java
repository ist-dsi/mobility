package module.mobility.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Person;
import module.organization.domain.Unit;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.MyOrg;
import myorg.domain.User;
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

    public SortedSet<Accountability> getManagementMembers() {
	final SortedSet<Accountability> managementMembers = new TreeSet<Accountability>(
		Accountability.COMPARATOR_BY_CHILD_PARTY_NAMES);
	if (hasManagementUnit() && hasManagementAccountabilityType()) {
	    final Unit accountingUnit = getManagementUnit();
	    final AccountabilityType accountabilityType = getManagementAccountabilityType();
	    for (final Accountability accountability : accountingUnit.getChildAccountabilitiesSet()) {
		if (accountability.getAccountabilityType() == accountabilityType && accountability.getChild().isPerson()) {
		    managementMembers.add(accountability);
		}
	    }
	}
	return managementMembers;
    }

    public Set<User> getManagementUsers() {
	final Set<User> managementUsers = new HashSet<User>();
	if (hasManagementUnit() && hasManagementAccountabilityType()) {
	    final Unit accountingUnit = getManagementUnit();
	    final AccountabilityType accountabilityType = getManagementAccountabilityType();
	    for (final Accountability accountability : accountingUnit.getChildAccountabilitiesSet()) {
		if (accountability.getAccountabilityType() == accountabilityType && accountability.getChild().isPerson()) {
		    managementUsers.add(((Person) accountability.getChild()).getUser());
		}
	    }
	}
	return managementUsers;
    }

    public boolean isManagementMember() {
	return getManagementAccountability(UserView.getCurrentUser()) != null;
    }

    public boolean isManagementMember(final User user) {
	return getManagementAccountability(user) != null;
    }

    public Accountability getManagementAccountability(final User user) {
	if (hasManagementUnit() && hasManagementAccountabilityType()) {
	    final Unit managementeUnit = getManagementUnit();
	    final AccountabilityType accountabilityType = getManagementAccountabilityType();
	    return findAccountability(user, accountabilityType, managementeUnit);
	}
	return null;
    }

    private Accountability findAccountability(final User user, final AccountabilityType accountabilityType, final Unit unit) {
	for (final Accountability accountability : unit.getChildAccountabilitiesSet()) {
	    if (accountability.getAccountabilityType() == accountabilityType && accountability.getChild().isPerson()) {
		final Person person = (Person) accountability.getChild();
		if (person.getUser() == user) {
		    return accountability;
		}
	    }
	}
	return null;
    }
}
