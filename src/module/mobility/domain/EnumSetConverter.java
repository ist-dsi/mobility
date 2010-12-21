package module.mobility.domain;

import java.io.Serializable;
import java.util.EnumSet;

public class EnumSetConverter implements Serializable {

    private static final String SEPARATOR = ":";

    public static String externalize(EnumSet enumSet) {
	String result = null;
	if (!enumSet.isEmpty()) {
	    result = enumSet.iterator().next().getClass().getName() + SEPARATOR + enumSet.toString();
	    System.out.println(result);
	}
	return result;
    }

    public static EnumSet internalize(String enumSetString) {
	int indexOfSeparator = enumSetString.indexOf(SEPARATOR);
	String className = enumSetString.substring(0, indexOfSeparator);
	String enumValues = enumSetString.substring(indexOfSeparator + 1);

	EnumSet result = null;
	try {
	    Class<Enum> enumClass = (Class<Enum>) Class.forName(className);
	    result = EnumSet.noneOf(enumClass);
	    enumValues = enumValues.replace('[', ' ').replace(']', ' ').trim();

	    for (String enumValue : enumValues.split(",")) {
		result.add(Enum.valueOf(enumClass, enumValue.trim()));
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}

	return result;
    }
}
