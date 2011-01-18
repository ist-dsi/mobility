package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.JobOffer;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.activity.ChooseJobOfferCandidatesInformation;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class WorkersForJobOfferSelection implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	List<PersonalPortfolioInfo> result = new ArrayList<PersonalPortfolioInfo>();

	ChooseJobOfferCandidatesInformation activityInformation = (ChooseJobOfferCandidatesInformation) arg0;

	JobOffer jobOffer = activityInformation.getProcess().getJobOffer();
	if (jobOffer.isInInternalRecruitment()) {
	    result.addAll(jobOffer.getCandidatePortfolioInfo());
	} else {
	    for (WorkerOffer workerOffer : jobOffer.getCandidateWorkerOffer()) {
		result.add(workerOffer.getPersonalPortfolioInfo());
	    }
	}

	return result;
    }

}
