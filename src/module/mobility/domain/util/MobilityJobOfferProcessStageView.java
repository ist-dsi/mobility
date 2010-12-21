package module.mobility.domain.util;

import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import module.mobility.domain.JobOffer;

public class MobilityJobOfferProcessStageView {
    private JobOffer jobOffer;

    protected static SortedMap<MobilityProcessStage, EnumSet<MobilityProcessStage>> mobilityStateMap;

    public static SortedMap<MobilityProcessStage, EnumSet<MobilityProcessStage>> getMobilityStateMap() {
	return mobilityStateMap;
    }

    public MobilityJobOfferProcessStageView(JobOffer jobOffer) {
	this.jobOffer = jobOffer;
    }

    static {
	mobilityStateMap = new TreeMap<MobilityProcessStage, EnumSet<MobilityProcessStage>>();

	mobilityStateMap.put(MobilityProcessStage.UNDER_CONSTRUCTION, EnumSet.of(MobilityProcessStage.SELECTION));
	mobilityStateMap.put(MobilityProcessStage.SELECTION, EnumSet.of(MobilityProcessStage.EVALUATION));
	mobilityStateMap.put(MobilityProcessStage.EVALUATION,
		EnumSet.of(MobilityProcessStage.CONCLUDED, MobilityProcessStage.JURY_DEFINITION));
	mobilityStateMap.put(MobilityProcessStage.JURY_DEFINITION, EnumSet.of(MobilityProcessStage.PUBLISHED));
	mobilityStateMap.put(MobilityProcessStage.PUBLISHED, EnumSet.of(MobilityProcessStage.CANDIDACY_EVALUATION));
	mobilityStateMap.put(MobilityProcessStage.CANDIDACY_EVALUATION, EnumSet.of(MobilityProcessStage.CONCLUDED_CANDIDACY));
	mobilityStateMap.put(MobilityProcessStage.CONCLUDED, EnumSet.noneOf(MobilityProcessStage.class));
	mobilityStateMap.put(MobilityProcessStage.CONCLUDED_CANDIDACY, EnumSet.noneOf(MobilityProcessStage.class));
    }

    public SortedMap<MobilityProcessStage, MobilityProcessStageState> getMobilityProcessStageStates() {
	final SortedMap<MobilityProcessStage, MobilityProcessStageState> result = new TreeMap<MobilityProcessStage, MobilityProcessStageState>();

	result.put(MobilityProcessStage.UNDER_CONSTRUCTION, getUnderConstructionState());
	result.put(MobilityProcessStage.SELECTION, getSelectionState());
	result.put(MobilityProcessStage.EVALUATION, getEvaluationState());

	if (jobOffer.isInInternalRecruitment()) {
	    result.put(MobilityProcessStage.JURY_DEFINITION, getJuryDefinitionState());
	    result.put(MobilityProcessStage.PUBLISHED, getPublishedState());
	    result.put(MobilityProcessStage.CANDIDACY_EVALUATION, getCandidacyEvaluationState());
	    result.put(MobilityProcessStage.CONCLUDED_CANDIDACY, getConcludedState());
	} else {
	    result.put(MobilityProcessStage.CONCLUDED, getConcludedState());
	}
	return result;
    }

    private MobilityProcessStageState getUnderConstructionState() {

	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.isUnderConstruction() ? MobilityProcessStageState.UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }

    private MobilityProcessStageState getSelectionState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.isPendingSelection() ? MobilityProcessStageState.UNDER_WAY
			: jobOffer.isUnderConstruction() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
				: MobilityProcessStageState.COMPLETED;
    }

    private MobilityProcessStageState getEvaluationState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.isPendingEvaluation() ? MobilityProcessStageState.UNDER_WAY : (jobOffer.isConcluded() || jobOffer
			.isInInternalRecruitment()) ? MobilityProcessStageState.COMPLETED
			: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    protected MobilityProcessStageState getConcludedState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.isArchived() ? MobilityProcessStageState.COMPLETED
			: jobOffer.isConcluded() ? MobilityProcessStageState.UNDER_WAY
				: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    private MobilityProcessStageState getJuryDefinitionState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.isPendingJuryDefinition() ? MobilityProcessStageState.UNDER_WAY : jobOffer
			.getSubmittedForApprovalDate() != null ? MobilityProcessStageState.COMPLETED
			: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    protected MobilityProcessStageState getPublishedState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY
		: jobOffer.getApprovalDate() != null ? MobilityProcessStageState.COMPLETED
			: jobOffer.isPendingApproval() ? MobilityProcessStageState.UNDER_WAY
				: MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    private MobilityProcessStageState getCandidacyEvaluationState() {
	return jobOffer.getCanceled() ? MobilityProcessStageState.NOT_YET_UNDER_WAY : (!jobOffer.isApproved() || !jobOffer
		.isCandidacyPeriodFinish()) ? MobilityProcessStageState.NOT_YET_UNDER_WAY : jobOffer
		.isPendingCandidacyEvaluation() ? MobilityProcessStageState.UNDER_WAY : MobilityProcessStageState.COMPLETED;
    }
    
    public boolean getCanceled() {
	return jobOffer.getCanceled();
    }
}
