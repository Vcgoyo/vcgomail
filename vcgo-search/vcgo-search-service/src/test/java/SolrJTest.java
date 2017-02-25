import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jms.MessageNotWriteableException;

public class SolrJTest {

    public void solrAdd() {
		try {
			SolrServer solrServer=new HttpSolrServer("http://192.168.28.134:9009/solr/collection1");
			
			SolrInputDocument document=new SolrInputDocument();
			
			document.addField("id", "123455");
			document.addField("item_title", "测试title");
			document.addField("item_sell_point", "测试卖点");
			document.addField("item_price", 1233);
			document.addField("item_image", "43444");
			document.addField("item_category_name", "手机");
			document.addField("item_desc", "33332");
			
			solrServer.add(document);
			
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void solrDelete() {
		try {
			SolrServer solrServer=new HttpSolrServer("http://192.168.28.134:9009/solr/collection1");
			solrServer.deleteById("123455");
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void queryList() {
		SolrServer solrServer=new HttpSolrServer("http://192.168.28.134:9009/solr/collection1");
		
//		SolrQuery query=new SolrQuery();
//		query.setQuery(query)
	}

}
