import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class freemakrerTest {
	
	@Test
	public void freemarkerTest() {
		try {
			Configuration configuration=new Configuration(Configuration.getVersion());
			
			configuration.setDefaultEncoding("utf-8");
			configuration.setDirectoryForTemplateLoading(new File("E:/JAVA/eclipse2017workplace/vcgo-item-web/src/main/webapp/WEB-INF/ftl"));
			
			Template template=configuration.getTemplate("example.ftl");
			Map  data=new HashMap<>();
			
			data.put("hellow", "你好");
			
			Writer writer=new FileWriter("E:/JAVA/eclipse2017workplace/freemakerTEMPFile/hellow.html");
			
			template.process(data, writer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
