package io.blacktoast.utils.sqlexecutor.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultBuilder {

    /**
     * @param resultMap
     * @param resultColumns
     * @return
     */
    private static Boolean canRenameColumns(List<Map<String, Object>> resultMap, List<String> resultColumns) {
        return resultColumns != null && resultMap != null && !resultColumns.isEmpty() && !resultMap.isEmpty();
    }

    /**
     * @param resultMap
     * @param resultColumns
     * @return
     */
    public static List<Map<String, Object>> getResultChangedColumns(List<Map<String, Object>> resultMap,
                                                                    List<String> resultColumns) {

        if (!canRenameColumns(resultMap, resultColumns)) {
            return resultMap;
        }

        List<Map<String, Object>> resultChangedNames = new ArrayList<>(resultMap);

        List<String> listKeys = new ArrayList<>(resultMap.get(0).keySet());

        Integer iterations = resultColumns.size() > resultMap.size() ? resultColumns.size() - resultMap.size()
                : resultColumns.size();

        for (Map<String, Object> mapChangedNames : resultChangedNames) {

            for (int i = 0; i < iterations; i++) {

                String key = listKeys.get(i);
                String newKey = resultColumns.get(i);
                changeKeyFromMap(key, newKey, mapChangedNames);
            }
        }

        return resultChangedNames;
    }

    /**
     * @param key
     * @param newKey
     * @param map
     */
    private static void changeKeyFromMap(String key, String newKey, Map<String, Object> map) {
        Object value = map.remove(key);
        map.put(newKey, value);
    }
}
