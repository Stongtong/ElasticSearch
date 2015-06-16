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
		settings.put("number_of_shards", 4);// ��Ƭ����
		settings.put("number_of_replicas", 0);// ��������
		settings.put("refresh_interval", "10s");// ˢ��ʱ��

		// �ڱ�������Ҫ��ע��,ttl��timestamp�����java ,��Щ�ֶεľ��庬��,��ȥ��es�����鿴
		CreateIndexRequestBuilder cib = client.admin().indices()
				.prepareCreate("abcd");
		cib.setSettings(settings);

		XContentBuilder mapping = XContentFactory
				.jsonBuilder()
				.startObject()
				.startObject("we3r")
				//
				.startObject("_ttl")
				// �����������,�͵���������������ļ�¼������ʧЧʱ��,
				// ttl��ʹ�õط����ڷֲ�ʽ��,webϵͳ�û���¼״̬��ά��.
				.field("enabled", true)
				// Ĭ�ϵ�false��
				.field("default", "5m")
				// Ĭ�ϵ�ʧЧʱ��,d/h/m/s ����/Сʱ/����/��
				.field("store", "yes")
				.field("index", "not_analyzed")
				.endObject()
				.startObject("_timestamp")
				// ����ֶ�Ϊʱ����ֶ�.�������һ��������¼��,�Զ����ü�¼���Ӹ�ʱ���ֶ�(��¼�Ĵ���ʱ��),�����п���ֱ���������ֶ�.
				.field("enabled", true)
				.field("store", "no")
				.field("index", "not_analyzed")
				.endObject()
				// properties�¶����name�ȵȾ�������������Ҫ���Զ����ֶ���,�൱�����ݿ��еı��ֶ� ,�˴��൱�ڴ������ݿ��
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
			// ͨ��add�������
			
			bulkRequest.add(client.prepareIndex("product", "wxt").setId("0000"+i).setSource(
					CreateData.getXContentBuilder(product)));
			i++;
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		// ���ʧ��
		if (bulkResponse.hasFailures()) {
			// process failures by iterating through each bulk response item
			System.out.println("buildFailureMessage:"
					+ bulkResponse.buildFailureMessage());
		}
		client.close();
	}
	
	/** 
     * �� []index �������� 
     * ���ط������԰��չ���������Query ��Ϊһ������ 
     * 
     * @param aliases aliases���� 
     * @param indices ��� index 
     * @return �Ƿ���� 
     */  
    protected static boolean createAliases(String aliases, String... indices) {  
        IndicesAliasesRequestBuilder builder = client.admin().indices().prepareAliases();  
        return builder.addAlias(indices, aliases).execute().isDone();  
    }  
  
    /** 
     * ��ѯ�˱����Ƿ���� 
     * 
     * @param aliases aliases 
     * @return �Ƿ���� 
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
