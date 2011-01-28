package module.mobility.presentationTier.renderers.dataProvider;

import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class PersonDocumentsForCandidacy implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyArrayConverter();
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	Person person = UserView.getCurrentUser().getPerson();
	return person.getPersonalPortfolio().getPersonalPortfolioProcess().getFiles();
    }

}
