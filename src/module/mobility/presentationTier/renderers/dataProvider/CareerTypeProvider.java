package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.List;

import module.mobility.domain.CareerType;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.ist.fenixWebFramework.renderers.converters.EnumArrayConverter;

public class CareerTypeProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return new EnumArrayConverter(CareerType.class);
    }

    @Override
    public Object provide(Object arg0, Object arg1) {
	List<CareerType> careers = new ArrayList<CareerType>();
	careers.add(CareerType.SENIOR_TECHNICIAN);
	careers.add(CareerType.TECHNICAL_ASSISTANT);
	careers.add(CareerType.TECHNICAL_OPERATION);
	return careers;
    }

}
