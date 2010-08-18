package module.mobility.domain.util;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum MobilityProcessStage implements IPresentableEnum {
    UNDER_CONSTRUCTION, PUBLISHED, ARCHIVED;

    private static final String BUNDLE = "resources.MobilityResources";
    private static final String KEY_PREFIX = "label.MobilityProcessStage.";
    private static final String KEY_PREFIX_DESCRIPTION = "label.MobilityProcessStage.description.";

    public String getLocalizedName() {
	final String key = KEY_PREFIX + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

    public String getLocalizedDescription() {
	final String key = KEY_PREFIX_DESCRIPTION + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

}
