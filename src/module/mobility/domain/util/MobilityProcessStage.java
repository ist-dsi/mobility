package module.mobility.domain.util;

import java.util.MissingResourceException;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum MobilityProcessStage implements IPresentableEnum {
    UNDER_CONSTRUCTION, SELECTION, EVALUATION, JURY_DEFINITION, PUBLISHED, CANDIDACY_EVALUATION, CONCLUDED, CONCLUDED_CANDIDACY;

    private static final String BUNDLE = "resources.MobilityResources";
    private static final String KEY_PREFIX = "label.MobilityProcessStage.";
    private static final String KEY_PREFIX_DESCRIPTION = "label.MobilityProcessStage.description.";

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
