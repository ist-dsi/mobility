/*
 * @(#)MobilityProcessStage.java
 *
 * Copyright 2010 Instituto Superior Tecnico
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
package module.mobility.domain.util;

import java.util.MissingResourceException;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public enum MobilityProcessStage implements IPresentableEnum {
	UNDER_CONSTRUCTION, SELECTION, EVALUATION, JURY_DEFINITION, PENDING_PUBLISHMENT, PUBLISHED, CANDIDACY_EVALUATION,
	CONCLUDED_CANDIDACY, CONCLUDED, ARCHIVED;

	private static final String BUNDLE = "resources.MobilityResources";
	private static final String KEY_PREFIX = "label.MobilityProcessStage.";
	private static final String KEY_PREFIX_DESCRIPTION = "label.MobilityProcessStage.description.";

	@Override
	public String getLocalizedName() {
		final String key = KEY_PREFIX + name();
		try {
			return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
		} catch (MissingResourceException e) {
			return name();
		}
	}

	public String getLocalizedDescription() {
		final String key = KEY_PREFIX_DESCRIPTION + name();
		return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
	}
}
