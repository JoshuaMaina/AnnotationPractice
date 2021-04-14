package com.joshua.resources;

import com.joshua.annotations.Column;
import com.joshua.annotations.Id;
import com.joshua.annotations.Table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public interface Mapper {
    String getStatement(Class<?> cls);
    String getStatement(Field field);
    String getStatement(Field[] fields);
    Field getId(Field[] fields);

    static Mapper getMapper(){
        return new MapperImpl();
    }

     Map<Class<?>, String> typeMap = new HashMap<>(Map.of(
            Integer.TYPE, "int",
            Double.TYPE, "double",
            String.class, "varchar(255)",
            Long.TYPE, "int"
    ));

    default String fieldName(Field field) {
        Column columnAnn = field.getDeclaredAnnotation(Column.class);
        return columnAnn == null
                ? field.getName()
                : columnAnn.name();
    }

    default String fieldType(Field field) {
        return typeMap.getOrDefault(field.getType(), "varchar(255)");
    }

    default String fieldArguments(Field field) {
        return "";
    }

    class MapperImpl implements Mapper {
        @Override
        public String getStatement(Class<?> cls) {
            Table tableAnn = cls.getDeclaredAnnotation(Table.class);
            String table_name = tableAnn == null
                    ? cls.getSimpleName().toUpperCase()
                    : tableAnn.name().toUpperCase();
            return String.format("""
                CREATE TABLE %s (
                %s
                PRIMARY KEY(%s))""", table_name, getStatement(cls.getDeclaredFields()),
                    fieldName(getId(cls.getDeclaredFields())));
        }

        @Override
        public String getStatement(Field field) {
            return String.format("%s %s %s", fieldName(field).toUpperCase(), fieldArguments(field), fieldType(field));
        }

        @Override
        public String getStatement(Field[] fields) {
            if (fields.length == 0) return null;
            StringJoiner joiner = new StringJoiner("\n");
            for (Field field : fields)
                joiner.add(getStatement(field));

            return joiner.toString();
        }

        @Override
        public Field getId(Field[] fields) {
            if (fields.length == 0) return null;
            Class<?> cls = fields[0].getDeclaringClass();
            Field primaryKey = null;
            int count = 0;
            for (Field field: fields){
                if (field.isAnnotationPresent(Id.class)){
                    if (count == 1){
                        throw new IllegalArgumentException("The class " + cls.getSimpleName() + "has more than one ID");
                    } else {
                        primaryKey = field;
                        count ++;
                    }
                }
            }
            if (primaryKey == null)
                throw new IllegalArgumentException("The class " + cls.getSimpleName() + "has no ID");
            return primaryKey;
        }
    }
}
