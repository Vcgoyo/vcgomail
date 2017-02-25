import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vcgo.mapper.TbItemMapper;
import com.vcgo.pojo.TbItem;
import com.vcgo.pojo.TbItemExample;

public class TestPageHelper {
	

	public void pageTest() {
		
		PageHelper.startPage(1,10);		
		ApplicationContext applicationContext =new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample  example=new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(list.get(0));
		
	}
}
