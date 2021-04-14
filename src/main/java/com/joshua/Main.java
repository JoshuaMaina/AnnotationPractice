package com.joshua;

import com.joshua.annotations.Entity;
import com.joshua.entities.DatabaseTable;
import com.joshua.resources.Mapper;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Ps, I know it's not the best code haha. I'm just playing around with concepts right now

// Ended up using a library called Reflections to cater for the searching in the class path
public class Main {

    public static void main(String[] args) {
        String currPackage = Main.class.getPackageName();
        Reflections reflections = new Reflections(currPackage);
        Set<Class<?>> clsz = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> cls : clsz ){
            Mapper mapper = Mapper.getMapper();
            if (cls.isAnnotationPresent(Entity.class)) {
                String statement = mapper.getStatement(cls);
                System.out.println(statement);
                System.out.println();
            }
        }

    }

}
