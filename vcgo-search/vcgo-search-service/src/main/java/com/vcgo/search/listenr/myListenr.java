package com.vcgo.search.listenr;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class myListenr implements MessageListener {

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

}
