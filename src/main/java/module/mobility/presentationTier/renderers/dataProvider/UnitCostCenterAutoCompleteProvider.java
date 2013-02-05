/*
 * @(#)UnitCostCenterAutoCompleteProvider.java
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
package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import module.organization.domain.Accountability;
import module.organization.domain.Party;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organizationIst.domain.IstAccountabilityType;
import module.organizationIst.domain.IstPartyType;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class UnitCostCenterAutoCompleteProvider implements AutoCompleteProvider {

    @Override
    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
        final List<Unit> units = new ArrayList<Unit>();

        final String trimmedValue = value.trim();
        final String[] input = trimmedValue.split(" ");
        StringNormalizer.normalize(input);

        for (final Party party : getParties(argsMap, value)) {
            if (party.isUnit() && party.getPartyTypes().contains(PartyType.readBy(IstPartyType.COST_CENTER.getType()))) {
                final Unit unit = (Unit) party;
                if (isActive(unit)) {
                    final String unitName = StringNormalizer.normalize(unit.getPartyName().getContent());
                    if (hasMatch(input, unitName)) {
                        units.add(unit);
                    } else {
                        final String unitAcronym = StringNormalizer.normalize(unit.getAcronym());
                        if (hasMatch(input, unitAcronym)) {
                            units.add(unit);
                        }
                    }
                }
            }
        }

        Collections.sort(units, Unit.COMPARATOR_BY_PRESENTATION_NAME);

        return units;
    }

    private boolean isActive(Unit unit) {
        for (Accountability accountability : unit.getParentAccountabilities(IstAccountabilityType.ORGANIZATIONAL
                .readAccountabilityType())) {
            if (accountability.isActiveNow()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMatch(final String[] input, final String unitNameParts) {
        for (final String namePart : input) {
            if (unitNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

    protected Set<Party> getParties(Map<String, String> argsMap, String value) {
        return MyOrg.getInstance().getPartiesSet();
    }
}
