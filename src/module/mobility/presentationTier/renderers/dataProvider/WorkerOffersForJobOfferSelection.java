package module.mobility.presentationTier.renderers.dataProvider;

import module.mobility.domain.activity.ChooseJobOfferCandidatesInformation;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class WorkerOffersForJobOfferSelection implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	ChooseJobOfferCandidatesInformation activityInformation = (ChooseJobOfferCandidatesInformation) arg0;
	return activityInformation.getProcess().getJobOffer().getCandidateWorkerOffer();
    }

}
