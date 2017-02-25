import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jws.soap.SOAPBinding;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActiveMQProducerTest {
	
	public void producerTest() {
		try {
			ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.28.134:61616");
			
			Connection connection = connectionFactory.createConnection();
			
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Queue queue=session.createQueue("test_queue");
			
			MessageProducer producer = session.createProducer(queue);
			
			TextMessage textMessage = session.createTextMessage("new Message111");
			
			producer.send(textMessage);
			
			producer.close();
			session.close();
			connection.close();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void customerTest() {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.28.134:61616");

			Connection connection = connectionFactory.createConnection();
			
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("test_queue");
			
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					try {
						
					TextMessage textMessage=(TextMessage) message;
					System.out.println(textMessage.getText());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			
			System.in.read();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void  producerTopic() throws Exception {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.28.134:61616");
		
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = session.createTopic("test-topic");
		
		MessageProducer producer = session.createProducer(topic);
		
		TextMessage textMessage = session.createTextMessage("topic_message");
		
		producer.send(textMessage);
		
		producer.close();
	session.close();
		connection.close();
	}
	public void CustomerTopic() throws Exception {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.28.134:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		System.out.println("consumer1");
		System.in.read();
	}
	public void CustomerTopic2() throws Exception {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.28.134:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		System.out.println("consumer2");
		System.in.read();
	}
	
	
	
	
}
