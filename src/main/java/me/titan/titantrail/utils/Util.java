package me.titan.titantrail.utils;

public class Util {

	public static <E extends Enum<E>> E getEnum(String name, Class<E> clazz){
		name = name.replace(" ", "_");
		name = name.toUpperCase();

		return Enum.valueOf(clazz, name);
	}
}
