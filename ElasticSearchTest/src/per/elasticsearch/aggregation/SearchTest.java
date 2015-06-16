package per.elasticsearch.aggregation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;

import per.elasticsearch.client.ElasticSearchClient;
import per.elasticsearch.writeData.Product;

public class SearchTest {
	
	private static final int DEFAULT = 100;
	private static final String INDEX_NAME = "product";
	private static final String TYPE_NAME = "wxt";

	public SearchTest() {
		
	}
	
	/*
	 * aggregation
	 */
	
	public static SearchResponse minMax(String from, String end) {
		Client client = ElasticSearchClient.getClient();
		SearchResponse sc = client
				.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(
						QueryBuilders.boolQuery().must(
								QueryBuilders.rangeQuery("ID").gte(from)
										.lte(end))).setSize(DEFAULT)
				// .must(QueryBuilders.queryString("")))
				.execute().actionGet();

		return sc;
	}
	
	public static List<Product> getSC(SearchResponse sr) {
		List<Product> products = new ArrayList<Product>();
		for (SearchHit hit : sr.getHits()) {
			Map<String, Object> source = hit.getSource();
			if (!source.isEmpty()) {
				for (Iterator<Map.Entry<String, Object>> it = source.entrySet()
						.iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					if ("title".equals(entry.getKey())) {
						System.out.println("title: "
							+ entry.getValue());
					}
				}
			}
		}
		/*for ( SearchHit hit : sr.getHits()) {
			hit.get
			System.out.println(hit.field("title"));
//			nameString = field.getValue();
			field = hit.field("price");
//			priceDouble = field.getValue();
			System.out.println(field);
			
		}*/
		return products;
	}
	public static void main(String args[]) {
		SearchTest.getSC(SearchTest.minMax("000024", "000050"));
	}

}
