/*
 * @(#)MobilityWorkerOfferProcessStageView.java
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
package module.mobility.domain.util;

import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import module.mobility.domain.WorkerOffer;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class MobilityWorkerOfferProcessStageView {

    private final WorkerOffer offer;

    protected static SortedMap<MobilityProcessStage, EnumSet<MobilityProcessStage>> mobilityStateMap;

    public static SortedMap<MobilityProcessStage, EnumSet<MobilityProcessStage>> getMobilityStateMap() {
        return mobilityStateMap;
    }

    public MobilityWorkerOfferProcessStageView(WorkerOffer offer) {
        this.offer = offer;
    }

    public boolean getCanceled() {
        return offer.getCanceled();
    }

    static {
        mobilityStateMap = new TreeMap<MobilityProcessStage, EnumSet<MobilityProcessStage>>();
        mobilityStateMap.put(MobilityProcessStage.UNDER_CONSTRUCTION, EnumSet.of(MobilityProcessStage.PUBLISHED));
        mobilityStateMap.put(MobilityProcessStage.PUBLISHED, EnumSet.of(MobilityProcessStage.CONCLUDED));
        mobilityStateMap.put(MobilityProcessStage.CONCLUDED, EnumSet.noneOf(MobilityProcessStage.class));
    }

    public SortedMap<MobilityProcessStage, MobilityProcessStageState> getMobilityProcessStageStates() {
        final SortedMap<MobilityProcessStage, MobilityProcessStageState> result =
                new TreeMap<MobilityProcessStage, MobilityProcessStageState>();

        if (!offer.getCanceled()) {
            result.put(MobilityProcessStage.UNDER_CONSTRUCTION, getUnderConstructionState());
            result.put(MobilityProcessStage.PUBLISHED, getPublishedState());
            result.put(MobilityProcessStage.CONCLUDED, getArchivedState());
        } else {
            result.put(MobilityProcessStage.UNDER_CONSTRUCTION, MobilityProcessStageState.NOT_YET_UNDER_WAY);
            result.put(MobilityProcessStage.PUBLISHED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
            result.put(MobilityProcessStage.CONCLUDED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
        }
        return result;
    }

    private MobilityProcessStageState getUnderConstructionState() {
        return offer.isUnderConstruction() ? MobilityProcessStageState.UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    protected MobilityProcessStageState getPublishedState() {
        return offer.isPendingApproval() ? MobilityProcessStageState.UNDER_WAY : offer.isUnderConstruction() ? MobilityProcessStageState.NOT_YET_UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    protected MobilityProcessStageState getArchivedState() {
        return offer.isApproved() && !offer.isActive() ? MobilityProcessStageState.COMPLETED : MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

}
