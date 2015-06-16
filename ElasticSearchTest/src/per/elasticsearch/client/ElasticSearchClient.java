package per.elasticsearch.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchClient {

	private final static String HOST = "192.168.244.128";

	public static Client getClient() {
		Client client = null;
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch")
				.put("client.transport.sniff", true)// �Զ���̽��Ⱥ�еĽڵ㣬���Ҽ��뵽�ͻ���
				.build();
		client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						HOST, 9300));

		return client;
	}
	
/*	public static void main(String[] args) throws Exception {

		Client client = ElasticSearchClient.getClient();

	}*/
}
