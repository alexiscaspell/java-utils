package io.blacktoast.utils.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {

	private static final String DEFAULT_CHARACTER_TO_SPLIT = ".";

	public static Object getProperty(String propertyName, Map<String, Object> map) {
		return getProperty(propertyName, map, DEFAULT_CHARACTER_TO_SPLIT);
	}

	public static Object getProperty(String propertyName, Map<String, Object> map, String characterToSplit) {

		Object actualObject = map;
		List<String> propertyNames = split(propertyName,characterToSplit);

		for (String actualPropertyName : propertyNames) {
			actualObject = getMapPropertyValue(actualObject, actualPropertyName);
		}

		return actualObject;
	}

	@SuppressWarnings("unchecked")
	private static Object getMapPropertyValue(Object object, String propertyName) {
		return ((Map<String, Object>) object).get(propertyName);
	}

	public static void setProperty(String propertyName, Map<String, Object> map, Object value) {
		setProperty(propertyName, map, DEFAULT_CHARACTER_TO_SPLIT, value);
	}

	@SuppressWarnings("unchecked")
	public static void setProperty(String propertyName, Map<String, Object> map, String characterToSplit,
			Object value) {

		Map<String, Object> actualMap = map;
		List<String> propertyNames = split(propertyName,characterToSplit);

		for (int i = 0; i < propertyNames.size(); i++) {

			String actualPropertyName = propertyNames.get(i);

			if (i == propertyNames.size() - 1) {
				actualMap.put(actualPropertyName, value);
				return;
			}

			if (!actualMap.containsKey(actualPropertyName)) {
				actualMap.put(actualPropertyName, new HashMap<String, Object>());
			}

			actualMap = (Map<String, Object>) actualMap.get(actualPropertyName);
		}

	}
	
	//ACTUALMENTE ES PARA UN SOLO CARACTER (LUEGO TENGO QUE IMPLEMENTARLO PARA VARIOS)
    public static List<String> split(String strToSplit, String strDelimiter) {
    	char delimiter = strDelimiter.charAt(0);
        List<String> arr = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strToSplit.length(); i++) {
            char at = strToSplit.charAt(i);
            if (at == delimiter) {
                arr.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(at);
            }
        }
        arr.add(sb.toString());
        return arr;
    }

}
