package module.mobility.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum CareerType implements IPresentableEnum {

    SENIOR_TECHNICIAN, TECHNICAL_ASSISTANT, TECHNICAL_OPERATION, INFORMATIC;

    private static final String BUNDLE = "resources.MobilityResources";
    private static final String KEY_PREFIX = "label.mobility.CareerType.";

    public String getLocalizedName() {
	final String key = KEY_PREFIX + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

}
