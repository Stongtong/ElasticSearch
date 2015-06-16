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
	 * ������������
	 * 
	 * @param indices
	 *            ��������
	 */
	public static void createCluterName(String indices) {

		client.admin().indices().prepareCreate(indices).execute().actionGet();

		client.close();

	}

	/**
	 * ����mapping(feid("indexAnalyzer","ik")���ֶηִ�IK����
	 * ��feid("searchAnalyzer","ik")���ֶηִ�ik��ѯ������ִʲ���뿴IK�ִʲ��˵��)
	 * 
	 * @param indices
	 *            �������ƣ�
	 * @param mappingType
	 *            ��������
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
