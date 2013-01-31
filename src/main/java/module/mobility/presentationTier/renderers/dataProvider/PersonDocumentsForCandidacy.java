/*
 * @(#)PersonDocumentsForCandidacy.java
 *
 * Copyright 2011 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.presentationTier.renderers.dataProvider;

import module.organization.domain.Person;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * 
 * @author Susana Fernandes
 * 
 */
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
