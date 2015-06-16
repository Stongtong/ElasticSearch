package per.elasticsearch.test;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import per.elasticsearch.client.ElasticSearchClient;

;

public class CreateMapping {

	private static Client client = ElasticSearchClient.getClient();

	/**
	 * 创建索引名称
	 * 
	 * @param indices
	 *            索引名称
	 */
	public static void createCluterName(String indices) {

		client.admin().indices().prepareCreate(indices).execute().actionGet();

		client.close();

	}

	/**
	 * 创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引
	 * ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
	 * 
	 * @param indices
	 *            索引名称；
	 * @param mappingType
	 *            索引类型
	 * @throws Exception
	 */
	public static void createMapping(String indices, String mappingType)
			throws Exception {

		new XContentFactory();

		XContentBuilder builder = XContentFactory.jsonBuilder()

		.startObject()

		.startObject(indices)

		.startObject("properties")

		.startObject("id").field("type", "integer").field("store", "yes")
				.endObject().startObject("kw").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().startObject("edate")
				.field("type", "date").field("store", "yes")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.endObject().endObject().endObject().endObject();

		PutMappingRequest mapping = Requests.putMappingRequest(indices)
				.type(mappingType).source(builder);

		client.admin().indices().putMapping(mapping).actionGet();

		client.close();

	}

	public static void main(String[] args) throws Exception {

		createMapping("lianan", "lianan");

		createCluterName("lianan");

	}

}
