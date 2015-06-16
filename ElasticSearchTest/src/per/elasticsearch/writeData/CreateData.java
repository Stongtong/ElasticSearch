package per.elasticsearch.writeData;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class CreateData {

	public CreateData() {
		
	}
	/*
	 * �����������Ԫ�أ�����list��
	 */
	public static List<Product> getRandomProduct(int num) throws ParseException {
		List<Product> products = new ArrayList<Product>();
		Product product = null;
		for (int i = 1; i < num+1; i++) {
			product = new Product();
			product.setID("0000"+i);
			product.setTitle("watch"+(i+10));
			product.setDescription("This is the "+i+" watches that is made in our factory");
			product.setPrice(Math.random()*1000);
			product.setNumber((int)(Math.random()*100));
			product.setInDate(Time.getRandomInTime());
			product.setOutDate(Time.getRandomOutTime());
			product.setType((Math.random() > 0.5) ? true : false);
			products.add(product);
		}
		return products;
	}
	
	/*
	 * �����������json
	 */
	public static XContentBuilder getXContentBuilder(Product product) throws IOException {  
        return XContentFactory.jsonBuilder()  
                .startObject()  
                .field("ID", product.getID())
                .field("title", product.getTitle())//���ֶ�������ķ�����mapping������,���Ը��ֶξ������Զ��������,���� age��  
                .field("description", product.getDescription())  
                .field("number", product.getNumber())  
                .field("price", product.getPrice())  
                .field("inDate", product.getInDate())  
                .field("outDate", product.getOutDate())//���ֶ������淽���е�mapping��û�ж���,���Ը��ֶε�����ʹ��esĬ�ϵ�.  
                .field("type", product.isType())
                .endObject();  
    }  
	

}
