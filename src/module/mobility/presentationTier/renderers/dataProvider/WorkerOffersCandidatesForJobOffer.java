package module.mobility.presentationTier.renderers.dataProvider;

import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class WorkerOffersCandidatesForJobOffer implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	return MyOrg.getInstance().getMobilitySystem().getWorkerOffer();
    }

}
