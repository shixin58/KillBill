package com.bride.client.language;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

/**
 * <p>Created by shixin on 2019-05-03.
 */
public enum Sex {
    GENTLEMAN, LADY;

    public static void main(String[] args) {
        EnumMap<Sex, String> enumMap = new EnumMap<>(Sex.class);
        enumMap.put(Sex.GENTLEMAN, "Victor");
        enumMap.put(Sex.LADY, "Sansa");
        Set<EnumMap.Entry<Sex, String>> entrySet = enumMap.clone().entrySet();
        for(EnumMap.Entry<Sex, String> entry : entrySet) {
            System.out.println(entry.getKey()+" -> "+entry.getValue());
        }

        System.out.println(Sex.GENTLEMAN.getDeclaringClass().getName()+" - "+Sex.class.getSuperclass().getName());

        // java.lang.Comparable<E>, interface java.io.Serializable,
        for (Type type : Enum.class.getGenericInterfaces()) {
            System.out.print(type.toString()+", ");
        }
        // java.util.Collection<E>,
        for (Type type : Set.class.getGenericInterfaces()) {
            System.out.print(type.toString()+", ");
        }
        System.out.println();

        for (Sex sex : Sex.values()) {
            System.out.print(sex.name()+", ");
        }
        System.out.println();

        for (Sex sex : Sex.GENTLEMAN.getClass().getEnumConstants()) {
            System.out.print(sex.name()+", ");
        }
        System.out.println();

        EnumSet<Sex> enumSet = EnumSet.noneOf(Sex.class);
        enumSet.add(Sex.GENTLEMAN);
        enumSet.add(Sex.LADY);
        for (Sex sex : enumSet.clone()) {
            System.out.print(sex.name()+", ");
        }
        System.out.println();
    }
}
