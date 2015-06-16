package per.elasticsearch.writeData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import static org.elasticsearch.common.xcontent.XContentFactory.*;  
import per.elasticsearch.client.ElasticSearchClient;

public class WriteData {

	public WriteData() {
		
		
	}
	static Client client = ElasticSearchClient.getClient();
	
	public void createIndexMethod1() throws IOException {
		
		
		XContentBuilder mapping = jsonBuilder().startObject()
				.startObject("properties")
				.startObject("ID").field("type", "string").field("store", "yes")
				.endObject()
				.startObject("title").field("type", "string").field("store", "yes")
				.endObject()
				.startObject("description").field("type", "string").field("index", "analyzed")
				.endObject()
				.startObject("number").field("type", "integer")
				.endObject()
				.startObject("price").field("type", "double")
				.endObject()
				.startObject("inDate").field("type", "string")
				.endObject()
				.startObject("outDate").field("type", "string")
				.endObject()
				.startObject("type").field("type", "boolean")
				.endObject().endObject().endObject();
				    
		client.prepareIndex("product","wxt").setSource(mapping).execute().actionGet();		
		client.close();

	}
	
	public void createIndexMethod2() throws IOException {

		Client client = ElasticSearchClient.getClient();
		Map<String, Object> settings = new HashMap<>();
		settings.put("number_of_shards", 4);// 分片数量
		settings.put("number_of_replicas", 0);// 复制数量
		settings.put("refresh_interval", "10s");// 刷新时间

		// 在本例中主要得注意,ttl及timestamp如何用java ,这些字段的具体含义,请去到es官网查看
		CreateIndexRequestBuilder cib = client.admin().indices()
				.prepareCreate("abcd");
		cib.setSettings(settings);

		XContentBuilder mapping = XContentFactory
				.jsonBuilder()
				.startObject()
				.startObject("we3r")
				//
				.startObject("_ttl")
				// 有了这个设置,就等于在这个给索引的记录增加了失效时间,
				// ttl的使用地方如在分布式下,web系统用户登录状态的维护.
				.field("enabled", true)
				// 默认的false的
				.field("default", "5m")
				// 默认的失效时间,d/h/m/s 即天/小时/分钟/秒
				.field("store", "yes")
				.field("index", "not_analyzed")
				.endObject()
				.startObject("_timestamp")
				// 这个字段为时间戳字段.即你添加一条索引记录后,自动给该记录增加个时间字段(记录的创建时间),搜索中可以直接搜索该字段.
				.field("enabled", true)
				.field("store", "no")
				.field("index", "not_analyzed")
				.endObject()
				// properties下定义的name等等就是属于我们需要的自定义字段了,相当于数据库中的表字段 ,此处相当于创建数据库表
				.startObject("properties").startObject("@timestamp")
				.field("type", "long").endObject().startObject("name")
				.field("type", "string").field("store", "yes").endObject()
				.startObject("home").field("type", "string")
				.field("index", "not_analyzed").endObject()
				.startObject("now_home").field("type", "string")
				.field("index", "not_analyzed").endObject()
				.startObject("height").field("type", "double").endObject()
				.startObject("age").field("type", "integer").endObject()
				.startObject("birthday").field("type", "date")
				.field("format", "YYYY-MM-dd").endObject()
				.startObject("isRealMen").field("type", "boolean").endObject()
				.startObject("location").field("lat", "double")
				.field("lon", "double").endObject().endObject().endObject()
				.endObject();
		cib.addMapping("wx", mapping);
		cib.execute().actionGet();
		client.close();

	}

	public static void buildBulkIndex(List<Product> products)
			throws IOException {
		Client client = ElasticSearchClient.getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		// either use Es_Setting.client#prepare, or use Requests# to directly
		// build index/delete requests
		int i = 0;
		for (Product product : products) {
			// 通过add批量添加
			
			bulkRequest.add(client.prepareIndex("product", "wxt").setId("0000"+i).setSource(
					CreateData.getXContentBuilder(product)));
			i++;
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		// 如果失败
		if (bulkResponse.hasFailures()) {
			// process failures by iterating through each bulk response item
			System.out.println("buildFailureMessage:"
					+ bulkResponse.buildFailureMessage());
		}
		client.close();
	}
	
	/** 
     * 给 []index 创建别名 
     * 重载方法可以按照过滤器或者Query 作为一个别名 
     * 
     * @param aliases aliases别名 
     * @param indices 多个 index 
     * @return 是否完成 
     */  
    protected static boolean createAliases(String aliases, String... indices) {  
        IndicesAliasesRequestBuilder builder = client.admin().indices().prepareAliases();  
        return builder.addAlias(indices, aliases).execute().isDone();  
    }  
  
    /** 
     * 查询此别名是否存在 
     * 
     * @param aliases aliases 
     * @return 是否存在 
     */  
    protected static boolean aliasesExist(String... aliases) {  
        AliasesExistRequestBuilder builder =  
                client.admin().indices().prepareAliasesExist(aliases);  
        AliasesExistResponse response = builder.execute().actionGet();  
        return response.isExists();  
    }  
	
	public static void main(String args[]) throws Exception{
		
		WriteData writeData = new WriteData();
		//writeData.createIndexMethod1();
		List<Product> products = new ArrayList<Product>();
//		List<String> strings = new ArrayList<String>();
//		for (int i = 0; i < 100; i++) {
//			strings.add(""+i);
//		}
//		System.out.println(strings.get(0));
//		strings.add("123");
//		strings.add("456");            
//		strings.add("789");
//		for (String product : strings) {
//			System.out.println(product);
//		}
//		products = CreateData.getRandomProduct(100);
//		for (Product product : products) {
//			System.out.println(product.getID());
//		}
//		writeData.buildBulkIndex(products);
//		System.out.println("true"); 
		writeData.createIndexMethod2();
		
	}
		

}
