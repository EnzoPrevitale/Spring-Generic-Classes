package com.example.demo.generics;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SqlConverter<M extends Model<PK>, PK> {

    public SqlConverter() {

    }

    public Map<String, String> convert(Class<M> model) {
        Map<String, String> fields = new HashMap<>();

        for(var field : model.getSuperclass().getDeclaredFields()) {
            fields.put(field.getName(), field.getType().getSimpleName());
        }

        for(var field : model.getDeclaredFields()) {
            fields.put(field.getName(), field.getType().getSimpleName());
        }

        for(String field : fields.keySet()) {
            var current = fields.get(field);

        }

        Map<String, String> converted = new LinkedHashMap<>();

        List<String> keys = new ArrayList<>(fields.keySet());
        Collections.reverse(keys);

        for(String key : keys) {
            converted.put(key, fields.get(key));
        }

        return converted;
    }

}
