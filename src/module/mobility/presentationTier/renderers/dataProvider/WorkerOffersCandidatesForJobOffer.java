package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.WorkerOffer;
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
	List<WorkerOffer> workerOfferList = new ArrayList<WorkerOffer>();
	for (WorkerOffer workerOffer : MyOrg.getInstance().getMobilitySystem().getWorkerOffer()) {
	    if (workerOffer.isActive() && workerOffer.isApproved()) {
		workerOfferList.add(workerOffer);
	    }
	}
	return workerOfferList;
    }

}
