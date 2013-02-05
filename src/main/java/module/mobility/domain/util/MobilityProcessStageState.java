/*
 * @(#)MobilityProcessStageState.java
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

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public enum MobilityProcessStageState implements IPresentableEnum {
    NOT_YET_UNDER_WAY("#ffffff", "#444444"), UNDER_WAY("#F6E3CE", "#B45F04"), COMPLETED("#CEF6CE", "#04B404");

    private String backgroundColor;
    private String borderColor;

    private MobilityProcessStageState(String backgroundColor, String borderColor) {
        setBackgroundColor(backgroundColor);
        setBorderColor(borderColor);
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    private static final String BUNDLE = "resources.MobilityResources";
    private static final String KEY_PREFIX = "label.MobilityProcessStageState.";

    @Override
    public String getLocalizedName() {
        final String key = KEY_PREFIX + name();
        return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }
}
