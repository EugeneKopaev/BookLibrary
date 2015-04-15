package org.booklibrary.app.rest.dto.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * JAXB class adapter for java.util.Map class
 */
public class MapAdapter extends XmlAdapter<MapEntry[], Map<String, String>> {

    public MapEntry[] marshal(Map<String, String> map){

        MapEntry[] mapElements = new MapEntry[map.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            mapElements[i++] = new MapEntry(entry.getKey(), entry.getValue());
        }
        return mapElements;
    }

    public Map<String, String> unmarshal(MapEntry[] arg0){
        Map<String, String> map = new HashMap<>();
        for (MapEntry entry : arg0) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
