package per.map.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		Map<Integer, Integer> finalResult = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			finalResult.put(i, i);
		}
		Iterator<java.util.Map.Entry<Integer, Integer>> it = finalResult
				.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) it
					.next();
			System.out.println("随机名次：" + entry.getKey() + " ,出现次数："
					+ entry.getValue());
		}
		

	}

}
