package de.merkeg.vsrentbe.mail;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class EmailVariables {

    private String name;
    private String registrationUrl;

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("registrationUrl", registrationUrl);

        return map;
    }
}
