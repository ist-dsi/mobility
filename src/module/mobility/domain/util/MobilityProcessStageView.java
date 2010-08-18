package module.mobility.domain.util;

import java.util.SortedMap;
import java.util.TreeMap;

import module.mobility.domain.Offer;

public class MobilityProcessStageView {

    private final Offer offer;

    public MobilityProcessStageView(Offer offer) {
	this.offer = offer;
    }

    public SortedMap<MobilityProcessStage, MobilityProcessStageState> getMobilityProcessStageStates() {
	final SortedMap<MobilityProcessStage, MobilityProcessStageState> result = new TreeMap<MobilityProcessStage, MobilityProcessStageState>();

	if (!offer.getCanceled()) {
	    result.put(MobilityProcessStage.UNDER_CONSTRUCTION, getUnderConstructionState());
	    result.put(MobilityProcessStage.PUBLISHED, getPublishedState());
	    result.put(MobilityProcessStage.ARCHIVED, getArchivedState());
	} else {
	    result.put(MobilityProcessStage.UNDER_CONSTRUCTION, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(MobilityProcessStage.PUBLISHED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(MobilityProcessStage.ARCHIVED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	}
	return result;
    }

    protected MobilityProcessStageState getUnderConstructionState() {
	return offer.isUnderConstruction() ? MobilityProcessStageState.UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    private MobilityProcessStageState getPublishedState() {
	return offer.isPendingApproval() ? MobilityProcessStageState.UNDER_WAY
		: offer.isUnderConstruction() ? MobilityProcessStageState.NOT_YET_UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    private MobilityProcessStageState getArchivedState() {
	return offer.isApproved() && !offer.isActive() ? MobilityProcessStageState.COMPLETED
		: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    public Offer getOffer() {
	return offer;
    }

}
