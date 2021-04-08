package com.joshua;

import com.joshua.annotations.Column;
import com.joshua.annotations.Entity;
import com.joshua.annotations.Id;
import com.joshua.annotations.Table;
import com.joshua.entities.DatabaseTable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;


public class Main {
    static Map<Class<?>, String> columnType = new HashMap<>(
            Map.of(Integer.class, "int", Double.class, "double"));

    public static void main(String[] args) {
        Class<DatabaseTable> cls = DatabaseTable.class;
        if (cls.isAnnotationPresent(Entity.class))
            createSQLStatement(cls);
    }


    private static void createSQLStatement(Class<?> cls) {
        String table_name;
        if (cls.isAnnotationPresent(Table.class)){
            table_name = cls.getDeclaredAnnotation(Table.class).name();
        }else{
            table_name = cls.getSimpleName().toUpperCase();
        }
        System.out.println("CREATE TABLE " + table_name);
        Field[] declaredFields = cls.getDeclaredFields();
        Arrays.stream(declaredFields)
                .map(x -> columnNameGenerator.apply(x) + " " + columnTypeGenerator.apply(x))
                .reduce(fieldAccumulator).ifPresent(System.out::println);

    }

    static BinaryOperator<String> fieldAccumulator = (x, y) -> x + "\n" + y;

    static Function<Field, String> columnNameGenerator = field ->
            (field.isAnnotationPresent(Column.class)
                ? field.getDeclaredAnnotation(Column.class).name()
                : field.getName()).toUpperCase();

    static Function<Field, String> columnTypeGenerator = field -> columnType.getOrDefault(field.getType(), "varchar(255)");


}
