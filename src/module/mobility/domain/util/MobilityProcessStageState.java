package module.mobility.domain.util;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

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

    public String getLocalizedName() {
	final String key = KEY_PREFIX + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }
}
