package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
	public static void main(String[] args) {
		long number = 10000;
		testStrategy(new HashMapStorageStrategy(), number);
	}
	public static Set<Long> getIds(Shortener shortener, Set<String> strings){
		Set<Long> longSet = new HashSet<>();
		for (String string : strings) {
			longSet.add(shortener.getId(string));
		}
		return longSet;
	}
	public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
		Set<String> stringSet = new HashSet<>();
        for (Long key : keys) {
            stringSet.add(shortener.getString(key));
        }
		return stringSet;
	}
	public static void testStrategy(StorageStrategy strategy, long elementsNumber){
		Helper.printMessage(strategy.getClass().getSimpleName() + ":");

		Set<String> originStrings = new HashSet<>();

		for (int i = 0; i < elementsNumber; ++i) {
			originStrings.add(Helper.generateRandomString());
		}

		Shortener shortener = new Shortener(strategy);
		Date starDate = new Date();
		Set<Long> keys = getIds(shortener, originStrings);
		Date endDate = new Date();
		long time = endDate.getTime() - starDate.getTime();
		Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time);

		starDate = new Date();
		Set<String> string = getStrings(shortener, keys);
        endDate = new Date();
		long time2 = endDate.getTime() - starDate.getTime();
		Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time);

		if(originStrings.equals(string)){
			Helper.printMessage("Тест пройден.");
		}else {
			Helper.printMessage("Тест не пройден.");
		}
	}
}
