package com.packageing.tools.packagetools.entitys;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PackageEntity{
    
    private static AtomicInteger ids;

    public static int getNewEntityID() throws PackageingException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
        if(ids == null){
                Class<?> clazz = Class.forName("net.minecraft.world.entity.Entity");
                List<Field> aints = new ArrayList<>();
                for(Field f : clazz.getDeclaredFields()){
                    f.setAccessible(true);
                    if(f.getType().getSimpleName().equals("AtomicInteger")){
                        aints.add(f);
                    }
                }
                if(aints.size() != 1)
                    throw new PackageingException("Can not find entity id field!");
                AtomicInteger a;
                a = (AtomicInteger) aints.get(0).get(null);
                ids = a;
                return a.incrementAndGet();
        }else{
            return ids.incrementAndGet();
        }
    }

}
