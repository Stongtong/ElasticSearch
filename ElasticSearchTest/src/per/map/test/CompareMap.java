package per.map.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareMap {

	public static void main(String [] args) {

		init();
	}

	static HashMap<String, Integer>  mHashMap;
	static List<Map.Entry<String, Integer>> mHashMapEntryList;

	private static void init() {
		mHashMap = new HashMap<String, Integer>();
		mHashMap.put("a", 9527);
		mHashMap.put("z", 7);
		mHashMap.put("c", 888);
		mHashMap.put("x", 666);
		mHashMapEntryList = new ArrayList<Map.Entry<String, Integer>>(
				mHashMap.entrySet());

		System.out.println("-----> ÅÅĞòÇ°µÄË³Ğò");
		for (int i = 0; i < mHashMapEntryList.size(); i++) {
			System.out.println(mHashMapEntryList.get(i));
		}

		Collections.sort(mHashMapEntryList,
				new Comparator<Map.Entry<String, Integer>>() {

					@Override
					public int compare(
							Map.Entry<String, Integer> firstMapEntry,
							Map.Entry<String, Integer> secondMapEntry) {
						return firstMapEntry.getKey().compareTo(
								secondMapEntry.getKey());
					}
				});

		System.out.println("-----> ÅÅĞòºóµÄË³Ğò");
		for (int i = 0; i < mHashMapEntryList.size(); i++) {
			System.out.println(mHashMapEntryList.get(i));
		}
	}
}
