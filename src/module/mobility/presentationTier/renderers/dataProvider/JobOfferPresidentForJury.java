package module.mobility.presentationTier.renderers.dataProvider;

import module.mobility.domain.activity.JobOfferJuryInformation;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class JobOfferPresidentForJury implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	JobOfferJuryInformation jobOfferJuryInformation = (JobOfferJuryInformation) arg0;
	return jobOfferJuryInformation.getJuryMembers();
    }

}
