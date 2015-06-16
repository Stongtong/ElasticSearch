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
	 * 随机产生对象元素，放入list中
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
	 * 将对象解析成json
	 */
	public static XContentBuilder getXContentBuilder(Product product) throws IOException {  
        return XContentFactory.jsonBuilder()  
                .startObject()  
                .field("ID", product.getID())
                .field("title", product.getTitle())//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 age等  
                .field("description", product.getDescription())  
                .field("number", product.getNumber())  
                .field("price", product.getPrice())  
                .field("inDate", product.getInDate())  
                .field("outDate", product.getOutDate())//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.  
                .field("type", product.isType())
                .endObject();  
    }  
	

}
