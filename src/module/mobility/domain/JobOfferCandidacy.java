package module.mobility.domain;

import java.util.List;

import module.workflow.domain.ProcessFile;

public class JobOfferCandidacy extends JobOfferCandidacy_Base {

    public JobOfferCandidacy(PersonalPortfolioInfo personalPortfolioInfo, List<ProcessFile> candidacyFiles) {
	super();
	setPersonalPortfolioInfo(personalPortfolioInfo);
	if (candidacyFiles != null) {
	    getCandidacyFileSet().addAll(candidacyFiles);
	}
    }

    public boolean isSelectedCandidacy() {
	return getJobOffer().getChosenCandidate().contains(getPersonalPortfolioInfo());
    }

}
