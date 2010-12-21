package module.mobility.domain.util;

import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import module.mobility.domain.WorkerOffer;

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
	final SortedMap<MobilityProcessStage, MobilityProcessStageState> result = new TreeMap<MobilityProcessStage, MobilityProcessStageState>();

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
	return offer.isPendingApproval() ? MobilityProcessStageState.UNDER_WAY
		: offer.isUnderConstruction() ? MobilityProcessStageState.NOT_YET_UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    protected MobilityProcessStageState getArchivedState() {
	return offer.isApproved() && !offer.isActive() ? MobilityProcessStageState.COMPLETED
		: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

}
