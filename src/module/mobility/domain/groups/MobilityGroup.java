package module.mobility.domain.groups;

import java.util.Set;

import module.mobility.domain.MobilitySystem;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class MobilityGroup extends MobilityGroup_Base {

    public MobilityGroup() {
	super();
    }

    @Override
    public boolean isMember(final User user) {
	return MobilitySystem.getInstance().isManagementMember(user);
    }

    @Service
    public static MobilityGroup getInstance() {
	final MobilityGroup mobilityGroup = (MobilityGroup) PersistentGroup.getSystemGroup(MobilityGroup.class);
	return mobilityGroup == null ? new MobilityGroup() : mobilityGroup;
    }

    @Override
    public String getName() {
	return BundleUtil.getStringFromResourceBundle("resources/MobilityResources", "label.mobility.group.mobilityGroup.name");
    }

    @Override
    public Set<User> getMembers() {
	return MobilitySystem.getInstance().getManagementUsers();
    }

}
