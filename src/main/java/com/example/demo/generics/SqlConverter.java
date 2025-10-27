package com.example.demo.generics;


import jakarta.persistence.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SqlConverter<M extends Model<PK>, PK> {

    private final Class<M> modelClass;
    private final Class<PK> pkClass;

    public SqlConverter(Class<M> modelClass, Class<PK> pkClass) {
        this.modelClass = modelClass;
        this.pkClass = pkClass;
    }

    private String getTable() {
        Table table = modelClass.getAnnotation(Table.class);
        return table.name();
    }

    private List<String> getConstraints(Field field) {
        List<String> constraints = new ArrayList<>();
        for(Annotation annotation : field.getAnnotations()) {
            String[] str = annotation.toString().split("\\.");
            constraints.add(str[str.length - 1]);
        }
        if(field.getName().equals("id")) constraints.add(pkClass.getSimpleName());
        else constraints.add(field.getType().getSimpleName());
        return constraints;
    }

    public Map<String, List<String>> getFields(Class<M> model) {
        Map<String, List<String>> fields = new LinkedHashMap<>();

        for(var field : model.getSuperclass().getDeclaredFields()) {
            fields.put(field.getName(), getConstraints(field));
        }

        for(var field : model.getDeclaredFields()) {
            fields.put(field.getName(), getConstraints(field));
        }

        return fields;
    }

    public String build() {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(getTable());
        builder.append("(\n    ");
        var fields = getFields(modelClass);
        for(var field : fields.keySet()) {
            var constraints = getFields(modelClass).get(field);
            System.out.println(constraints);
            builder.append(field).append(" ");

            if(constraints.contains("Long")) builder.append("BIGINT");
            else if(constraints.contains("Integer")) builder.append("INTEGER");
            else if(constraints.contains("Byte")) builder.append("SMALLINT");
            else if(constraints.contains("Float")) builder.append("REAL");

            else if(constraints.contains("Boolean")) builder.append("BOOL");

            if (constraints.contains("String")) builder.append("VARCHAR");

            if (constraints.contains("Id()")) builder.append(" PRIMARY KEY");
            if (constraints.contains("GeneratedValue(strategy=IDENTITY, generator=\"\")")) builder.append(" AUTO_INCREMENT");

            if(constraints.getFirst().contains("nullable=false")) builder.append(" NOT NULL");

            builder.append(",\n    ");
        }
        builder.append(");\n");
        builder.append(getFields(modelClass));
        return builder.toString();
    }

}
