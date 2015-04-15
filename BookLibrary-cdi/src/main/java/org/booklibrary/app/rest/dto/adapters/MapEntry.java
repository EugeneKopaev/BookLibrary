package org.booklibrary.app.rest.dto.adapters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "constraint")
public class MapEntry {

    private String key;
    private String value;

    private MapEntry() {
    }

    public MapEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @XmlElement(name = "property")
    public String getKey() {
        return key;
    }

    @XmlElement(name = "violation")
    public String getValue() {
        return value;
    }
}
