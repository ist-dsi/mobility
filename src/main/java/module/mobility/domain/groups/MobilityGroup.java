/*
 * @(#)MobilityGroup.java
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
package module.mobility.domain.groups;

import java.util.Set;

import module.mobility.domain.MobilitySystem;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author Susana Fernandes
 * 
 */
public class MobilityGroup extends MobilityGroup_Base {

    public MobilityGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(final User user) {
        return MobilitySystem.getInstance().isManagementMember(user);
    }

    @Atomic
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
