package com.vcgo.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.EasyUIDataGridResult;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.pojo.TbItem;
import com.vcgo.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name="topic")
	private Destination topic;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long id){
		return itemService.geTbItemById(id);
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		return itemService.getItemList(page, rows);
	}
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult addItem(TbItem item,String desc) {
		 
		 JsonResult result = itemService.addItem(item, desc);
		 
		 final Long  id = ((TbItem)result.getData()).getId();
		 jmsTemplate.send(topic,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				System.out.println(id);
				TextMessage textMessage = session.createTextMessage(id+"");
				return textMessage;
			}
		});
		 return result;
	}
}
