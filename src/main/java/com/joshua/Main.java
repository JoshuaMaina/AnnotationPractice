package com.joshua;

import com.joshua.annotations.Entity;
import com.joshua.entities.DatabaseTable;
import com.joshua.resources.Mapper;

import java.util.HashMap;
import java.util.Map;

// Ps, I know it's not the best code haha. I'm just playing around with concepts right now

//TODO Try and implement a full function that works for an entity
//TODO Use Reflection to search for the other classes with @Entity annotation
//TODO Generate multiple 'SQL Statements' for the variety of classes
public class Main {
    static Map<Class<?>, String> columnType = new HashMap<>(
            Map.of(Integer.class, "int", Double.class, "double"));

    public static void main(String[] args) {
        Class<DatabaseTable> cls = DatabaseTable.class;
        Mapper mapper = Mapper.getMapper();
        if (cls.isAnnotationPresent(Entity.class)) {
            String statement = mapper.getStatement(cls);
            System.out.println(statement);
        }
    }

}
