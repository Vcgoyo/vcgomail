import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMQTest {
	public void sQueueProducerTest(){
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		Queue queue=(Queue) context.getBean("queueMQ");
		
		jmsTemplate.send(queue,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("springMQTe;st");
				return message;
			}
		});
	}
	
}
